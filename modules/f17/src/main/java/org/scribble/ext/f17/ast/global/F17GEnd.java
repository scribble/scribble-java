package org.scribble.ext.f17.ast.global;

import java.util.Set;

import org.scribble.ext.f17.ast.F17End;
import org.scribble.ext.f17.main.F17Exception;
import org.scribble.sesstype.name.Role;


public class F17GEnd extends F17End implements F17GType
{
	public static final F17GEnd END = new F17GEnd();
	
	private F17GEnd()
	{
		
	}

	/*@Override
	public F17GType onceUnfolding()
	{
		return this;
	}*/

	@Override
	public void checkRoleEnabling(Set<Role> enabled) throws F17Exception
	{
		
	}
}
