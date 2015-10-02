package org.scribble.net;

import org.scribble.sesstype.name.Op;

public class ScribInterrupt extends ScribMessage
{
	private static final long serialVersionUID = 1L;
	
	public static final Op SCRIB_INTERR = new Op("__interr");

	public ScribInterrupt(Throwable t)
	{
		super(SCRIB_INTERR, new Object[] { t });
	}

	@Override
	public int hashCode()
	{
		int hash = 79;
		hash = 31 * hash + super.hashCode();
		return hash;
	}
	
	public boolean canEqual(Object o)
	{
		return o instanceof ScribInterrupt;
	}
}
