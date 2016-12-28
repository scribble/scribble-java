package org.scribble.ext.f17.ast;

import org.scribble.sesstype.name.RecVar;


public abstract class F17RecVar implements F17Type
{
	public final RecVar var;
	
	public F17RecVar(RecVar var)
	{
		this.var = var;
	}

	@Override 
	public String toString()
	{
		return this.var.toString();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof F17RecVar))
		{
			return false;
		}
		F17RecVar them = (F17RecVar) obj;
		return them.canEquals(this) && this.var.equals(them.var);
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof F17RecVar;
	}

	@Override
	public int hashCode()
	{
		return this.var.hashCode();
	}
}
