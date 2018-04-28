package org.scribble.ext.go.core.ast.global;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.scribble.ast.global.GChoice;
import org.scribble.ast.global.GContinue;
import org.scribble.ast.global.GInteractionNode;
import org.scribble.ast.global.GMessageTransfer;
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.ast.global.GProtocolDef;
import org.scribble.ast.global.GRecursion;
import org.scribble.ast.global.GSimpleInteractionNode;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.global.GProtocolDefDel;
import org.scribble.ext.go.ast.RPAstFactory;
import org.scribble.ext.go.ast.global.RPGCrossMessageTransfer;
import org.scribble.ext.go.ast.global.RPGDotMessageTransfer;
import org.scribble.ext.go.ast.global.RPGForeach;
import org.scribble.ext.go.core.ast.RPCoreAstFactory;
import org.scribble.ext.go.core.ast.RPCoreSyntaxException;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.ext.go.core.type.RPInterval;
import org.scribble.main.Job;
import org.scribble.type.Message;
import org.scribble.type.kind.RecVarKind;
import org.scribble.type.name.DataType;
import org.scribble.type.name.RecVar;

public class RPCoreGProtocolDeclTranslator
{
	public static final DataType UNIT_DATATYPE = new DataType("_Unit");  // FIXME: move
	
	private final Job job;
	private final RPCoreAstFactory af;
	
	private static int recCounter = 1;

	private static String makeFreshRecVarName()
	{
		return "_X" + RPCoreGProtocolDeclTranslator.recCounter++;
	}
	
	public RPCoreGProtocolDeclTranslator(Job job, RPCoreAstFactory af)
	{
		this.job = job;
		this.af = af;
	}

	public RPCoreGType translate(GProtocolDecl gpd) throws RPCoreSyntaxException
	{
		GProtocolDef inlined = ((GProtocolDefDel) gpd.def.del()).getInlinedProtocolDef();
		return parseSeq(inlined.getBlock().getInteractionSeq().getInteractions(), new HashMap<>(), false, false);
	}

	// List<GInteractionNode> because subList is useful for parsing the continuation
	private RPCoreGType parseSeq(List<GInteractionNode> is, Map<RecVar, RecVar> rvs,
			boolean checkChoiceGuard, boolean checkRecGuard) throws RPCoreSyntaxException
	{
		if (is.isEmpty())
		{
			return this.af.ParamCoreGEnd();
		}

		GInteractionNode first = is.get(0);
		if (first instanceof GSimpleInteractionNode && !(first instanceof GContinue))
		{
			if (first instanceof GMessageTransfer)
			{
				return parseGMessageTransfer(is, rvs, (GMessageTransfer) first);
			}
			else
			{
				throw new RuntimeException("[rp-core] Shouldn't get in here: " + first);
			}
		}
		else
		{
			if (checkChoiceGuard)  // No "flattening" of nested choices not allowed?
			{
				throw new RPCoreSyntaxException(first.getSource(), "[rp-core] Unguarded in choice case: " + first);
			}
			if (!(first instanceof RPGForeach) && is.size() > 1)  // Using Scribble sequencing for foreach continuation
			{
				throw new RPCoreSyntaxException(is.get(1).getSource(), "[rp-core] Bad sequential composition after: " + first);
			}

			if (first instanceof GChoice)
			{
				/*if (first instanceof RPGMultiChoices)
				{
					return parseParamGMultiChoices(rvs, checkRecGuard, (RPGMultiChoices) first);
				}
				else*/ // GChoice or ParamGChoice
				{
					return parseGChoice(rvs, checkRecGuard, (GChoice) first);
				}
			}
			else if (first instanceof GRecursion)
			{
				return parseGRecursion(rvs, checkChoiceGuard, (GRecursion) first);
			}
			else if (first instanceof GContinue)
			{
				return parseGContinue(rvs, checkRecGuard, (GContinue) first);
			}
			else if (first instanceof RPGForeach)
			{
				Map<RecVar, RecVar> tmp = new HashMap<>(rvs);
				RPCoreGType seq = parseSeq(is.subList(1, is.size()), tmp, false, false);  // CHECKME: false guards
				return parseRPGForeach(rvs, checkRecGuard, (RPGForeach) first, seq);
			}
			else
			{
				throw new RuntimeException("[rp-core] Shouldn't get in here: " + first);
			}
		}
	}

	private RPCoreGType parseGChoice(Map<RecVar, RecVar> rvs,
			boolean checkRecGuard, GChoice gc) throws RPCoreSyntaxException
	{
		List<RPCoreGType> children = new LinkedList<>();
		for (GProtocolBlock b : gc.getBlocks())
		{
			children.add(parseSeq(b.getInteractionSeq().getInteractions(), rvs, true, checkRecGuard));  // Check cases are guarded
		}

		RPCoreGActionKind kind = null;
		RPIndexedRole src = null;
		RPIndexedRole dest = null;
		//LinkedHashMap<RPCoreMessage, RPCoreGType>
		LinkedHashMap<Message, RPCoreGType>
				cases = new LinkedHashMap<>();
		for (RPCoreGType c : children)
		{
			// Because all cases should be action guards (unary choices)
			if (!(c instanceof RPCoreGChoice))
			{
				throw new RuntimeException("[rp-core] Shouldn't get in here: " + c);
			}
			RPCoreGChoice tmp = (RPCoreGChoice) c;
			if (tmp.cases.size() > 1)
			{
				throw new RuntimeException("[rp-core] Shouldn't get in here: " + c);
			}
			
			if (src == null)
			{
				kind = tmp.getKind();
				src = tmp.src;
				dest = tmp.dest;
			}
			else if (!kind.equals(tmp.kind))
			{
				throw new RuntimeException("[rp-core] Shouldn't get in here: " + gc + ", " + kind + ", " + tmp.kind);
			}
			else if (!src.equals(tmp.src) || !dest.equals(tmp.dest))
			{
				throw new RPCoreSyntaxException(gc.getSource(),
						"[rp-core] Non-directed choice not supported: " + gc + ", " + src + ", " + tmp.src + ", " + dest + ", " + tmp.dest);
			}
			
			// "Flatten" nested choices (already checked they are guarded) -- Scribble choice subjects ignored
			for //(Entry<RPCoreMessage, RPCoreGType>
					(Entry<Message, RPCoreGType>
							e : tmp.cases.entrySet())
			{
				//RPCoreMessage k = e.getKey();
				Message k = e.getKey();
				if (cases.keySet().stream().anyMatch(x -> //x.op.equals(k.op)))
						x.equals(k)))
				{
					throw new RPCoreSyntaxException(gc.getSource(),
							"[rp-core] Non-deterministic actions not supported: " + k);
				}
				cases.put(k, e.getValue());
			}
		}
		
		return this.af.ParamCoreGChoice(src, kind, dest, cases);
	}

	/*private RPCoreGMultiChoices parseGMultiChoicesTransfer(List<GInteractionNode> is, Map<RecVar, RecVar> rvs) throws RPCoreSyntaxException 
	{
		GInteractionNode in = is.get(0);
		if (!(in instanceof RPGMultiChoicesTransfer))
		{
			throw new RPCoreSyntaxException(in.getSource(), "[rp-core] Expected GMultiChoicesTransfer: " + in.getClass());
		}

		RPGMultiChoicesTransfer gmt = (RPGMultiChoicesTransfer) in;
		RPCoreMessage a = this.af.ParamCoreAction(parseOp(gmt), parsePayload(gmt));

		String srcName = parseSourceRole(gmt);
		String destName = parseDestinationRole(gmt);
		RPIndexedRole src = af.ParamRole(srcName, new RPInterval(gmt.var, gmt.var));  // HACK var -- multichoices needs src range (e.g., projection), but multichoicestransfer doesn't
		RPIndexedRole dest = af.ParamRole(destName, new RPInterval(gmt.destRangeStart, gmt.destRangeEnd));
		
		RPCoreGType cont = parseSeq(is.subList(1, is.size()), rvs, false, false);  // Subseqeuent choice/rec is guarded by (at least) this action
		return this.af.ParamCoreGMultiChoices(src, gmt.var, dest, Stream.of(a).collect(Collectors.toList()), cont);
	}

	// FIXME: factor out with parseGChoice
	private RPCoreGType parseParamGMultiChoices(Map<RecVar, RecVar> rvs,
			boolean checkRecGuard, RPGMultiChoices gc) throws RPCoreSyntaxException
	{
		List<RPCoreGMultiChoices> children = new LinkedList<>();
		for (GProtocolBlock b : gc.getBlocks())
		{
			children.add(parseGMultiChoicesTransfer(b.getInteractionSeq().getInteractions(), rvs));  // Check cases are guarded
		}

		RPCoreGActionKind kind = null;
		RPIndexedRole src = null;
		RPIndexedRole dest = null;
		LinkedHashMap<RPCoreMessage, RPCoreGType> cases = new LinkedHashMap<>();
		for (RPCoreGMultiChoices c : children)
		{
			RPCoreGMultiChoices tmp = (RPCoreGMultiChoices) c;
			if (tmp.cases.size() > 1)
			{
				throw new RuntimeException("[rp-core] Shouldn't get in here: " + c);
			}
			
			if (src == null)
			{
				kind = tmp.getKind();
				src = tmp.src;
				dest = tmp.dest;
			}
			else if (!kind.equals(tmp.kind))
			{
				throw new RuntimeException("[rp-core] Shouldn't get in here: " + gc + ", " + kind + ", " + tmp.kind);
			}
			else if (!src.equals(tmp.src) || !dest.equals(tmp.dest))
			{
				throw new RPCoreSyntaxException(gc.getSource(),
						"[rp-core] Non-directed choice not supported: " + gc + ", " + src + ", " + tmp.src + ", " + dest + ", " + tmp.dest);
			}
			
			// "Flatten" nested choices (already checked they are guarded) -- Scribble choice subjects ignored
			for (Entry<RPCoreMessage, RPCoreGType> e : tmp.cases.entrySet())
			{
				RPCoreMessage k = e.getKey();
				if (cases.keySet().stream().anyMatch(x -> x.op.equals(k.op)))
				{
					throw new RPCoreSyntaxException(gc.getSource(),
							"[rp-core] Non-deterministic actions not supported: " + k.op);
				}
				cases.put(k, e.getValue());
			}
		}
		
		if (new HashSet<>(cases.values()).size() > 1)
		{
			throw new RPCoreSyntaxException(gc.getSource(),
					"[rp-core] Continuations not syntactically equal: " + cases.values());
		}
		
		return this.af.ParamCoreGMultiChoices(
				//src,
				new RPIndexedRole(gc.subj.toString(), Stream.of(new RPInterval(gc.start, gc.end)).collect(Collectors.toSet())),
				gc.var, dest, cases.keySet().stream().collect(Collectors.toList()), cases.values().iterator().next());
	}*/

	private RPCoreGType parseGRecursion(Map<RecVar, RecVar> rvs,
			boolean checkChoiceGuard, GRecursion gr) throws RPCoreSyntaxException
	{
		RecVar recvar = gr.recvar.toName();
		if (recvar.toString().contains("__"))  // HACK: "inlined proto rec var"
		{
			RecVarNode rvn = (RecVarNode) ((RPAstFactory) this.job.af).SimpleNameNode(null, RecVarKind.KIND, makeFreshRecVarName());
			rvs.put(recvar, rvn.toName());
			recvar = rvn.toName();
		}
		RPCoreGType body = parseSeq(gr.getBlock().getInteractionSeq().getInteractions(), rvs, checkChoiceGuard, true);  // Check rec body is guarded

		return this.af.ParamCoreGRec(recvar, body);
	}

	private RPCoreGType parseGContinue(Map<RecVar, RecVar> rvs, boolean checkRecGuard, GContinue gc)
			throws RPCoreSyntaxException
	{
		if (checkRecGuard)
		{
			throw new RPCoreSyntaxException(gc.getSource(), "[rp-core] Unguarded in recursion: " + gc);  // FIXME: too conservative, e.g., rec X . A -> B . rec Y . X
		}
		RecVar recvar = gc.recvar.toName();
		if (rvs.containsKey(recvar))
		{
			recvar = rvs.get(recvar);
		}
		return this.af.ParamCoreGRecVar(recvar);
	}
	
	// FIXME: need "cont"?
	private RPCoreGForeach parseRPGForeach(Map<RecVar, RecVar> rvs, boolean checkRecGuard, RPGForeach gf, RPCoreGType seq) throws RPCoreSyntaxException
	{
		RPCoreGType body = parseSeq(gf.getBlock().getInteractionSeq().getInteractions(), Collections.emptyMap(), false, true);
		return this.af.RPCoreGForeach(gf.subj.toName(), gf.var, gf.start, gf.end, body, seq);
	}

	// Parses message interactions as unary choices
	private RPCoreGChoice parseGMessageTransfer(List<GInteractionNode> is, Map<RecVar, RecVar> rvs, GMessageTransfer gmt) throws RPCoreSyntaxException 
	{
		//RPCoreMessage a = this.af.ParamCoreAction(parseOp(gmt), parsePayload(gmt));
		Message a = gmt.msg.toMessage();
		String srcName = parseSourceRole(gmt);
		String destName = parseDestinationRole(gmt);
		RPCoreGActionKind kind;
		RPIndexedRole src;
		RPIndexedRole dest;
		if (gmt instanceof RPGCrossMessageTransfer)
		{
			RPGCrossMessageTransfer cross = (RPGCrossMessageTransfer) gmt;
			kind = RPCoreGActionKind.CROSS_TRANSFER;
			/*src = af.ParamRole(srcName, new ParamRange(cross.srcRangeStart.toName(), cross.srcRangeEnd.toName()));
			dest = af.ParamRole(destName, new ParamRange(cross.destRangeStart.toName(), cross.destRangeEnd.toName()));*/
			src = af.ParamRole(srcName, new RPInterval(cross.srcRangeStart, cross.srcRangeEnd));
			dest = af.ParamRole(destName, new RPInterval(cross.destRangeStart, cross.destRangeEnd));
		}
		else if (gmt instanceof RPGDotMessageTransfer)
		{
			RPGDotMessageTransfer dot = (RPGDotMessageTransfer) gmt;
			kind = RPCoreGActionKind.DOT_TRANSFER;
			/*src = af.ParamRole(srcName, new ParamRange(dot.srcRangeStart.toName(), dot.srcRangeEnd.toName()));
			dest = af.ParamRole(destName, new ParamRange(dot.destRangeStart.toName(), dot.destRangeEnd.toName()));*/
			src = af.ParamRole(srcName, new RPInterval(dot.srcRangeStart, dot.srcRangeEnd));
			dest = af.ParamRole(destName, new RPInterval(dot.destRangeStart, dot.destRangeEnd));
		}
		else
		{
			/*src = af.ParamRole(srcName, 1, 1); 
			dest = af.ParamRole(destName, 1, 1);*/
			throw new RPCoreSyntaxException(gmt.getSource(), "[rp-core] Not supported: " + gmt.getClass() + "\n    " + gmt);
		}
		return parseGSimpleInteractionNode(is, rvs, src, kind, a, dest);
	}

	// Duplicated from parseGMessageTransfer (MessageTransfer and ConnectionAction have no common

	private RPCoreGChoice parseGSimpleInteractionNode(
			List<GInteractionNode> is, Map<RecVar, RecVar> rvs, 
			RPIndexedRole src, RPCoreGActionKind kind, //RPCoreMessage a,
			Message a,
			RPIndexedRole dest) throws RPCoreSyntaxException 
	{
		if (src.equals(dest))
		{
			throw new RuntimeException("[rp-core] Shouldn't get in here (self-communication): " + src + ", " + dest);
		}
		
		RPCoreGType cont = parseSeq(is.subList(1, is.size()), rvs, false, false);  // Subseqeuent choice/rec is guarded by (at least) this action
		//LinkedHashMap<RPCoreMessage, RPCoreGType>
		LinkedHashMap<Message, RPCoreGType>
				tmp = new LinkedHashMap<>();
		tmp.put(a, cont);
		return this.af.ParamCoreGChoice(src, kind, dest, tmp);
				//Stream.of(a).collect(Collectors.toMap(x -> x, x -> cont)));
	}

	/*private Op parseOp(GMessageTransfer gmt) throws RPCoreSyntaxException
	{
		return parseOp(gmt.msg);
	}

	private Op parseOp(MessageNode mn) throws RPCoreSyntaxException
	{
		if (!mn.isMessageSigNode())
		{
			throw new RPCoreSyntaxException(mn.getSource(), " [rp-core] Message sig names not supported: " + mn);  // TODO: MessageSigName
		}
		MessageSigNode msn = ((MessageSigNode) mn);
		return msn.op.toName();
	}

	private Payload parsePayload(GMessageTransfer gmt) throws RPCoreSyntaxException
	{
		return parsePayload(gmt.msg);
	}

	private Payload parsePayload(MessageNode mn) throws RPCoreSyntaxException
	{
		if (!mn.isMessageSigNode())
		{
			throw new RPCoreSyntaxException(mn.getSource(), " [rp-core] Message sig names not supported: " + mn);  // TODO: MessageSigName
		}
		return ((MessageSigNode) mn).toMessage().payload;
	}*/

	private String parseSourceRole(GMessageTransfer gmt)
	{
		return parseSourceRole(gmt.src);
	}

	private String parseSourceRole(RoleNode rn)
	{
		return rn.toString();
	}
	
	private String parseDestinationRole(GMessageTransfer gmt) throws RPCoreSyntaxException
	{
		if (gmt.getDestinations().size() > 1)
		{
			throw new RPCoreSyntaxException(gmt.getSource(), " [TODO] Multicast not supported: " + gmt);
		}
		return parseDestinationRole(gmt.getDestinations().get(0));
	}

	private String parseDestinationRole(RoleNode rn) throws RPCoreSyntaxException
	{
		return rn.toString();
	}

	
	
	
	
	
	
	
	
	
	/*
	// Mostly duplicated from parseGMessageTransfer, but GMessageTransfer/GConnect have no useful base class 
	private ParamCoreGConnect parseGConnect(GConnect gc) throws F17Exception 
	{
		Role src = gc.src.toName();
		Role dest = gc.dest.toName();
		if (!gc.msg.isMessageSigNode())
		{
			throw new F17SyntaxException(gc.msg.getSource(), " [f17] Message kind not supported: " + gc.msg);
		}
		MessageSigNode msn = ((MessageSigNode) gc.msg);
		Op op = msn.op.toName();
		Payload pay = null;
		if (msn.payloads.getElements().isEmpty())
		{
			pay = Payload.EMPTY_PAYLOAD;
		}
		else
		{
			String tmp = msn.payloads.getElements().get(0).toString().trim();
			int i = tmp.indexOf('@');
			if (i != -1)
			{
				throw new F17Exception("[f17] Delegation not supported: " + tmp);
			}
			else
			{
				pay = msn.payloads.toPayload();
			}
		}
		return this.factory.GConnect(src, dest, op, pay);
	}
	*/
	
	/*private ParamCoreGType parseSeq(JobContext jc, ModuleContext mc, List<GInteractionNode> is,
			boolean checkChoiceGuard, boolean checkRecGuard) throws F17Exception
	{
		//List<GInteractionNode> is = block.getInteractionSeq().getInteractions();
		if (is.isEmpty())
		{
			return this.factory.GEnd();
		}

		GInteractionNode first = is.get(0);
		if (first instanceof GSimpleInteractionNode && !(first instanceof GContinue))
		{
			if (first instanceof GMessageTransfer)
			{
				ParamCoreGMessageTransfer gmt = parseGMessageTransfer((GMessageTransfer) first);
				ParamCoreGType cont = parseSeq(jc, mc, is.subList(1, is.size()), false, false);
				Map<ParamCoreGAction, ParamCoreGType> cases = new HashMap<>();
					cases.put(gmt, cont);
				return this.factory.GChoice(cases);
			}
			else if (first instanceof GConnect)
			{
				ParamCoreGConnect gc = parseGConnect((GConnect) first);
				ParamCoreGType cont = parseSeq(jc, mc, is.subList(1, is.size()), false, false);
				Map<ParamCoreGAction, ParamCoreGType> cases = new HashMap<>();
				cases.put(gc, cont);
				return this.factory.GChoice(cases);
			}
			else if (first instanceof GDisconnect)
			{
				F17GDisconnect gdc = parseGDisconnect((GDisconnect) first);
				ParamCoreGType cont = parseSeq(jc, mc, is.subList(1, is.size()), false, false);
				Map<ParamCoreGAction, ParamCoreGType> cases = new HashMap<>();
				cases.put(gdc, cont);
				return this.factory.GChoice(cases);
			}
			else
			{
				throw new RuntimeException("[f17] Shouldn't get in here: " + first);
			}
		}
		else
		{
			if (checkChoiceGuard)
			{
				throw new F17SyntaxException(first.getSource(), "[f17] Unguarded in choice case: " + first);
			}
			if (is.size() > 1)
			{
				throw new F17SyntaxException(is.get(1).getSource(), "[f17] Bad sequential composition after: " + first);
			}

			if (first instanceof GChoice)
			{
				/*if (checkRecGuard)
				{
					throw new F17Exception(first.getSource(), "[f17] Unguarded in choice case (2): " + first);
				}* /

				GChoice gc = (GChoice) first; 
				List<ParamCoreGType> parsed = new LinkedList<>();
				for (GProtocolBlock b : gc.getBlocks())
				{
					parsed.add(parseSeq(jc, mc, b.getInteractionSeq().getInteractions(), true, checkRecGuard));  // "Directly" nested choice will still return a GlobalSend (which is really a choice; uniform global choice constructor is convenient)
				}
				Map<ParamCoreGAction, ParamCoreGType> cases = new HashMap<>();
				for (ParamCoreGType p : parsed)
				{
					if (!(p instanceof ParamCoreGChoice))
					{
						throw new RuntimeException("[f17] Shouldn't get in here: " + p);
					}
					ParamCoreGChoice tmp = (ParamCoreGChoice) p;
					//tmp.cases.entrySet().forEach((e) -> cases.put(e.getKey(), e.getValue()));
					for (Entry<ParamCoreGAction, ParamCoreGType> e : tmp.cases.entrySet())
					{
						ParamCoreGAction k = e.getKey();
						if (k.isMessageAction())
						{
							if (cases.keySet().stream().anyMatch((x) ->
									x.isMessageAction() && ((F17MessageAction) k).getOp().equals(((F17MessageAction) x).getOp())))
							{
								throw new F17SyntaxException("[f17] Non-determinism (" + e.getKey() + ") not supported: " + gc);
							}
						}
						cases.put(k, e.getValue());
					}
				}
				return this.factory.GChoice(cases);
			}
			else if (first instanceof GRecursion)
			{
				GRecursion gr = (GRecursion) first;
				RecVar recvar = gr.recvar.toName();
				ParamCoreGType body = parseSeq(jc, mc, gr.getBlock().getInteractionSeq().getInteractions(), checkChoiceGuard, true);
				return new ParamCoreGRec(recvar, body);
			}
			else if (first instanceof GContinue)
			{
				if (checkRecGuard)
				{
					throw new F17SyntaxException(first.getSource(), "[f17] Unguarded in recursion: " + first);  // FIXME: conservative, e.g., rec X . A -> B . rec Y . X
				}

				return this.factory.GRecVar(((GContinue) first).recvar.toName());
			}
			else
			{
				throw new RuntimeException("[f17] Shouldn't get in here: " + first);
			}
		}
	}

	private ParamCoreGMessageTransfer parseGMessageTransfer(GMessageTransfer gmt) throws F17Exception 
	{
		Role src = gmt.src.toName();
		if (gmt.getDestinations().size() > 1)
		{
			throw new F17Exception(gmt.getSource(), " [TODO] Multicast not supported: " + gmt);
		}
		Role dest = gmt.getDestinations().get(0).toName();
		if (!gmt.msg.isMessageSigNode())
		{
			throw new F17SyntaxException(gmt.msg.getSource(), " [f17] Not supported: " + gmt.msg);  // TODO: MessageSigName
		}
		MessageSigNode msn = ((MessageSigNode) gmt.msg);
		Op op = msn.op.toName();
		Payload pay = null;
		if (msn.payloads.getElements().isEmpty())
		{
			pay = Payload.EMPTY_PAYLOAD;
		}
		else
		{
			String tmp = msn.payloads.getElements().get(0).toString().trim();
			int i = tmp.indexOf('@');
			if (i != -1)
			{
				throw new F17Exception("[f17] Delegation not supported: " + tmp);
			}
			else
			{
				pay = msn.payloads.toPayload();
			}
		}
		return this.factory.GMessageTransfer(src, dest, op, pay);
	}

	// Mostly duplicated from parseGMessageTransfer, but GMessageTransfer/GConnect have no useful base class 
	private ParamCoreGConnect parseGConnect(GConnect gc) throws F17Exception 
	{
		Role src = gc.src.toName();
		Role dest = gc.dest.toName();
		if (!gc.msg.isMessageSigNode())
		{
			throw new F17SyntaxException(gc.msg.getSource(), " [f17] Message kind not supported: " + gc.msg);
		}
		MessageSigNode msn = ((MessageSigNode) gc.msg);
		Op op = msn.op.toName();
		Payload pay = null;
		if (msn.payloads.getElements().isEmpty())
		{
			pay = Payload.EMPTY_PAYLOAD;
		}
		else
		{
			String tmp = msn.payloads.getElements().get(0).toString().trim();
			int i = tmp.indexOf('@');
			if (i != -1)
			{
				throw new F17Exception("[f17] Delegation not supported: " + tmp);
			}
			else
			{
				pay = msn.payloads.toPayload();
			}
		}
		return this.factory.GConnect(src, dest, op, pay);
	}

	private F17GDisconnect parseGDisconnect(GDisconnect gdc) throws F17Exception 
	{
		Role src = gdc.src.toName();
		Role dest = gdc.dest.toName();
		return this.factory.GDisconnect(src, dest);
	}*/
}
