package org.scribble.lang;

import org.scribble.type.Message;
import org.scribble.type.kind.ProtocolKind;
import org.scribble.type.name.Role;

public abstract class MessageTransfer<K extends ProtocolKind>
		extends STypeBase<K> implements SType<K>
{
	public final Role src;
	public final Message msg;
	public final Role dst;

	public MessageTransfer(org.scribble.ast.MessageTransfer<K> source, Role src,
			Message msg, Role dst)
	{
		super(source);
		this.src = src;
		this.msg = msg;
		this.dst = dst;
	}
	
	public abstract MessageTransfer<K> reconstruct(
			org.scribble.ast.MessageTransfer<K> source, Role src, Message msg,
			Role dst);

	@Override
	public MessageTransfer<K> substitute(Substitutions<Role> subs)
	{
		return reconstruct(getSource(), subs.apply(this.src), this.msg,
				subs.apply(this.dst));
	}

	@Override
	public MessageTransfer<K> getInlined(STypeInliner i)
	{
		return this;
	}
	
	@Override
	public org.scribble.ast.MessageTransfer<K> getSource()
	{
		return (org.scribble.ast.MessageTransfer<K>) super.getSource();
	}
	
	@Override
	public String toString()
	{
		return this.msg + " from " + this.src + " to " + this.dst + ";";
	}
	
	@Override
	public int hashCode()
	{
		int hash = 17;
		hash = 31 * hash + super.hashCode();
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
		return super.equals(this)  // Does canEquals
				&& this.src.equals(them.src) && this.msg.equals(them.msg)
				&& this.dst.equals(them.dst);
	}
}
