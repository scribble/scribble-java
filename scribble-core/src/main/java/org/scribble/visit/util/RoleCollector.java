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
package org.scribble.visit.util;

import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.context.ModuleContext;
import org.scribble.main.Job;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.name.Role;

// Second pass for ProtocolDeclContext building
// Maybe generalise to integrate enabled message collection?
// NOTE: doesn't collect from choice subjects
public class RoleCollector extends NameCollector<Role>
{
	public RoleCollector(Job job)
	{
		super(job);
	}

	public RoleCollector(Job job, ModuleContext mcontext)
	{
		super(job, mcontext);
	}

	@Override
	protected final void offsetSubprotocolEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.offsetSubprotocolEnter(parent, child);
		if (child instanceof ProtocolDecl<?>)
		{
			super.clear();
		}
		child.del().enterRoleCollection(parent, child, this);
	}
	
	@Override
	protected ScribNode offsetSubprotocolLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveRoleCollection(parent, child, this, visited);
		return super.offsetSubprotocolLeave(parent, child, visited);
	}
}
