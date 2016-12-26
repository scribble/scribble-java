package org.scribble.f17.ast.global;

import org.scribble.f17.ast.F17RecVar;
import org.scribble.sesstype.name.RecVar;


public class F17GRecVar extends F17RecVar implements F17GType
{
	public F17GRecVar(RecVar var)
	{
		super(var);
	}
	
	// FIXME: hashCode/equals
}
