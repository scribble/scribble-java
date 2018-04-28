package org.scribble.ext.go.type.name;

import org.scribble.ext.go.type.index.RPIndexExpr;
import org.scribble.ext.go.type.kind.RPIndexedRoleKind;
import org.scribble.type.name.AbstractName;

// Currently used for both "actual params" and int literals -- cf. ParamRoleParamRoleNode, and isConstant
public class RPIndexedRole extends AbstractName<RPIndexedRoleKind>
{
	public final RPIndexExpr start;
	public final RPIndexExpr end;

	private static final long serialVersionUID = 1L;

	protected RPIndexedRole(RPIndexExpr start, RPIndexExpr end)
	{
		super(RPIndexedRoleKind.KIND);
		this.start = start; 
		this.end = end;
	}

	public RPIndexedRole(String text, RPIndexExpr start, RPIndexExpr end)
	{
		super(RPIndexedRoleKind.KIND, text);
		this.start = start; 
		this.end = end;
	}
	
	public boolean isSingleton()
	{
		return this.start.equals(this.end);
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof RPIndexedRole))
		{
			return false;
		}
		RPIndexedRole them = (RPIndexedRole) o;
		return super.equals(o)  // Does canEqual
				&& this.start.equals(them.start) && this.end.equals(them.end);
	}
	
	public boolean canEqual(Object o)
	{
		return o instanceof RPIndexedRole;
	}

	@Override
	public int hashCode()
	{
		int hash = 7187;
		hash = 31*hash + super.hashCode();
		hash = 31*hash + this.start.hashCode();
		hash = 31*hash + this.end.hashCode();
		return hash;
	}
}
