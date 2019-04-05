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

import org.scribble.core.type.kind.NonRoleParamKind;
import org.scribble.core.type.name.ProtocolName;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.Arg;

// CHECKME: relocate?
// Immutable
public class SubprotoSig
{
	public final ProtocolName<?> fullname;
	// public Scope scope;
	public final List<Role> roles;  // i.e., roles (and args) are ordered
	public final List<Arg<? extends NonRoleParamKind>> args;
			// NonRoleParamKind, not NonRoleArgKind, because latter includes AmbigKind due to parsing requirements
			// Arg, not MemberName, because need to include MessageSigs (sig literals)

	// public SubprotocolSignature(ProtocolName fmn, Scope scope, List<Role>
	// roles, List<Argument<? extends Kind>> args)
	public SubprotoSig(ProtocolName<?> fullname,
			List<Role> roles, List<Arg<? extends NonRoleParamKind>> args)
	{
		this.fullname = fullname;
		// this.scope = scope;
		this.roles = Collections.unmodifiableList(roles);
		this.args = Collections.unmodifiableList(args);
	}

	@Override
	public int hashCode()
	{
		int hash = 1093;
		hash = 31 * hash + this.fullname.hashCode();
		// hash = 31 * hash + this.scope.hashCode();
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
		return this.fullname.equals(subsig.fullname) // && this.scope.equals(subsig.scope)
				&& this.roles.equals(subsig.roles) && this.args.equals(subsig.args);
	}

	@Override
	public String toString()
	{
		return // this.scope + ":" +
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
