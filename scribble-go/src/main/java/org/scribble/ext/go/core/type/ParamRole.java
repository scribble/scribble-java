package org.scribble.ext.go.core.type;

import org.scribble.type.name.Role;

// FIXME: make distinct kind?  i.e., don't extend Role? -- yes: ParamRole is not a Role (Role is actually a ParamRole)
public class ParamRole extends Role
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final ParamRange range;
	
	public ParamRole(String name, ParamRange range)
	{
		super(name);
		this.range = range;
	}
	
	// FIXME
	public Role getName()
	{
		return new Role(this.getLastElement());
	}
	
	@Override
	public int hashCode()
	{
		int hash = 7121;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.range.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof ParamRole))
		{
			return false;
		}
		ParamRole them = (ParamRole) obj;
		return super.equals(them)  // Does canEqual
				&& this.range.equals(them.range);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof ParamRole;
	}

	@Override
	public String toString()
	{
		return super.toString() + this.range;
	}
}
