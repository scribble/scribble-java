package org.scribble.lang;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.scribble.ast.ProtocolDecl;
import org.scribble.type.kind.ProtocolKind;
import org.scribble.type.name.ProtocolName;
import org.scribble.type.name.Role;

public abstract class Protocol<K extends ProtocolKind, N extends ProtocolName<K>, B extends Seq<K>>
		extends SessTypeBase<K> 
{
	public final N fullname;
	public final List<Role> roles;  // Ordered role params; pre: size >= 2
	//public final List<NonRoleParamNode<?>> params;  // TODO
	public final B body;  // CHECKME: take "? extends Seq<K>" as generic param?

	public Protocol(ProtocolDecl<K> source, N fullname,
			List<Role> roles, // List<?> params,
			B body)
	{
		super(source);
		this.fullname = fullname;
		this.roles = Collections.unmodifiableList(roles);
		this.body = body;
	}
	
	public abstract Protocol<K, N, B> reconstruct(ProtocolDecl<K> source,
			N fullname, List<Role> roles, B body);
	
	/*public N getFullName()
	{
		return this.fullname;
	}*/

	@Override
	public String toString()
	{
		return " protocol " + this.fullname + "(" + this.roles.stream()
					.map(x -> x.toString()).collect(Collectors.joining(", ")) + ")"
				+ " {\n" + this.body + "\n}";
	}
	
	@Override
	public int hashCode()
	{
		int hash = 7;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.fullname.hashCode();
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
		Protocol<?, ?, ?> them = (Protocol<?, ?, ?>) o;
		return super.equals(this)  // Does canEquals
				&& this.fullname.equals(them.fullname) && this.roles.equals(them.roles)
				&& this.body.equals(them.body);
	}

}
