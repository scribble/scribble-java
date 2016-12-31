package org.scribble.ext.f17.ast.global;

import java.util.Set;

import org.scribble.ext.f17.ast.F17RecVar;
import org.scribble.ext.f17.main.F17Exception;
import org.scribble.sesstype.name.RecVar;
import org.scribble.sesstype.name.Role;

	
// FIXME: hashCode/equals
public class F17GRecVar extends F17RecVar implements F17GType
{
	public F17GRecVar(RecVar var)
	{
		super(var);
	}

	/*@Override
	public F17GType onceUnfolding()
	{
		throw new RuntimeException("[f17] Shouldn't get here: " + this);
	}*/

	@Override
	public void checkRoleEnabling(Set<Role> enabled) throws F17Exception
	{
		//throw new RuntimeException("[f17] Shouldn't get here: " + this);
	}
}
