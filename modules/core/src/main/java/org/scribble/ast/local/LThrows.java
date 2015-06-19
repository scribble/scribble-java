package org.scribble.ast.local;

import java.util.List;

import org.scribble.ast.MessageNode;
import org.scribble.ast.name.simple.RoleNode;

public class LThrows extends LInterrupt
{
	protected LThrows(RoleNode src, List<MessageNode> msgs)
	{
		super(src, msgs);
		// TODO Auto-generated constructor stub
	}
	/*public LocalThrows(CommonTree ct, RoleNode src, List<MessageNode> msgs, List<RoleNode> dests)
	{
		this(ct, src, msgs, dests, null, null);
	}

	protected LocalThrows(CommonTree ct, RoleNode src, List<MessageNode> msgs, List<RoleNode> dests, GlobalInterruptContext gicontext, Env env)
	{
		super(ct, src, msgs, dests, gicontext, env);
	}

	@Override
	protected LocalThrows reconstruct(CommonTree ct, RoleNode src, List<MessageNode> msgs, List<RoleNode> dests, GlobalInterruptContext icontext, Env env)
	{
		return new LocalThrows(ct, src, msgs, dests, icontext, env);
	}
	
	/*@Override
	public LocalThrows visitChildren(NodeVisitor nv) throws ScribbleException
	{
		LocalInterrupt interr = super.visitChildren(nv);
		//return new LocalThrows(interr.ct, interr.src, interr.msgs, interr.dests, (GlobalInterruptContext) interr.getContext());
		return reconstruct(interr.ct, interr.src, interr.msgs, interr.dests, (GlobalInterruptContext) interr.getContext(), getEnv());
	}* /
	
	@Override
	public String toString()
	{
		String s = AntlrConstants.THROWS_KW + " " + this.msgs.get(0).toString();
		for (MessageNode msg : this.msgs.subList(1, this.msgs.size()))
		{
			s += ", " + msg;
		}
		s += " " + AntlrConstants.TO_KW + " " + this.dests.get(0);
		for (RoleNode dest : this.dests.subList(1, this.dests.size()))
		{
			s += ", " + dest.toName();
		}
		return s + ";";
	}
	
	/*@Override
	public void toGraph(GraphBuilder gb)
	{
		throw new RuntimeException("Shouldn't get in here.");
	}*/
}
