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

import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.RoleArgList;
import org.scribble.ast.RoleDeclList;
import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.type.name.Role;
import org.scribble.visit.wf.NameDisambiguator;

public class RoleArgListDel extends DoArgListDel
{
	public RoleArgListDel()
	{

	}

	@Override
	public RoleArgList leaveDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb, ScribNode visited) throws ScribbleException
	{
		visited = super.leaveDisambiguation(parent, child, disamb, visited);

		// Duplicate check not needed for NonRoleArgList
		RoleArgList ral = (RoleArgList) visited;
		List<Role> roles = ral.getRoles();
		//if (roles.size() != new HashSet<>(roles).size())
		if (roles.size() != roles.stream().distinct().count())
		{
			throw new ScribbleException(ral.getSource(), "Duplicate role args: " + roles);
		}
		return ral;
	}

	@Override
	protected RoleDeclList getParamDeclList(ProtocolDecl<?> pd)
	{
		return pd.header.roledecls;
	}
}
