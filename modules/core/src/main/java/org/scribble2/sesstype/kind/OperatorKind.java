package org.scribble2.sesstype.kind;

import java.io.Serializable;

public class OperatorKind extends Kind implements Serializable
{
	private static final long serialVersionUID = 1L;

	public static final OperatorKind KIND = new OperatorKind();
	
	protected OperatorKind()
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
		if (!(o instanceof OperatorKind))
		{
			return false;
		}
		return true;
	}
}
