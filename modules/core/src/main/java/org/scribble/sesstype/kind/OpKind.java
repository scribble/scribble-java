package org.scribble.sesstype.kind;

import java.io.Serializable;

public class OpKind extends AbstractKind implements MessageIdKind, Serializable
{
	private static final long serialVersionUID = 1L;

	public static final OpKind KIND = new OpKind();
	
	protected OpKind()
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
		if (!(o instanceof OpKind))
		{
			return false;
		}
		return ((OpKind) o).canEqual(this);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof OpKind;
	}
}
