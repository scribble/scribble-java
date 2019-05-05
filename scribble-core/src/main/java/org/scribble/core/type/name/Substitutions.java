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
package org.scribble.core.type.name;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.scribble.core.lang.SubprotoSig;
import org.scribble.core.type.kind.NonRoleParamKind;
import org.scribble.core.type.session.Arg;

// CHECKME: move to util?
public class Substitutions
{
	// Old name -> new name
	private final Map<Role, Role> rsubs = new HashMap<>();
	private final Map<MemberName<? extends NonRoleParamKind>, Arg<? extends NonRoleParamKind>>
			asubs = new HashMap<>();
			// Keys are actually simple names (params)
			// args are MessageSigName or DataType (but not MessageSig) -- N.B. substitution may replace sig name with a sig
			// NonRoleParamKind, not NonRoleArgKind, because latter includes AmbigKind due to parsing requirements
			// Better (CHECKME: necessary?) to separate roles and args -- but MessageSigName and DataType need to be distinct (so can group up, cf. NonRoleArgList)

	public Substitutions(List<Role> rold, List<Role> rnew,
			List<MemberName<? extends NonRoleParamKind>> aold,
			List<Arg<? extends NonRoleParamKind>> anew)
	{
		if (rold.size() != rnew.size())
		{
			throw new RuntimeException(
					"Role list arity mismatch: " + rold + " ; " + rnew);
		}
		if (aold.size() != anew.size())
		{
			throw new RuntimeException(
					"Arg list arity mismatch: " + aold + " ; " + anew);
		}
		Iterator<Role> i = rnew.iterator();
		rold.forEach(x -> this.rsubs.put(x, i.next()));
		Iterator<Arg<? extends NonRoleParamKind>> j = anew.iterator();
		aold.forEach(x -> this.asubs.put(x, j.next()));
	}
	
	/*public void put(N old, N neu)
	{
		this.subs.put(old, neu);
	}*/
	
	public Role subsRole(Role old)
	{
		return subsRole(old, false);
	}

	public Role subsRole(Role old, boolean passive)
	{
		if (!this.rsubs.containsKey(old))
		{
			if (!passive)
			{
				throw new RuntimeException("Unknown role: " + old);
			}
			return old;
		}
		return this.rsubs.get(old);
	}
	
	public Arg<? extends NonRoleParamKind> subsArg(MemberName<?> old)
	{
		return subsArg(old, false);
	}
	
	public //<K extends NonRoleParamKind>
			Arg<? extends NonRoleParamKind> subsArg(MemberName<?> old, boolean passive)
			// ? param more convenient for accepting DataType/MessageSigName params
	{
		if (!this.asubs.containsKey(old))
		{
			if (!passive)
			{
				throw new RuntimeException("Unknown param/arg: " + old);
			}
			return SubprotoSig.paramToArg(old);
		}
		return this.asubs.get(old);
	}
	
	public boolean hasArg(MemberName<?> old)
			// ? param more convenient for accepting DataType/MessageSigName params
	{
		return this.asubs.containsKey(old);
	}

	@Override
	public String toString()
	{
		return this.rsubs + "; " + this.asubs;
	}

	@Override
	public int hashCode()
	{
		int hash = 1889;
		hash = 31 * hash + this.rsubs.hashCode();
		hash = 31 * hash + this.asubs.hashCode();
		return hash;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof Substitutions))
		{
			return false;
		}
		Substitutions them = (Substitutions) o;
		return this.rsubs.equals(them.rsubs) && this.asubs.equals(them.asubs);
	}
}
