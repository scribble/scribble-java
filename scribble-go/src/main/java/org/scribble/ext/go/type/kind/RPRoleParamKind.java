package org.scribble.ext.go.type.kind;

import java.io.Serializable;

import org.scribble.type.kind.AbstractKind;

public class RPRoleParamKind extends AbstractKind implements Serializable
{
	private static final long serialVersionUID = 1L;

	public static final RPRoleParamKind KIND = new RPRoleParamKind();
	
	protected RPRoleParamKind()
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
		if (!(o instanceof RPRoleParamKind))
		{
			return false;
		}
		return ((RPRoleParamKind) o).canEqual(this);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof RPRoleParamKind;
	}
}
