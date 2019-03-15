package org.scribble.lang;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ProtocolMod;
import org.scribble.type.kind.ProtocolKind;
import org.scribble.type.name.ProtocolName;
import org.scribble.type.name.Role;

public abstract class Protocol<K extends ProtocolKind, N extends ProtocolName<K>, B extends Seq<K>>
		extends STypeBase<K> 
{
	public final List<ProtocolMod> mods;
	public final N fullname;
	public final List<Role> roles;  // Ordered role params; pre: size >= 2
	//public final List<NonRoleParamNode<?>> params;  // TODO
	public final B def;  // CHECKME: take "? extends Seq<K>" as generic param?

	public Protocol(ProtocolDecl<K> source, List<ProtocolMod> mods, N fullname,
			List<Role> roles, // List<?> params,
			B def)
	{
		super(source);
		this.mods = Collections.unmodifiableList(mods);
		this.fullname = fullname;
		this.roles = Collections.unmodifiableList(roles);
		this.def = def;
	}
	
	public abstract Protocol<K, N, B> reconstruct(ProtocolDecl<K> source,
			List<ProtocolMod> mods, N fullname, List<Role> roles, B def);
	
	public boolean isAux()
	{
		return this.mods.contains(ProtocolMod.AUX);
	}

	public boolean isExplicit()
	{
		return this.mods.contains(ProtocolMod.EXPLICIT);
	}
	
	public Set<Role> getRoles()
	{
		throw new RuntimeException("Unsupported for Protocol: " + this);
	}

	@Override
	public ProtocolDecl<K> getSource()
	{
		return (ProtocolDecl<K>) super.getSource();
	}

	@Override
	public String toString()
	{
		return " protocol " + this.fullname + "(" + this.roles.stream()
					.map(x -> x.toString()).collect(Collectors.joining(", ")) + ")"
				+ " {\n" + this.def + "\n}";
	}
	
	// CHECKME: only should/need to use fullname?
	@Override
	public int hashCode()
	{
		int hash = 7;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.mods.hashCode();
		hash = 31 * hash + this.fullname.hashCode();
		hash = 31 * hash + this.roles.hashCode();
		hash = 31 * hash + this.def.hashCode();
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
				&& this.mods.equals(them.mods) && this.fullname.equals(them.fullname)
				&& this.roles.equals(them.roles) && this.def.equals(them.def);
	}
}
