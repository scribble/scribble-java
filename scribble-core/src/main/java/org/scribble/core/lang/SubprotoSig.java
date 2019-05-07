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

import org.scribble.core.lang.global.GProtocol;
import org.scribble.core.lang.local.LProtocol;
import org.scribble.core.type.kind.NonRoleParamKind;
import org.scribble.core.type.name.DataName;
import org.scribble.core.type.name.MemberName;
import org.scribble.core.type.name.ProtoName;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.name.SigName;
import org.scribble.core.type.session.Arg;

// CHECKME: relocate?
// Immutable
public class SubprotoSig
{
	public final ProtoName<?> fullname;
	public final List<Role> roles;  // i.e., roles (and args) are ordered
	public final List<Arg<? extends NonRoleParamKind>> args;
			// NonRoleParamKind, not NonRoleArgKind, because latter includes AmbigKind due to parsing requirements
			// Arg, not MemberName, because need to include MessageSigs (sig literals)

	// CHECKME: refactor as factory methods on Protocol/Do ?
	public SubprotoSig(ProtoName<?> fullname,
			List<Role> roles, List<Arg<? extends NonRoleParamKind>> args)
	{
		this.fullname = fullname;
		this.roles = Collections.unmodifiableList(roles);
		this.args = Collections.unmodifiableList(args);
	}

	public SubprotoSig(GProtocol n)
	{
		this(n.fullname, n.roles, paramsToArgs(n.params));
	}

	public SubprotoSig(LProtocol n)
	{
		this(n.fullname, n.roles.stream().map(x -> x.equals(n.self) ? Role.SELF : x)
						// N.B. role decls (cf. do-args) don't feature self (cf. LSelfDecl), even after pruning/fixing
						// FIXME: (implicit) self role mess
				.collect(Collectors.toList()), paramsToArgs(n.params));
	}

	private static List<Arg<? extends NonRoleParamKind>> paramsToArgs(
			List<MemberName<? extends NonRoleParamKind>> params)
	{
		// Convert MemberName params to Args -- cf. NonRoleArgList::getParamKindArgs
		return params.stream().map(x -> paramToArg(x)).collect(Collectors.toList());
	}
	
	// TODO: refactor, into params?
	public static Arg<? extends NonRoleParamKind> paramToArg(
			MemberName<?> n)  // Omit " extends NonRoleParamKind" on param, more flexible without major harm
	{
		if (n instanceof DataName)
		{
			return (DataName) n;
		}
		else if (n instanceof SigName)
		{
			return (SigName) n;
		}
		else
		{
			throw new RuntimeException("[TODO] : " + n.getClass() + "\n\t" + n);
		}
	}

	@Override
	public int hashCode()
	{
		int hash = 1093;
		hash = 31 * hash + this.fullname.hashCode();
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
		if (!(o instanceof SubprotoSig))
		{
			return false;
		}
		SubprotoSig subsig = (SubprotoSig) o;
		return this.fullname.equals(subsig.fullname)
				&& this.roles.equals(subsig.roles) && this.args.equals(subsig.args);
	}

	@Override
	public String toString()
	{
		return
				this.fullname
				+ "<"
				+ this.args.stream().map(x -> x.toString())
						.collect(Collectors.joining(", "))
				+ ">"
				+ "("
				+ this.roles.stream().map(x -> x.toString())
						.collect(Collectors.joining(", "))
				+ ")";
	}
}
