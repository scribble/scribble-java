package org.scribble.ext.go.type.name;

import org.scribble.ext.go.type.kind.RPRoleParamKind;
import org.scribble.type.name.AbstractName;

// Currently used for both "actual params" and int literals -- cf. ParamRoleParamRoleNode, and isConstant
public class RPRoleParam extends AbstractName<RPRoleParamKind>
{
	private static final long serialVersionUID = 1L;

	protected RPRoleParam()
	{
		super(RPRoleParamKind.KIND);
	}

	public RPRoleParam(String text)
	{
		super(RPRoleParamKind.KIND, text);
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
		if (!(o instanceof RPRoleParam))
		{
			return false;
		}
		return super.equals(o);  // Does canEqual
	}
	
	public boolean canEqual(Object o)
	{
		return o instanceof RPRoleParam;
	}

	@Override
	public int hashCode()
	{
		int hash = 7187;
		hash = 31 * super.hashCode();
		return hash;
	}
}
