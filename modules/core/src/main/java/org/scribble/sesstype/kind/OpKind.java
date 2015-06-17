package org.scribble.sesstype.kind;

import java.io.Serializable;

public class OpKind extends Kind implements Serializable
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
		return true;
	}
}
