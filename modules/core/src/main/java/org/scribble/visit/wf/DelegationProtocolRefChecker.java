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
package org.scribble.visit.wf;

import java.util.Deque;
import java.util.LinkedList;

import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ScribNode;
import org.scribble.main.Job;
import org.scribble.main.ScribbleException;
import org.scribble.visit.context.ModuleContextVisitor;

// Checks for recursive protocoldecl dependencies in the presence of delegations (currently, both via the delegation, and in of the protocoldelcs themselves -- FIXME over restrictive)
public class DelegationProtocolRefChecker extends ModuleContextVisitor
{
	private Deque<ProtocolDecl<?>> pds = new LinkedList<>();  // Stack should be unnecessary
			// HACK: for DelegationElem recursive protocoldecl dependency checking  // FIXME: should factor out more generally 
	
	public DelegationProtocolRefChecker(Job job)
	{
		super(job);
	}

	@Override
	public void enter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.enter(parent, child);
		if (child instanceof ProtocolDecl<?>)
		{
			this.pds.push((ProtocolDecl<?>) child);
		}
		child.del().enterDelegationProtocolRefCheck(parent, child, this);
	}
	
	@Override
	public ScribNode leave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveDelegationProtocolRefCheck(parent, child, this, visited);
		if (visited instanceof ProtocolDecl<?>)
		{
			this.pds.pop();
		}
		return super.leave(parent, child, visited);
	}
	
	public ProtocolDecl<?> getProtocolDeclOnEntry()  // Result of visiting (on leave) will be a different instance
	{
		return this.pds.peek();
	}
}
