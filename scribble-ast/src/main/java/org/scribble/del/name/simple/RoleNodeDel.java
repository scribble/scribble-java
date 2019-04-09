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
package org.scribble.del.name.simple;

import org.scribble.ast.ScribNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ScribDelBase;
import org.scribble.util.ScribException;
import org.scribble.visit.NameDisambiguator;

public class RoleNodeDel extends ScribDelBase
{
	public RoleNodeDel()
	{

	}

	@Override
	public RoleNode leaveDisambiguation(ScribNode child, NameDisambiguator disamb,
			ScribNode visited) throws ScribException
	{
		RoleNode rn = (RoleNode) visited;
		if (!disamb.isBoundRole(rn.toName()))  // Added on RoleDecl entry
		{
			throw new ScribException(rn.getSource(), "Role not bound: " + rn);
		}
		return rn;
	}
}
