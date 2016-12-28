package org.scribble.ext.f17.ast.local;

import org.scribble.ext.f17.ast.F17RecVar;
import org.scribble.sesstype.name.RecVar;


public class F17LRecVar extends F17RecVar implements F17LType
{
	public F17LRecVar(RecVar var)
	{
		super(var);
	}
	
	// FIXME: hashCode/equals
	
	@Override
	public F17LRecVar copy()
	{
		return this;
	}
}
