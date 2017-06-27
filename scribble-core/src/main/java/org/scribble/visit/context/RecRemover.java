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
package org.scribble.visit.context;

import java.util.HashSet;
import java.util.Set;

import org.scribble.ast.ScribNode;
import org.scribble.main.Job;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.name.RecVar;
import org.scribble.visit.NoEnvInlinedProtocolVisitor;

// FIXME: shadowed recvars
public class RecRemover extends NoEnvInlinedProtocolVisitor
{
	private final Set<RecVar> rvs = new HashSet<>();

	public RecRemover(Job job)
	{
		super(job);
	}
	
	public void setToRemove(Set<RecVar> rv)
	{
		this.rvs.clear();
		this.rvs.addAll(rv);
	}
	
	public boolean toRemove(RecVar rv)
	{
		return this.rvs.contains(rv);
	}
	
	public void remove(RecVar rv)
	{
		this.rvs.remove(rv);
	}

	@Override
	public void inlinedEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.inlinedEnter(parent, child);
		child.del().enterRecRemoval(parent, child, this);
	}
	
	@Override
	public ScribNode inlinedLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveRecRemoval(parent, child, this, visited);
		return super.inlinedLeave(parent, child, visited);
	}
}
