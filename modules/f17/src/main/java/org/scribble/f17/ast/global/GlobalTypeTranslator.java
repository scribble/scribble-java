package org.scribble.f17.ast.global;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.scribble.ast.MessageSigNode;
import org.scribble.ast.context.ModuleContext;
import org.scribble.ast.global.GInteractionNode;
import org.scribble.ast.global.GMessageTransfer;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.ast.global.GProtocolDef;
import org.scribble.ast.global.GRecursion;
import org.scribble.del.global.GProtocolDefDel;
import org.scribble.f17.ast.F17AstFactory;
import org.scribble.f17.ast.global.action.F17GAction;
import org.scribble.f17.ast.global.action.F17GMessageTransfer;
import org.scribble.f17.main.F17Exception;
import org.scribble.main.JobContext;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.Op;
import org.scribble.sesstype.name.RecVar;
import org.scribble.sesstype.name.Role;


public class GlobalTypeTranslator
{
	private final F17AstFactory factory = new F17AstFactory();
	//private final LocalTypeParser ltp = new LocalTypeParser();

	public GlobalTypeTranslator()
	{

	}

	// merge is for projection of "delegation payload types"
	public F17GType translate(JobContext jc, ModuleContext mc, GProtocolDecl gpd) throws ScribbleException
	{
		GProtocolDef inlined = ((GProtocolDefDel) gpd.def.del()).getInlinedProtocolDef();
		return parseSeq(jc, mc, inlined.getBlock().getInteractionSeq().getInteractions());
	}
	
	/*private F17GType translate(JobContext jc, ModuleContext mc, GProtocolDef gpd) throws ScribbleException
	{
		return parseSeq(jc, mc, merge, gpd.getBlock().getInteractionSeq().getInteractions());
	}*/

	private F17GMessageTransfer parseGMessageTransfer(GMessageTransfer gmt) throws F17Exception 
	{
		Role src = gmt.src.toName();
		if (gmt.getDestinations().size() > 1)
		{
			throw new F17Exception(gmt.getSource(), " [TODO] Multicast not supported: " + gmt);
		}
		Role dest = gmt.getDestinations().get(0).toName();
		if (!gmt.msg.isMessageSigNode())
		{
			throw new F17Exception(gmt.msg.getSource(), " [f17] Message kind not supported: " + gmt.msg);
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
	
	private F17GType parseSeq(JobContext jc, ModuleContext mc, List<GInteractionNode> is) throws ScribbleException
	{
		//List<GInteractionNode> is = block.getInteractionSeq().getInteractions();
		if (is.isEmpty())
		{
			return this.factory.GEnd();
		}

		GInteractionNode first = is.get(0);
		if (first instanceof GMessageTransfer)
		{
			F17GMessageTransfer gmt = parseGMessageTransfer((GMessageTransfer) first);
			F17GType cont = parseSeq(jc, mc, is.subList(1, is.size()));
			Map<F17GAction, F17GType> cases = new HashMap<>();

			System.out.println("bbb: " + gmt);

			cases.put(gmt, cont);
			return this.factory.GChoice(cases);
		}
		/*else if (first instanceof GChoice)
		{
			if (is.size() > 1)
			{
				throw new F17Exception(is.get(1).getSource(), " [f17] Sequential composition after choice not supported: " + is.get(1));
			}
			GChoice gc = (GChoice) first; 
			/*List<GlobalType> parsed = gc.getBlocks().stream()
					.map((b) -> parseSeq(b.getInteractionSeq().getInteractions()))
					.collect(Collectors.toList());* /
			List<F17GType> parsed = new LinkedList<>();
			for (GProtocolBlock b : gc.getBlocks())
			{
				parsed.add(parseSeq(jobc, mainc, merge, b.getInteractionSeq().getInteractions()));  // "Directly" nested choice will still return a GlobalSend (which is really a choice; uniform global choice constructor is convenient)
			}
			Role src = null;
			Role dest = null;
			Map<Label, GlobalSendCase> cases = new HashMap<>();
			for (F17GType p : parsed)
			{
				if (!(p instanceof F17GSend))
				{
					throw new RuntimeException("[f17] Shouldn't get in here: " + p);
				}
				F17GSend tmp = (F17GSend) p;
				if (src == null)
				{
					src = tmp.src;
					dest = tmp.dest;
				}
				else
				{
					if (!dest.equals(tmp.dest))
					{
						throw new F17Exception(gc.getSource(), " [f17] Choice message from " + src + " to inconsistent recipients: " + tmp.dest + " and " + dest);
					}
				}
				tmp.cases.entrySet().forEach((e) -> cases.put(e.getKey(), e.getValue()));
			}
			return this.factory.GlobalSend(src, dest, cases);
		}*/
		else if (first instanceof GRecursion)
		{
			if (is.size() > 1)
			{
				throw new F17Exception(is.get(1).getSource(), " [f17] Sequential composition after recursion not supported: " + is.get(1));
			}
			GRecursion gr = (GRecursion) first;
			RecVar recvar = gr.recvar.toName();
			F17GType body = parseSeq(jc, mc, gr.getBlock().getInteractionSeq().getInteractions());
			return new F17GRec(recvar, body);
		}
		/*else if (first instanceof GContinue)
		{
			if (is.size() > 1)
			{
				throw new RuntimeException("[f17] Shouldn't get in here: " + is);
			}
			return this.factory.RecVar(((GContinue) first).recvar.toString());
		}*/
		else
		{
			throw new RuntimeException("[f17] Shouldn't get in here: " + first);
		}
	}
}
