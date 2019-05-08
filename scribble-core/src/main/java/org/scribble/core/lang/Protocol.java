/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.scribble.core.lang;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.core.type.kind.NonRoleParamKind;
import org.scribble.core.type.kind.ProtoKind;
import org.scribble.core.type.name.DataName;
import org.scribble.core.type.name.MemberName;
import org.scribble.core.type.name.ProtoName;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.name.SigName;
import org.scribble.core.type.session.Seq;
import org.scribble.core.visit.STypeInliner;
import org.scribble.core.visit.STypeUnfolder;
import org.scribble.util.Constants;

public abstract class Protocol
		<K extends ProtoKind, N extends ProtoName<K>, B extends Seq<K, B>>
		implements SNode<K>
{
	private final CommonTree source;  // CHECKME: factor out with SType(Base) ?
	
	public final List<ProtoMod> mods;
	public final N fullname;
	public final List<Role> roles;  // Ordered role params; pre: size >= 2
	public final List<MemberName<? extends NonRoleParamKind>> params;  
			// N.B. there is no Data/SigParamName  // CHECKME: always simple names?
			// NonRoleParamKind, not NonRoleArgKind, because latter includes AmbigKind due to parsing requirements
			// CHECKME: make a ParamName? or at least SimpleName?
	public final B def;

	public Protocol(CommonTree source, List<ProtoMod> mods, N fullname,
			List<Role> roles, List<MemberName<? extends NonRoleParamKind>> params,
			B def)
	{
		this.source = source;  // CHECKME: factor out with SType(Base) ?
		this.mods = Collections.unmodifiableList(mods);
		this.fullname = fullname;
		this.roles = Collections.unmodifiableList(roles);
		this.params = Collections.unmodifiableList(params);
		this.def = def;
	}
	
	// N.B. LProtocol has an additional "self" field, reconstruct pattern not perfect
	public abstract Protocol<K, N, B> reconstruct(CommonTree source,
			List<ProtoMod> mods, N fullname, List<Role> roles,
			List<MemberName<? extends NonRoleParamKind>> params, B def);
	
	public boolean isAux()
	{
		return this.mods.contains(ProtoMod.AUX);
	}

	public boolean isExplicit()
	{
		return this.mods.contains(ProtoMod.EXPLICIT);
	}

	public abstract Protocol<K, N, B> getInlined(STypeInliner<K, B> v);
	public abstract Protocol<K, N, B> unfoldAllOnce(STypeUnfolder<K, B> v);

	public boolean hasSource()  // i.e., was parsed
	{
		return this.source != null;
	}

  // CHECKME: factor out with SType(Base) ?
	public CommonTree getSource()  // Pre: hasSource
	{
		return this.source;
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
						if (x instanceof DataName) // CHECKME: refactor?
						{
							k = Constants.TYPE_KW;
						}
						else if (x instanceof SigName)
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
		return them.canEquals(this)
				&& this.mods.equals(them.mods) && this.fullname.equals(them.fullname)
				&& this.roles.equals(them.roles) && this.params.equals(them.params)
				&& this.def.equals(them.def);
	}

	public abstract boolean canEquals(Object o);
}
