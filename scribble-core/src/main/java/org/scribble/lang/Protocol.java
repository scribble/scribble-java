package org.scribble.lang;

import java.util.Collections;
import java.util.List;

import org.scribble.ast.ProtocolDecl;
import org.scribble.type.kind.ProtocolKind;
import org.scribble.type.name.Role;

public abstract class Protocol<K extends ProtocolKind> extends SessTypeBase<K>
		implements SessType<K>
{
	public final List<Role> roles;  // Ordered role params; pre: size >= 2
	//public final List<NonRoleParamNode<?>> params;  // CHECKME
	public final Seq<K> body;

	public Protocol(ProtocolDecl<K> source, List<Role> roles, //List<?> params, 
			Seq<K> body)
	{
		super(source);
		this.roles = Collections.unmodifiableList(roles);
		this.body = body;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 7;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.roles.hashCode();
		hash = 31 * hash + this.body.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof Protocol))
		{
			return false;
		}
		Protocol<?> them = (Protocol<?>) o;
		return super.equals(this)  // Does canEquals
				&& this.roles.equals(them.roles) && this.body.equals(them.body);
	}

}
