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
package org.scribble.ast.context;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import org.scribble.type.name.ProtocolName;
import org.scribble.type.name.Role;

import java.util.Set;

// Mutable
// Used for two purposes: one to encapsulate Map structure and add method for ContextBuilder; second to allow overriding the generic types in ProtocolDeclContext (cf. nested Map generics)
public abstract class DependencyMap<N extends ProtocolName<?>>  // Maybe better to parameterise on Kind only?
{
	// self -> (proto -> target) -- the self role in this proto, depends on the proto, for the role param target
	private final Map<Role, Map<N, Set<Role>>> deps = new HashMap<>();  // All the potential dependencies from this protocol decl as the root

	public DependencyMap()
	{

	}

	protected DependencyMap(DependencyMap<N> deps)
	{
		for (Role r : deps.deps.keySet())  // FIXME: optimise
		{
			Map<N, Set<Role>> tmp = deps.deps.get(r);
			for (Entry<N, Set<Role>> e : tmp.entrySet())
			{
				for (Role rr : e.getValue())
				{
					addProtocolDependency(r, e.getKey(), rr);
				}
			}
		}
	}
	
	public abstract DependencyMap<N> clone();

	public void addProtocolDependency(Role self, N pn, Role target)
	{
		Map<N, Set<Role>> tmp1 = this.deps.get(self);
		if (tmp1 == null)
		{
			tmp1 = new HashMap<>();
			this.deps.put(self, tmp1);
		}
		
		Set<Role> tmp2 = tmp1.get(pn);
		if (tmp2 == null)
		{
			tmp2 = new HashSet<>();
			tmp1.put(pn, tmp2);
		}
		tmp2.add(target);
	}
	
	public Map<Role, Map<N, Set<Role>>> getDependencies()
	{
		return this.deps;
	}
}
