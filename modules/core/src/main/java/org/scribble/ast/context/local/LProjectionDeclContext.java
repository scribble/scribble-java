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

import java.util.Set;

import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.Role;


@Deprecated
public class LProjectionDeclContext extends LProtocolDeclContext
{
	private GProtocolName fullname;
	private Role self;
	
	protected LProjectionDeclContext(Set<Role> roles, LDependencyMap deps, GProtocolName gpn, Role self)
	{
		super(roles, deps);
	}
	
	@Override
	public LProjectionDeclContext copy()
	{
		return new LProjectionDeclContext(getRoleOccurrences(), getDependencyMap(), getSourceProtocol(), getSelfRole());
	}
	
	public GProtocolName getSourceProtocol()
	{
		return this.fullname;
	}
	
	// Redundant with SelfRoleDecl in header
	public Role getSelfRole()
	{
		return this.self;
	}
}

