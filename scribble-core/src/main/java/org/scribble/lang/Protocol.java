package org.scribble.lang;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ast.Constants;
import org.scribble.ast.ProtocolDecl;
import org.scribble.type.kind.NonRoleParamKind;
import org.scribble.type.kind.ProtocolKind;
import org.scribble.type.name.DataType;
import org.scribble.type.name.MemberName;
import org.scribble.type.name.MessageSigName;
import org.scribble.type.name.ProtocolName;
import org.scribble.type.name.RecVar;
import org.scribble.type.name.Role;

public abstract class Protocol<K extends ProtocolKind, N extends ProtocolName<K>, B extends Seq<K>>
		extends STypeBase<K> 
{
	public final List<ProtocolMod> mods;
	public final N fullname;
	public final List<Role> roles;  // Ordered role params; pre: size >= 2
	public final List<MemberName<? extends NonRoleParamKind>> params;
			// NonRoleParamKind, not NonRoleArgKind, because latter includes AmbigKind due to parsing requirements
			// CHECKME: make a ParamName? or at least SimpleName?
	public final B def;

	public Protocol(ProtocolDecl<K> source, List<ProtocolMod> mods, N fullname,
			List<Role> roles, List<MemberName<? extends NonRoleParamKind>> params, B def)
	{
		super(source);
		this.mods = Collections.unmodifiableList(mods);
		this.fullname = fullname;
		this.roles = Collections.unmodifiableList(roles);
		this.params = Collections.unmodifiableList(params);
		this.def = def;
	}
	
	/*public abstract Protocol<K, N, B> reconstruct(ProtocolDecl<K> source,
			List<ProtocolMod> mods, N fullname, List<Role> roles,
			List<MemberName<? extends NonRoleParamKind>> params, B def);*/
	
	public boolean isAux()
	{
		return this.mods.contains(ProtocolMod.AUX);
	}

	public boolean isExplicit()
	{
		return this.mods.contains(ProtocolMod.EXPLICIT);
	}
	
	// FIXME: confusing with this.roles List -- refactor: Protocol shouldn't be a SType
	@Override
	public Set<Role> getRoles()
	{
		throw new RuntimeException("Unsupported for Protocol: " + this);
	}

	@Override
	public Set<RecVar> getRecVars()
	{
		throw new RuntimeException("Unsupported for Protocol: " + this);
	}

	@Override
	public Protocol<K, N, B> substitute(Substitutions subs)
	{
		// CHECKME: needed?
		/*List<Role> roles = this.roles.stream().map(x -> subs.subsRole(x))
				.collect(Collectors.toList());
		List<MemberName<NonRoleArgKind>> params = this.params.stream().map(x -> ...)
				.collect(Collectors.toList());
		return reconstruct(getSource(), this.mods, this.fullname, roles,
				this.def.substitute(subs));*/
		throw new RuntimeException("Unsupported for Protocol: " + this);
	}

	@Override
	public Protocol<K, N, B> pruneRecs()
	{
		throw new RuntimeException("Unsupported for Protocol: " + this);
	}
	
	@Override
	public List<ProtocolName<K>> getProtoDependencies()
	{
		return this.def.getProtoDependencies();
	}

	@Override
	public List<MemberName<?>> getNonProtoDependencies()
	{
		return this.def.getNonProtoDependencies();
	}

	@Override
	public ProtocolDecl<K> getSource()
	{
		return (ProtocolDecl<K>) super.getSource();
	}

	@Override
	public String toString()
	{
		return "protocol " + this.fullname.getSimpleName()
				+ paramsToString()
				+ rolesToString()
				+ " {\n" + this.def + "\n}";
	}
	
	protected String rolesToString()
	{
		return "("
				+ this.roles.stream().map(x -> Constants.ROLE_KW + " " + x.toString())
						.collect(Collectors.joining(", "))
				+ ")";
	}

	protected String paramsToString()
	{
		return "<" + this.params.stream() // CHECKME: drop empty "<>" ?
				.map(x ->
					{
						String k;
						if (x instanceof DataType) // CHECKME: refactor?
						{
							k = Constants.TYPE_KW;
						}
						else if (x instanceof MessageSigName)
						{
							k = Constants.SIG_KW;
						}
						else
						{
							throw new RuntimeException();
						}
						return k + x;
					})
				.collect(Collectors.joining(", "))
			+ ">";
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
		hash = 31 * hash + this.params.hashCode();
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
				&& this.roles.equals(them.roles) && this.params.equals(them.params)
				&& this.def.equals(them.def);
	}
}
