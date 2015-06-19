package org.scribble.ast.local;

import java.util.List;

import org.scribble.ast.Constants;
import org.scribble.ast.MessageNode;
import org.scribble.ast.MessageTransfer;
import org.scribble.ast.ScribNodeBase;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Local;

public class LReceive extends MessageTransfer<Local> implements LSimpleInteractionNode
{
	public LReceive(RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		super(src, msg, dests);
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new LReceive(this.src, this.msg, this.dests);
	}

	@Override
	protected LReceive reconstruct(RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		ScribDel del = del();
		LReceive lr = new LReceive(src, msg, dests);
		lr = (LReceive) lr.del(del);
		return lr;
	}

	@Override
	public String toString()
	{
		return this.msg + " " + Constants.FROM_KW + " " + this.src + ";";
	}
}
