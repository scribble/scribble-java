package org.scribble.sesstype.name;

import org.scribble.sesstype.kind.Global;


public class GProtocolName extends ProtocolName<Global>
{
	private static final long serialVersionUID = 1L;

	public GProtocolName(ModuleName modname, ProtocolName<Global> membname)
	{
		super(Global.KIND, modname, membname);
	}
	
	public GProtocolName(String simpname)
	{
		super(Global.KIND, simpname);
	}

	@Override
	public GProtocolName getSimpleName()
	{
		return new GProtocolName(getLastElement());
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof GProtocolName))
		{
			return false;
		}
		GProtocolName n = (GProtocolName) o;
		return n.canEqual(this) && super.equals(o);
	}
	
	public boolean canEqual(Object o)
	{
		return o instanceof GProtocolName;
	}

	@Override
	public int hashCode()
	{
		int hash = 2777;
		hash = 31 * super.hashCode();
		return hash;
	}
}
