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
package org.scribble.type;

import java.util.Collections;
import java.util.List;

import org.scribble.type.name.ProtocolName;
import org.scribble.type.name.Role;

// Immutable
public class SubprotoSig
{
	public final ProtocolName<?> fullname;
	// public Scope scope;
	public final List<Role> roles;  // i.e., roles (and args) are ordered
	public final List<Arg<?>> args;

	// public SubprotocolSignature(ProtocolName fmn, Scope scope, List<Role>
	// roles, List<Argument<? extends Kind>> args)
	public SubprotoSig(ProtocolName<?> fullname,
			List<Role> roles, List<Arg<?>> args)
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
		return this.fullname.equals(subsig.fullname) // &&
																									// this.scope.equals(subsig.scope)
				&& this.roles.equals(subsig.roles) && this.args.equals(subsig.args);
	}

	@Override
	public String toString()
	{
		String roles = this.roles.toString();
		String args = this.args.toString();
		return // this.scope + ":" +
				this.fullname + "<" + args.substring(1, args.length() - 1) + ">("
						+ roles.substring(1, roles.length() - 1) + ")";
	}
}
