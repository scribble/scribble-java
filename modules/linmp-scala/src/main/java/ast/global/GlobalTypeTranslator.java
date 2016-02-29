package ast.global;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.scribble.ast.MessageSigNode;
import org.scribble.ast.global.GChoice;
import org.scribble.ast.global.GContinue;
import org.scribble.ast.global.GInteractionNode;
import org.scribble.ast.global.GMessageTransfer;
import org.scribble.ast.global.GProtocolDef;
import org.scribble.ast.global.GRecursion;

import ast.AstFactory;
import ast.PayloadType;
import ast.local.LocalTypeParser;
import ast.name.MessageLab;
import ast.name.RecVar;
import ast.name.Role;

public class GlobalTypeTranslator
{
	private final AstFactory factory = new AstFactory();
	private final LocalTypeParser ltp = new LocalTypeParser();
	
	public GlobalType translate(GProtocolDef gpd)
	{
		return parseSeq(gpd.getBlock().getInteractionSeq().getInteractions());
	}
	
	private GlobalType parseSeq(List<GInteractionNode> is)
	{
		//List<GInteractionNode> is = block.getInteractionSeq().getInteractions();
		if (is.isEmpty())
		{
			return this.factory.GlobalEnd();
		}
		else
		{
			GInteractionNode first = is.get(0);
			if (first instanceof GMessageTransfer)
			{
				GMessageTransfer gmt = (GMessageTransfer) first;
				Role src = this.factory.Role(gmt.src.toString());
				if (gmt.getDestinations().size() > 1)
				{
					throw new RuntimeException("TODO: " + gmt);
				}
				Role dest = this.factory.Role(gmt.getDestinations().get(0).toString());
				if (!gmt.msg.isMessageSigNode())
				{
					throw new RuntimeException("TODO: " + gmt);
				}
				MessageSigNode msn = ((MessageSigNode) gmt.msg);
				MessageLab lab = this.factory.MessageLab(msn.op.toString());
				PayloadType pay = null;
				if (msn.payloads.getElements().size() > 1)
				{
					throw new RuntimeException("TODO: " + gmt);
				}
				else if (!msn.payloads.getElements().isEmpty())
				{
					String tmp = msn.payloads.getElements().get(0).toString().trim();
					if (tmp.length() > 1 && tmp.startsWith("\"") && tmp.endsWith("\""))
					{
						tmp = tmp.substring(1, tmp.length() - 1);
						pay = this.ltp.parse(tmp);
						if (pay == null)
						{
							throw new RuntimeException("Shouldn't get in here: " + tmp);
						}
					}
					else
					{
						pay = this.factory.BaseType(tmp);
					}
				}
				GlobalType cont = parseSeq(is.subList(1, is.size()));
				Map<MessageLab, GlobalSendCase> cases = new HashMap<>();
				cases.put(lab, this.factory.GlobalSendCase(pay, cont));
				return this.factory.GlobalSend(src, dest, cases);
			}
			else if (first instanceof GChoice)
			{
				if (is.size() > 1)
				{
					throw new RuntimeException("TODO: " + is);
				}
				GChoice gc = (GChoice) first; 
				List<GlobalType> parsed = gc.getBlocks().stream()
						.map((b) -> parseSeq(b.getInteractionSeq().getInteractions()))
						.collect(Collectors.toList());
				Role src = null;
				Role dest = null;
				Map<MessageLab, GlobalSendCase> cases = new HashMap<>();
				for (GlobalType p : parsed)
				{
					if (!(p instanceof GlobalSend))
					{
						throw new RuntimeException("Shouldn't get in here: " + p);
					}
					GlobalSend tmp = (GlobalSend) p;
					if (src == null)
					{
						src = tmp.src;
						dest = tmp.dest;
					}
					tmp.cases.entrySet().forEach((e) -> cases.put(e.getKey(), e.getValue()));
				}
				return this.factory.GlobalSend(src, dest, cases);
			}
			else if (first instanceof GRecursion)
			{
				if (is.size() > 1)
				{
					throw new RuntimeException("TODO: " + is);
				}
				GRecursion gr = (GRecursion) first;
				RecVar recvar = this.factory.RecVar(gr.recvar.toString());
				GlobalType body = parseSeq(gr.getBlock().getInteractionSeq().getInteractions());
				return new GlobalRec(recvar, body);
			}
			else if (first instanceof GContinue)
			{
				if (is.size() > 1)
				{
					throw new RuntimeException("Shouldn't get in here: " + is);
				}
				return this.factory.RecVar(((GContinue) first).recvar.toString());
			}
			else
			{
				throw new RuntimeException("Shouldn't get in here: " + first);
			}
		}
	}
}
