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
package org.scribble.del;

import java.util.List;

import org.scribble.ast.ProtoDecl;
import org.scribble.ast.RoleArgList;
import org.scribble.ast.RoleDeclList;
import org.scribble.ast.ScribNode;
import org.scribble.core.type.name.Role;
import org.scribble.util.ScribException;
import org.scribble.visit.NameDisambiguator;

public class RoleArgListDel extends DoArgListDel
{
	public RoleArgListDel()
	{

	}

	@Override
	public RoleArgList leaveDisambiguation(ScribNode child,
			NameDisambiguator disamb, ScribNode visited) throws ScribException
	{
		visited = super.leaveDisambiguation(child, disamb, visited);  // Checks matching arity

		// Duplicate check not needed for NonRoleArgList
		RoleArgList ral = (RoleArgList) visited;
		List<Role> roles = ral.getRoles();
		if (roles.size() != roles.stream().distinct().count())
		{
			throw new ScribException(ral.getSource(),
					"Duplicate role args: " + roles);
		}
		return ral;
	}

	@Override
	protected RoleDeclList getDeclList(ProtoDecl<?> pd)
	{
		return pd.getHeaderChild().getRoleDeclListChild();
	}
}
