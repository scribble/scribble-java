package org.scribble.ext.f17.ast.global;

import java.util.Set;

import org.scribble.ext.f17.ast.F17Type;
import org.scribble.ext.f17.main.F17Exception;
import org.scribble.sesstype.name.Role;


//public abstract class F17GType extends F17Type
public interface F17GType extends F17Type
{
	//F17GType onceUnfolding();
	void checkRoleEnabling(Set<Role> enabled) throws F17Exception;
}
