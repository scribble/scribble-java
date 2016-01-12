package org.scribble.sesstype.name;

import org.scribble.sesstype.kind.AmbigKind;

public class AmbigName extends AbstractName<AmbigKind>
{
	private static final long serialVersionUID = 1L;
	
	public AmbigName(String text)
	{
		super(AmbigKind.KIND, text);
	}
	
	public MessageSigName toMessageSigName()
	{
		return new MessageSigName(getLastElement());
	}

	public DataType toDataType()
	{
		return new DataType(getLastElement());
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof AmbigName))
		{
			return false;
		}
		AmbigName n = (AmbigName) o;
		return n.canEqual(this) && super.equals(o);
	}
	
	public boolean canEqual(Object o)
	{
		return o instanceof AmbigName;
	}

	@Override
	public int hashCode()
	{
		int hash = 2753;
		hash = 31 * super.hashCode();
		return hash;
	}
}
