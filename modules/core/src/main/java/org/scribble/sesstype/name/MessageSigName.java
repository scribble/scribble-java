package org.scribble.sesstype.name;

import org.scribble.sesstype.Message;
import org.scribble.sesstype.kind.SigKind;


// The name of a declared (imported) message signature member
public class MessageSigName extends MemberName<SigKind> implements Message, MessageId<SigKind>
{
	private static final long serialVersionUID = 1L;
	
	public MessageSigName(ModuleName modname, MessageSigName simplename)
	{
		super(SigKind.KIND, modname, simplename);
	}

	public MessageSigName(String simplename)
	{
		super(SigKind.KIND, simplename);
	}

	@Override
	public SigKind getKind()
	{
		return SigKind.KIND;  // Same as this.kind
	}

	@Override
	public MessageSigName getSimpleName()
	{
		return new MessageSigName(getLastElement());
	}
	
	@Override 
	public MessageId<SigKind> getId()
	{
		return this;  // FIXME: should be resolved to a canonical name
	}

	@Override
	public boolean isMessageSigName()
	{
		return true;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof MessageSigName))
		{
			return false;
		}
		MessageSigName n = (MessageSigName) o;
		return n.canEqual(this) && super.equals(o);
	}
	
	public boolean canEqual(Object o)
	{
		return o instanceof MessageSigName;
	}

	@Override
	public int hashCode()
	{
		int hash = 2791;
		hash = 31 * super.hashCode();
		return hash;
	}
}
