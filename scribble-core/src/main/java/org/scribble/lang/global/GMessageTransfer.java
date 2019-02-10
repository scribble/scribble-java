package org.scribble.lang.global;

import java.util.Deque;

import org.scribble.lang.MessageTransfer;
import org.scribble.lang.Substitutions;
import org.scribble.type.Message;
import org.scribble.type.SubprotoSig;
import org.scribble.type.kind.Global;
import org.scribble.type.name.Role;

public class GMessageTransfer extends MessageTransfer<Global>
		implements GType
{

	public GMessageTransfer(org.scribble.ast.MessageTransfer<Global> source,
			Role src, Message msg, Role dst)
	{
		super(source, src, msg, dst);
	}

	@Override
	public GMessageTransfer reconstruct(
			org.scribble.ast.MessageTransfer<Global> source, Role src, Message msg,
			Role dst)
	{
		return new GMessageTransfer(source, src, msg, dst);
	}

	@Override
	public GMessageTransfer substitute(Substitutions<Role> subs)
	{
		return (GMessageTransfer) super.substitute(subs);
	}

	@Override
	public GMessageTransfer getInlined(GTypeTranslator t, Deque<SubprotoSig> stack)
	{
		return (GMessageTransfer) super.getInlined(t, stack);
	}
	
	@Override
	public int hashCode()
	{
		int hash = 1481;
		hash = 31 * hash + super.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof GMessageTransfer))
		{
			return false;
		}
		return super.equals(o);  // Does canEquals
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof GMessageTransfer;
	}

}
