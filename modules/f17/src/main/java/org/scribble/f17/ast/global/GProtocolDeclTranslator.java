package org.scribble.f17.ast.global;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.scribble.ast.MessageSigNode;
import org.scribble.ast.context.ModuleContext;
import org.scribble.ast.global.GChoice;
import org.scribble.ast.global.GContinue;
import org.scribble.ast.global.GInteractionNode;
import org.scribble.ast.global.GMessageTransfer;
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.ast.global.GProtocolDef;
import org.scribble.ast.global.GRecursion;
import org.scribble.del.global.GProtocolDefDel;
import org.scribble.f17.ast.F17AstFactory;
import org.scribble.f17.ast.global.action.F17GAction;
import org.scribble.f17.ast.global.action.F17GMessageTransfer;
import org.scribble.f17.main.F17Exception;
import org.scribble.main.JobContext;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.Op;
import org.scribble.sesstype.name.RecVar;
import org.scribble.sesstype.name.Role;


public class GProtocolDeclTranslator
{
	private final F17AstFactory factory = new F17AstFactory();

	public GProtocolDeclTranslator()
	{

	}

	// merge is for projection of "delegation payload types"
	public F17GType translate(JobContext jc, ModuleContext mc, GProtocolDecl gpd) throws F17Exception
	{
		GProtocolDef inlined = ((GProtocolDefDel) gpd.def.del()).getInlinedProtocolDef();
		return parseSeq(jc, mc, inlined.getBlock().getInteractionSeq().getInteractions(), false, false);
	}
	
	/*private F17GType translate(JobContext jc, ModuleContext mc, GProtocolDef gpd) throws ScribbleException
	{
		return parseSeq(jc, mc, merge, gpd.getBlock().getInteractionSeq().getInteractions());
	}*/
	
	private F17GType parseSeq(JobContext jc, ModuleContext mc, List<GInteractionNode> is,
			boolean checkChoiceGuard, boolean checkRecGuard) throws F17Exception
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
			F17GType cont = parseSeq(jc, mc, is.subList(1, is.size()), false, false);
			Map<F17GAction, F17GType> cases = new HashMap<>();
			cases.put(gmt, cont);
			return this.factory.GChoice(cases);
		}
		else
		{
			if (checkChoiceGuard)
			{
				throw new F17Exception(first.getSource(), "[f17] Unguarded in choice case: " + first);
			}
			if (is.size() > 1)
			{
				throw new F17Exception(is.get(1).getSource(), "[f17] Bad sequential composition after: " + first);
			}

			if (first instanceof GChoice)
			{
				/*if (checkRecGuard)
				{
					throw new F17Exception(first.getSource(), "[f17] Unguarded in choice case (2): " + first);
				}*/

				GChoice gc = (GChoice) first; 
				List<F17GType> parsed = new LinkedList<>();
				for (GProtocolBlock b : gc.getBlocks())
				{
					parsed.add(parseSeq(jc, mc, b.getInteractionSeq().getInteractions(), true, checkRecGuard));  // "Directly" nested choice will still return a GlobalSend (which is really a choice; uniform global choice constructor is convenient)
				}
				Map<F17GAction, F17GType> cases = new HashMap<>();
				for (F17GType p : parsed)
				{
					if (!(p instanceof F17GChoice))
					{
						throw new RuntimeException("[f17] Shouldn't get in here: " + p);
					}
					F17GChoice tmp = (F17GChoice) p;
					tmp.cases.entrySet().forEach((e) -> cases.put(e.getKey(), e.getValue()));
				}
				return this.factory.GChoice(cases);
			}
			else if (first instanceof GRecursion)
			{
				GRecursion gr = (GRecursion) first;
				RecVar recvar = gr.recvar.toName();
				F17GType body = parseSeq(jc, mc, gr.getBlock().getInteractionSeq().getInteractions(), checkChoiceGuard, true);
				return new F17GRec(recvar, body);
			}
			else if (first instanceof GContinue)
			{
				if (checkRecGuard)
				{
					throw new F17Exception(first.getSource(), "[f17] Unguarded in recursion: " + first);  // FIXME: conservative, e.g., rec X . A -> B . rec Y . X
				}

				return this.factory.GRecVar(((GContinue) first).recvar.toName());
			}
			else
			{
				throw new RuntimeException("[f17] Shouldn't get in here: " + first);
			}
		}
	}

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
}
