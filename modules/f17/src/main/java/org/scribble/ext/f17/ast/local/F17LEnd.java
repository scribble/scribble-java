package org.scribble.ext.f17.ast.local;

import org.scribble.ext.f17.ast.F17End;


public class F17LEnd extends F17End implements F17LType
{
	public static final F17LEnd END = new F17LEnd();
	
	private F17LEnd()
	{
		
	}
	
	@Override
	public F17LEnd copy()
	{
		return this;
	}
}
