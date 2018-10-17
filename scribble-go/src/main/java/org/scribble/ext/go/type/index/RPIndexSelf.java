package org.scribble.ext.go.type.index;

// Does not occur in RPRoleVariant, only RPIndexedRole
public class RPIndexSelf extends RPIndexVar
{
	public static final RPIndexSelf SELF = new RPIndexSelf();
	
	private RPIndexSelf()
	{
		super("self");
	}

	@Override
	public RPIndexExpr minimise(int self)
	{
		// FIXME: factor out constant
		return (self < 1) ? this : RPIndexFactory.ParamIntVal(self);
	}
	
	@Override
	public String toString()
	{
		return this.name; 
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof RPIndexSelf))
		{
			return false;
		}
		return super.equals(this);
	}
	
	@Override
	protected boolean canEqual(Object o)
	{
		return o instanceof RPIndexSelf;
	}

	@Override
	public int hashCode()
	{
		int hash = 2099;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.name.hashCode();
		return hash;
	}
}
