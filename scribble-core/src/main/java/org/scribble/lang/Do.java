package org.scribble.lang;

import java.util.Collections;
import java.util.List;

import org.scribble.type.kind.ProtocolKind;
import org.scribble.type.name.Role;

public abstract class Do<K extends ProtocolKind> implements SessType<K>
{
	public final List<Role> roles;  // Ordered role args; pre: size > 1
	//public final List<NonRoleArg> args;  // CHECKME

	public Do(List<Role> roles)
	{
		this.roles = Collections.unmodifiableList(roles);
	}

	@Override
	public int hashCode()
	{
		int hash = 193;
		hash = 31 * hash + this.roles.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof Do))
		{
			return false;
		}
		Do<?> them = (Do<?>) o;
		return them.canEquals(this) && this.roles.equals(them.roles);
	}
}
