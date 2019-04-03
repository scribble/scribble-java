package org.scribble.lang;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.type.Message;
import org.scribble.type.MessageSig;
import org.scribble.type.Payload;
import org.scribble.type.kind.ProtocolKind;
import org.scribble.type.name.DataType;
import org.scribble.type.name.GDelegationType;
import org.scribble.type.name.MemberName;
import org.scribble.type.name.MessageSigName;
import org.scribble.type.name.PayloadElemType;
import org.scribble.type.name.ProtocolName;
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
	public Set<Role> getRoles()
	{
		// Inlcudes self
		return Stream.of(this.src, this.dst).collect(Collectors.toSet());
	}

	@Override
	public List<ProtocolName<K>> getProtoDependencies()
	{
		return Collections.emptyList();
	}

	@Override
	public List<MemberName<?>> getNonProtoDependencies()
	{
		List<MemberName<?>> res = new LinkedList<>();
		if (this.msg.isMessageSigName())
		{
			res.add((MessageSigName) this.msg);
		}
		else //if (this.msg.isMessageSig)
		{
			Payload pay = ((MessageSig) this.msg).payload;
			for (PayloadElemType<?> p : pay.elems)
			{
				if (p.isDataType())
				{
					res.add((DataType) p);
				}
				else if (p.isGDelegationType())  // TODO FIXME: should be projected to local name
				{
					res.add(((GDelegationType) p).getGlobalProtocol());
				}
			}
		}
		return res;
	}
	
	@Override
	public MessageTransfer<K> substitute(Substitutions subs)
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
