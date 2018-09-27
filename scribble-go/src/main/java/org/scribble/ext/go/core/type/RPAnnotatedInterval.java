package org.scribble.ext.go.core.type;

import org.scribble.ext.go.type.index.RPForeachVar;
import org.scribble.ext.go.type.index.RPIndexExpr;

public class RPAnnotatedInterval extends RPInterval
{
	public final RPForeachVar var;  // FIXME: generally deprecate RPForeachVar for RPIndexVar?
	
	public RPAnnotatedInterval(RPForeachVar var, RPIndexExpr start, RPIndexExpr end)
	{
		super(start, end);
		this.var = var;
	}

	@Override
	public String toString()
	{
		return "[" + this.var + ":" + this.start + ((this.start == this.end) ? "" : "," + this.end) + "]";
	}
	
	@Override
	public int hashCode()
	{
		int hash = 3119;
		hash = 31 * hash + this.var.hashCode();
		hash = 31 * hash + super.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof RPAnnotatedInterval))
		{
			return false;
		}
		RPAnnotatedInterval them = (RPAnnotatedInterval) obj;
		return super.equals(them)  // Does canEqual
				&& this.var.equals(them.var);
	}
	
	@Override
	public boolean canEqual(RPInterval o)
	{
		return o instanceof RPAnnotatedInterval;
	}
}
