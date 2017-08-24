package org.scribble.ext.go.type.name;

import org.scribble.ext.go.type.kind.ParamRoleParamKind;
import org.scribble.type.name.AbstractName;

public class ParamRoleParam extends AbstractName<ParamRoleParamKind>
{
	private static final long serialVersionUID = 1L;

	protected ParamRoleParam()
	{
		super(ParamRoleParamKind.KIND);
	}

	public ParamRoleParam(String text)
	{
		super(ParamRoleParamKind.KIND, text);
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof ParamRoleParam))
		{
			return false;
		}
		ParamRoleParam n = (ParamRoleParam) o;
		return n.canEqual(this) && super.equals(o);
	}
	
	public boolean canEqual(Object o)
	{
		return o instanceof ParamRoleParam;
	}

	@Override
	public int hashCode()
	{
		int hash = 7187;
		hash = 31 * super.hashCode();
		return hash;
	}
}
