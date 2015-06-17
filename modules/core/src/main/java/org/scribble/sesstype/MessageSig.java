package org.scribble.sesstype;

import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.kind.SigKind;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Op;

// FIXME: rename to UnscopedMessageSignature -- no: scope now here
public class MessageSig implements Message
{
	//public final Scope scope;  // Maybe scope should be built into the operator
	public final Op op;
	//public final List<PayloadType> payload;
	public final Payload payload;
	
	//public MessageSignature(Scope scope, Operator op, List<PayloadType> payload)
	//public MessageSignature(Scope scope, Operator op, Payload payload)
	public MessageSig(Op op, Payload payload)
	{
		//this.scope = scope;
		this.op = op;
		this.payload = payload;
	}

	@Override
	public Kind getKind()
	{
		return SigKind.KIND;
	}

	@Override
	public MessageId getId()
	{
		return this.op;
	}

	/*//@Override
	public Scope getScope()
	{
		return this.scope;
	}*/

	/*@Override
	public ScopedMessage toScopedMessage(Scope scope)
	{
		return new ScopedMessageSignature(scope, this.op, this.payload);
	}

	@Override
	public KindEnum getKindEnum()
	{
		return KindEnum.SIG;
	}*/

	@Override
	public int hashCode()
	{
		int hash = 3187;
		//hash = 31 * hash + this.scope.hashCode();
		hash = 31 * hash + this.op.hashCode();
		hash = 31 * hash + this.payload.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		//if (o == null || this.getClass() != o.getClass())
		if (!(o instanceof MessageSig))
		{
			return false;
		}
		MessageSig sig = (MessageSig) o;
		return //this.scope.equals(sig.scope) &&
				this.op.equals(sig.op) && this.payload.equals(sig.payload);
	}
	
	@Override
	public String toString()
	{
		return //this.scope.toString() + "." +
				this.op.toString() + this.payload.toString();
	}
	
	/*@Override
	public boolean isParameter()
	{
		return false;
	}*/
}
