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
package org.scribble.ast.context.local;

import java.util.Collections;
import java.util.Set;

import org.scribble.ast.context.ProtocolDeclContext;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.name.Role;

public class LProtocolDeclContext extends ProtocolDeclContext<Local>
{
	protected LProtocolDeclContext(Set<Role> roles, LDependencyMap deps)
	{
		super(roles, deps);
	}

	public LProtocolDeclContext(LDependencyMap deps)
	{
		this(Collections.emptySet(), deps);
	}
	
	@Override
	protected LProtocolDeclContext copy()
	{
		return new LProtocolDeclContext(getRoleOccurrences(), getDependencyMap());
	}
	
	@Override
	public LDependencyMap getDependencyMap()
	{
		return (LDependencyMap) super.getDependencyMap();
	}
}
