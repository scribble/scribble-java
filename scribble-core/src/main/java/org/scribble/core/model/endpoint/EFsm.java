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
package org.scribble.core.model.endpoint;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.core.model.endpoint.actions.EAction;

// Factor out with SModel?
public class EFsm
{
	public final EGraph graph;
	public final EState curr;
	
	protected EFsm(EGraph graph)
	{
		this(graph, graph.init);
	}

	protected EFsm(EGraph graph, EState curr)
	{
		this.graph = graph;
		this.curr = curr;
	}

	// CHECKME: Set? List is for non-det actions, but is that relevant to EFsm's?
	// Pre: this.curr.getAllFireable().contains(a) ?
	public List<EFsm> fireAll(EAction a)
	{
		return this.curr.getSuccs(a).stream().map(x -> new EFsm(this.graph, x))
				.collect(Collectors.toList());
	}

	// CHECKME: check if unfolded initial accept is possible, and if it breaks anything

	// Following are a bunch of wrapper methods to curr
	public boolean isInitial()
	{
		return this.curr.equals(this.graph.init);
	}

	@Override
	public final int hashCode()
	{
		int hash = 1049;
		hash = 31 * hash + this.graph.init.hashCode();
		hash = 31 * hash + this.curr.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof EFsm))
		{
			return false;
		}
		EFsm them = (EFsm) o;
		return this.graph.equals(them.graph) && this.curr.equals(them.curr);
	}
	
	@Override
	public String toString()
	{
		return Integer.toString(this.curr.id);
	}
}















	
	/*public boolean isTerminated()
	{
		return this.curr.isTerminal();
	}

	public EStateKind getStateKind()
	{
		return this.curr.getStateKind();
	}

	public List<EAction> getActions()
	{
		return this.curr.getActions();
	}
	
	public boolean hasAction(EAction a)
	{
		return this.curr.hasAction(a);
	}
	
	public boolean isRequesttOrClientWrapOnly()
	{
		return this.curr.isRequestOrClientWrapOnly();
	}*/
