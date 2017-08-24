package org.scribble.ext.go.type.kind;

import java.io.Serializable;

import org.scribble.type.kind.AbstractKind;

public class ParamRoleParamKind extends AbstractKind implements Serializable
{
	private static final long serialVersionUID = 1L;

	public static final ParamRoleParamKind KIND = new ParamRoleParamKind();
	
	protected ParamRoleParamKind()
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
		if (!(o instanceof ParamRoleParamKind))
		{
			return false;
		}
		return ((ParamRoleParamKind) o).canEqual(this);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof ParamRoleParamKind;
	}
}
