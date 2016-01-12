package org.scribble.sesstype;

import org.scribble.sesstype.kind.OpKind;
import org.scribble.sesstype.kind.SigKind;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Op;

public class MessageSig implements Message
{
	public final Op op;
	public final Payload payload;
	
	public MessageSig(Op op, Payload payload)
	{
		this.op = op;
		this.payload = payload;
	}

	@Override
	public SigKind getKind()
	{
		return SigKind.KIND;
	}

	@Override
	public MessageId<OpKind> getId()
	{
		return this.op;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof MessageSig))
		{
			return false;
		}
		MessageSig sig = (MessageSig) o;
		return this.op.equals(sig.op) && this.payload.equals(sig.payload);
	}

	@Override
	public int hashCode()
	{
		int hash = 3187;
		hash = 31 * hash + this.op.hashCode();
		hash = 31 * hash + this.payload.hashCode();
		return hash;
	}
	
	@Override
	public String toString()
	{
		return this.op.toString() + this.payload.toString();
	}
}
