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
package org.scribble.visit;

import org.scribble.ast.ScribNode;
import org.scribble.main.Job;
import org.scribble.main.ScribbleException;

// Pattern: node accepts visitor and calls visitor back (standard visitor pattern -- adding a new operation doesn't affect the Ast classes), but then visitor delegates back to node delegate (so routines for handling each node type not centralised in visitor, but decentralised to delegates)
public abstract class AstVisitor
{
	public final Job job;  // Immutable except for JobContext internals

	//private ModuleContext mcontext;  // Factor up to ModelVisitor? (will be null before context building) -- maybe make a ModuleVisitor
	
	protected AstVisitor(Job job)
	{
		this.job = job;
	}

	public ScribNode visit(ScribNode parent, ScribNode child) throws ScribbleException
	{
		enter(parent, child);
		ScribNode visited = child.visitChildren(this);   // visited means "children visited so far"; we're about to visit "this" now via "leave"
		return leave(parent, child, visited);
	}
	
	protected void enter(ScribNode parent, ScribNode child) throws ScribbleException
	{

	}
	
	protected ScribNode leave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		return visited;
	}
}
