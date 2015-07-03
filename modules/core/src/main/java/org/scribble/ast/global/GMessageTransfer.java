package org.scribble.ast.global;

import java.util.List;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.Constants;
import org.scribble.ast.MessageNode;
import org.scribble.ast.MessageTransfer;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Global;
import org.scribble.util.ScribUtil;

public class GMessageTransfer extends MessageTransfer<Global> implements GSimpleInteractionNode
{
	public GMessageTransfer(RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		super(src, msg, dests);
	}

	@Override
	protected GMessageTransfer copy()
	{
		return new GMessageTransfer(this.src, this.msg, this.dests);
	}
	
	@Override
	public GMessageTransfer clone()
	{
		RoleNode src = this.src.clone();
		MessageNode msg = this.msg.clone();
		List<RoleNode> dests = ScribUtil.cloneList(this.dests);
		return AstFactoryImpl.FACTORY.GMessageTransfer(src, msg, dests);
	}

	@Override
	protected GMessageTransfer reconstruct(RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		ScribDel del = del();
		GMessageTransfer gmt = new GMessageTransfer(src, msg, dests);
		gmt = (GMessageTransfer) gmt.del(del);
		return gmt;
	}

	@Override
	public String toString()
	{
		String s = this.msg + " " + Constants.FROM_KW + " " + this.src + " " + Constants.TO_KW + " " + this.dests.get(0);
		for (RoleNode dest : this.dests.subList(1, this.dests.size()))
		{
			s += ", " + dest;
		}
		return s + ";";
	}
}
