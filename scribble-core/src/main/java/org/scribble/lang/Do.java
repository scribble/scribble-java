package org.scribble.lang;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.type.Arg;
import org.scribble.type.kind.NonRoleParamKind;
import org.scribble.type.kind.ProtocolKind;
import org.scribble.type.name.DataType;
import org.scribble.type.name.MemberName;
import org.scribble.type.name.MessageSigName;
import org.scribble.type.name.ModuleName;
import org.scribble.type.name.ProtocolName;
import org.scribble.type.name.Role;

public abstract class Do<K extends ProtocolKind, N extends ProtocolName<K>>
		extends STypeBase<K> implements SType<K>
{
	public final N proto;  // Currently disamb'd to fullname by GTypeTranslator (see GDoDel::translate)
	public final List<Role> roles;  // Ordered role args; pre: size > 2
	public final List<Arg<? extends NonRoleParamKind>> args;
			// NonRoleParamKind, not NonRoleArgKind, because latter includes AmbigKind due to parsing requirements

	public Do(org.scribble.ast.Do<K> source, N proto,
			List<Role> roles, List<Arg<? extends NonRoleParamKind>> args)
	{
		super(source);
		this.proto = proto;
		this.roles = Collections.unmodifiableList(roles);
		this.args = Collections.unmodifiableList(args);
	}

	public abstract Do<K, N> reconstruct(org.scribble.ast.Do<K> source,
			N proto, List<Role> roles, List<Arg<? extends NonRoleParamKind>> args);

	@Override
	public Set<Role> getRoles()
	{
		return this.roles.stream().collect(Collectors.toSet());
	}

	@Override
	public List<ModuleName> getDependencies()
	{
		return Stream.of(this.proto.getPrefix()).collect(Collectors.toList());
	}

	@Override
	public Do<K, N> substitute(Substitutions subs)
	{
		List<Role> roles = this.roles.stream().map(x -> subs.subsRole(x))
				.collect(Collectors.toList());
		List<Arg<? extends NonRoleParamKind>> args = new LinkedList<>();
		for (Arg<? extends NonRoleParamKind> a : this.args) 
		{
			if (a instanceof MemberName<?> && subs.hasArg((MemberName<?>) a))
			{
				if (a instanceof DataType)
				{
					a = subs.subsArg((DataType) a);
				}
				else if (a instanceof MessageSigName)
				{
					a = subs.subsArg((MessageSigName) a);
				}
			}
			args.add(a);
		}
		return reconstruct((org.scribble.ast.Do<K>) getSource(), this.proto, roles,
				args);
	}

	@Override
	public SType<K> unfoldAllOnce(STypeUnfolder<K> u)
	{
		throw new RuntimeException("Unsupported for Do: " + this);
	}
	
	@Override
	public String toString()
	{
		return "do " + this.proto 
				+ "<"
				+ this.args.stream().map(x -> x.toString())
						.collect(Collectors.joining(", "))
				+ ">"
				+ "(" + this.roles.stream().map(x -> x.toString())
						.collect(Collectors.joining(", "))
				+ ");";
	}

	@Override
	public int hashCode()
	{
		int hash = 193;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.proto.hashCode();
		hash = 31 * hash + this.roles.hashCode();
		hash = 31 * hash + this.args.hashCode();
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
				&& this.proto.equals(them.proto) && this.roles.equals(them.roles) 
				&& this.args.equals(them.args);
	}
}
