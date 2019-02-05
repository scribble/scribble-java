package org.scribble.lang;

import org.scribble.type.Message;
import org.scribble.type.kind.ProtocolKind;
import org.scribble.type.name.Role;

public abstract class MessageTransfer<K extends ProtocolKind>
		implements SessType<K>
{
	public final Role src;
	public final Message msg;
	public final Role dst;

	public MessageTransfer(Role src, Message msg, Role dst)
	{
		this.src = src;
		this.msg = msg;
		this.dst = dst;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 17;
		hash = 31 * hash + this.src.hashCode();
		hash = 31 * hash + this.msg.hashCode();
		hash = 31 * hash + this.dst.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof MessageTransfer))
		{
			return false;
		}
		MessageTransfer<?> them = (MessageTransfer<?>) o;
		return them.canEquals(this) && this.src.equals(them.src)
				&& this.msg.equals(them.msg) && this.dst.equals(them.dst);
	}
}
