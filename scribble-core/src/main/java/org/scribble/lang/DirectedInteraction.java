package org.scribble.lang;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.type.Message;
import org.scribble.type.kind.ProtocolKind;
import org.scribble.type.name.MemberName;
import org.scribble.type.name.Role;

// Besides directed-ness, also features a Message
public abstract class DirectedInteraction<K extends ProtocolKind>
		extends BasicInteraction<K>
{
	public final Role src;
	public final Message msg;
	public final Role dst;

	public DirectedInteraction(org.scribble.ast.DirectedInteraction<K> source,
			Role src, Message msg, Role dst)
	{
		super(source);
		this.src = src;
		this.msg = msg;
		this.dst = dst;
	}
	
	public abstract DirectedInteraction<K> reconstruct(
			org.scribble.ast.DirectedInteraction<K> source, Role src, Message msg,
			Role dst);

	@Override
	public Set<Role> getRoles()
	{
		// Includes self
		return Stream.of(this.src, this.dst).collect(Collectors.toSet());
	}
	
	@Override
	public DirectedInteraction<K> substitute(Substitutions subs)
	{
		Message msg = this.msg;
		if (msg instanceof MemberName)
		{
			MemberName<?> n = (MemberName<?>) msg;
			if (subs.hasArg(n))
			{
				msg = (Message) subs.subsArg(n);
			}
		}
		return reconstruct(getSource(), subs.subsRole(this.src), msg,
				subs.subsRole(this.dst));
	}
	
	@Override
	public org.scribble.ast.DirectedInteraction<K> getSource()
	{
		return (org.scribble.ast.DirectedInteraction<K>) super.getSource();
	}
	
	@Override
	public int hashCode()
	{
		int hash = 10631;
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
		if (!(o instanceof DirectedInteraction))
		{
			return false;
		}
		DirectedInteraction<?> them = (DirectedInteraction<?>) o;
		return super.equals(this)  // Does canEquals
				&& this.src.equals(them.src) && this.msg.equals(them.msg)
				&& this.dst.equals(them.dst);
	}
}
