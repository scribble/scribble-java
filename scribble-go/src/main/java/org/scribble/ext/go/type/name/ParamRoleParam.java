package org.scribble.ext.go.type.name;

import org.scribble.ext.go.type.kind.ParamRoleParamKind;
import org.scribble.type.name.AbstractName;

// Currently used for both "actual params" and int literals -- cf. ParamRoleParamRoleNode, and isConstant
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
	
	public boolean isConstant()
	{
		try
		{
			Integer.valueOf(this.toString());
			return true;
		}
		catch (NumberFormatException e)
		{
			return false;
		}
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
		return super.equals(o);  // Does canEqual
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
