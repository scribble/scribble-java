package org.scribble.ext.go.core.ast.global;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.ast.MessageNode;
import org.scribble.ast.MessageSigNode;
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
import org.scribble.ext.go.ast.ParamAstFactory;
import org.scribble.ext.go.ast.global.ParamGCrossMessageTransfer;
import org.scribble.ext.go.ast.global.ParamGDotMessageTransfer;
import org.scribble.ext.go.core.ast.ParamCoreAstFactory;
import org.scribble.ext.go.core.ast.ParamCoreMessage;
import org.scribble.ext.go.core.ast.ParamCoreSyntaxException;
import org.scribble.ext.go.core.type.ParamRange;
import org.scribble.ext.go.core.type.ParamRole;
import org.scribble.main.Job;
import org.scribble.type.Payload;
import org.scribble.type.kind.RecVarKind;
import org.scribble.type.name.DataType;
import org.scribble.type.name.Op;
import org.scribble.type.name.RecVar;

public class ParamCoreGProtocolDeclTranslator
{
	public static final DataType UNIT_DATATYPE = new DataType("_Unit");  // FIXME: move
	
	private final Job job;
	private final ParamCoreAstFactory af;
	
	private static int recCounter = 1;

	private static String makeFreshRecVarName()
	{
		return "_X" + recCounter++;
	}
	
	public ParamCoreGProtocolDeclTranslator(Job job, ParamCoreAstFactory af)
	{
		this.job = job;
		this.af = af;
	}

	public ParamCoreGType translate(GProtocolDecl gpd) throws ParamCoreSyntaxException
	{
		GProtocolDef inlined = ((GProtocolDefDel) gpd.def.del()).getInlinedProtocolDef();
		return parseSeq(inlined.getBlock().getInteractionSeq().getInteractions(), new HashMap<>(), false, false);
	}

	// List<GInteractionNode> because subList is useful for parsing the continuation
	private ParamCoreGType parseSeq(List<GInteractionNode> is, Map<RecVar, RecVar> rvs,
			boolean checkChoiceGuard, boolean checkRecGuard) throws ParamCoreSyntaxException
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
				throw new RuntimeException("[param] Shouldn't get in here: " + first);
			}
		}
		else
		{
			if (checkChoiceGuard)  // No "flattening" of nested choices not allowed?
			{
				throw new ParamCoreSyntaxException(first.getSource(), "[param-core] Unguarded in choice case: " + first);
			}
			if (is.size() > 1)
			{
				throw new ParamCoreSyntaxException(is.get(1).getSource(), "[param-core] Bad sequential composition after: " + first);
			}

			if (first instanceof GChoice)
			{
				return parseGChoice(rvs, checkRecGuard, (GChoice) first);
			}
			else if (first instanceof GRecursion)
			{
				return parseGRecursion(rvs, checkChoiceGuard, (GRecursion) first);
			}
			else if (first instanceof GContinue)
			{
				return parseGContinue(rvs, checkRecGuard, (GContinue) first);
			}
			else
			{
				throw new RuntimeException("[param-core] Shouldn't get in here: " + first);
			}
		}
	}

	private ParamCoreGType parseGChoice(Map<RecVar, RecVar> rvs,
			boolean checkRecGuard, GChoice gc) throws ParamCoreSyntaxException
	{
		List<ParamCoreGType> children = new LinkedList<>();
		for (GProtocolBlock b : gc.getBlocks())
		{
			children.add(parseSeq(b.getInteractionSeq().getInteractions(), rvs, true, checkRecGuard));  // Check cases are guarded
		}

		ParamCoreGActionKind kind = null;
		ParamRole src = null;
		ParamRole dest = null;
		Map<ParamCoreMessage, ParamCoreGType> cases = new HashMap<>();
		for (ParamCoreGType c : children)
		{
			// Because all cases should be action guards (unary choices)
			if (!(c instanceof ParamCoreGChoice))
			{
				throw new RuntimeException("[param-core] Shouldn't get in here: " + c);
			}
			ParamCoreGChoice tmp = (ParamCoreGChoice) c;
			if (tmp.cases.size() > 1)
			{
				throw new RuntimeException("[param-core] Shouldn't get in here: " + c);
			}
			
			if (src == null)
			{
				kind = tmp.getKind();
				src = tmp.src;
				dest = tmp.dest;
			}
			else if (!kind.equals(tmp.kind))
			{
				throw new RuntimeException("[param-core] Shouldn't get in here: " + gc + ", " + kind + ", " + tmp.kind);
			}
			else if (!src.equals(tmp.src) || !dest.equals(tmp.dest))
			{
				throw new ParamCoreSyntaxException(gc.getSource(), "[param-core] Non-directed choice not supported: " + gc + ", " + src + ", " + tmp.src + ", " + dest + ", " + tmp.dest);
			}
			
			// "Flatten" nested choices (already checked they are guarded) -- Scribble choice subjects ignored
			for (Entry<ParamCoreMessage, ParamCoreGType> e : tmp.cases.entrySet())
			{
				ParamCoreMessage k = e.getKey();
				if (cases.keySet().stream().anyMatch(x -> x.op.equals(k.op)))
				{
					throw new ParamCoreSyntaxException(gc.getSource(), "[param-core] Non-deterministic actions not supported: " + k.op);
				}
				cases.put(k, e.getValue());
			}
		}
		
		return this.af.ParamCoreGChoice(src, kind, dest, cases);
	}

	private ParamCoreGType parseGRecursion(Map<RecVar, RecVar> rvs,
			boolean checkChoiceGuard, GRecursion gr) throws ParamCoreSyntaxException
	{
		RecVar recvar = gr.recvar.toName();
		if (recvar.toString().contains("__"))  // HACK: "inlined proto rec var"
		{
			RecVarNode rvn = (RecVarNode) ((ParamAstFactory) this.job.af).SimpleNameNode(null, RecVarKind.KIND, makeFreshRecVarName());
			rvs.put(recvar, rvn.toName());
			recvar = rvn.toName();
		}
		ParamCoreGType body = parseSeq(gr.getBlock().getInteractionSeq().getInteractions(), rvs, checkChoiceGuard, true);  // Check rec body is guarded

		return this.af.ParamCoreGRec(recvar, body);
	}

	private ParamCoreGType parseGContinue(Map<RecVar, RecVar> rvs, boolean checkRecGuard, GContinue gc)
			throws ParamCoreSyntaxException
	{
		if (checkRecGuard)
		{
			throw new ParamCoreSyntaxException(gc.getSource(), "[param-core] Unguarded in recursion: " + gc);  // FIXME: too conservative, e.g., rec X . A -> B . rec Y . X
		}
		RecVar recvar = gc.recvar.toName();
		if (rvs.containsKey(recvar))
		{
			recvar = rvs.get(recvar);
		}
		return this.af.ParamCoreGRecVar(recvar);
	}

	// Parses message interactions as unary choices
	private ParamCoreGChoice parseGMessageTransfer(List<GInteractionNode> is, Map<RecVar, RecVar> rvs, GMessageTransfer gmt) throws ParamCoreSyntaxException 
	{
		ParamCoreMessage a = this.af.ParamCoreAction(parseOp(gmt), parsePayload(gmt));
		String srcName = parseSourceRole(gmt);
		String destName = parseDestinationRole(gmt);
		ParamCoreGActionKind kind;
		ParamRole src;
		ParamRole dest;
		if (gmt instanceof ParamGCrossMessageTransfer)
		{
			ParamGCrossMessageTransfer cross = (ParamGCrossMessageTransfer) gmt;
			kind = ParamCoreGActionKind.CROSS_TRANSFER;
			/*src = af.ParamRole(srcName, new ParamRange(cross.srcRangeStart.toName(), cross.srcRangeEnd.toName()));
			dest = af.ParamRole(destName, new ParamRange(cross.destRangeStart.toName(), cross.destRangeEnd.toName()));*/
			src = af.ParamRole(srcName, new ParamRange(cross.srcRangeStart, cross.srcRangeEnd));
			dest = af.ParamRole(destName, new ParamRange(cross.destRangeStart, cross.destRangeEnd));
		}
		else if (gmt instanceof ParamGDotMessageTransfer)
		{
			ParamGDotMessageTransfer dot = (ParamGDotMessageTransfer) gmt;
			kind = ParamCoreGActionKind.DOT_TRANSFER;
			/*src = af.ParamRole(srcName, new ParamRange(dot.srcRangeStart.toName(), dot.srcRangeEnd.toName()));
			dest = af.ParamRole(destName, new ParamRange(dot.destRangeStart.toName(), dot.destRangeEnd.toName()));*/
			src = af.ParamRole(srcName, new ParamRange(dot.srcRangeStart, dot.srcRangeEnd));
			dest = af.ParamRole(destName, new ParamRange(dot.destRangeStart, dot.destRangeEnd));
		}
		else
		{
			/*src = af.ParamRole(srcName, 1, 1); 
			dest = af.ParamRole(destName, 1, 1);*/
			throw new ParamCoreSyntaxException(gmt.getSource(), "[param-core] Not supported: " + gmt.getClass());
		}
		return parseGSimpleInteractionNode(is, rvs, src, kind, a, dest);
	}

	// Duplicated from parseGMessageTransfer (MessageTransfer and ConnectionAction have no common

	private ParamCoreGChoice parseGSimpleInteractionNode(
			List<GInteractionNode> is, Map<RecVar, RecVar> rvs, 
			ParamRole src, ParamCoreGActionKind kind, ParamCoreMessage a, ParamRole dest) throws ParamCoreSyntaxException 
	{
		if (src.equals(dest))
		{
			throw new RuntimeException("[param-core] Shouldn't get in here (self-communication): " + src + ", " + dest);
		}
		
		ParamCoreGType cont = parseSeq(is.subList(1, is.size()), rvs, false, false);  // Subseqeuent choice/rec is guarded by (at least) this action
		return this.af.ParamCoreGChoice(src, kind, dest, Stream.of(a).collect(Collectors.toMap(x -> x, x -> cont)));
	}

	private Op parseOp(GMessageTransfer gmt) throws ParamCoreSyntaxException
	{
		return parseOp(gmt.msg);
	}

	private Op parseOp(MessageNode mn) throws ParamCoreSyntaxException
	{
		if (!mn.isMessageSigNode())
		{
			throw new ParamCoreSyntaxException(mn.getSource(), " [param-core] Message sig names not supported: " + mn);  // TODO: MessageSigName
		}
		MessageSigNode msn = ((MessageSigNode) mn);
		return msn.op.toName();
	}

	private Payload parsePayload(GMessageTransfer gmt) throws ParamCoreSyntaxException
	{
		return parsePayload(gmt.msg);
	}

	private Payload parsePayload(MessageNode mn) throws ParamCoreSyntaxException
	{
		if (!mn.isMessageSigNode())
		{
			throw new ParamCoreSyntaxException(mn.getSource(), " [param-core] Message sig names not supported: " + mn);  // TODO: MessageSigName
		}
		return ((MessageSigNode) mn).toMessage().payload;
	}

	private String parseSourceRole(GMessageTransfer gmt)
	{
		return parseSourceRole(gmt.src);
	}

	private String parseSourceRole(RoleNode rn)
	{
		return rn.toString();
	}
	
	private String parseDestinationRole(GMessageTransfer gmt) throws ParamCoreSyntaxException
	{
		if (gmt.getDestinations().size() > 1)
		{
			throw new ParamCoreSyntaxException(gmt.getSource(), " [TODO] Multicast not supported: " + gmt);
		}
		return parseDestinationRole(gmt.getDestinations().get(0));
	}

	private String parseDestinationRole(RoleNode rn) throws ParamCoreSyntaxException
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
