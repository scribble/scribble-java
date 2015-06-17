package org.scribble.sesstype.kind;

//public abstract class ProtocolKind implements Kind
public class ProtocolKind extends Kind
{
	//public static final ProtocolKind KIND = new ProtocolKind();
	
	protected ProtocolKind()
	{

	}

	@Override
	public int hashCode()
	{
		return super.hashCode();
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (o == this)
		{
			return true;
		}
		if (!(o instanceof ProtocolKind))
		{
			return false;
		}
		return true;
	}
	
	@Override
	public String toString()
	{
		return this.getClass().toString();
	}
}
