package org.scribble.lang;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.scribble.type.kind.ProtocolKind;
import org.scribble.type.name.ProtocolName;
import org.scribble.type.name.Role;

public abstract class Do<K extends ProtocolKind, N extends ProtocolName<K>>
		extends SessTypeBase<K> implements SessType<K>
{
	public final N proto;  // Currently disamb'd to fullname by GTypeTranslator (see GDoDel::translate)
	public final List<Role> roles;  // Ordered role args; pre: size > 1
	//public final List<NonRoleArg> args;  // CHECKME

	public Do(org.scribble.ast.Do<K> source, N proto,
			List<Role> roles)
	{
		super(source);
		this.proto = proto;
		this.roles = Collections.unmodifiableList(roles);
	}

	public abstract Do<K, N> reconstruct(org.scribble.ast.Do<K> source,
			N proto, List<Role> roles);
	
	@Override
	public String toString()
	{
		return "do " + this.proto + "(" + this.roles.stream().map(x -> x.toString())
				.collect(Collectors.joining(", ")) + ");";
	}

	@Override
	public int hashCode()
	{
		int hash = 193;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.proto.hashCode();
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
		Do<?, ?> them = (Do<?, ?>) o;
		return super.equals(this)  // Does canEquals
				&& this.proto.equals(them.proto) && this.roles.equals(them.roles);
	}
}
