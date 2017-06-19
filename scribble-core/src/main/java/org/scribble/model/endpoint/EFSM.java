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
package org.scribble.model.endpoint;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.model.endpoint.actions.EAction;

// Factor out with SModel?
public class EFSM
{
	public final EGraph graph;
	public final EState curr;
	
	protected EFSM(EGraph graph)
	{
		this(graph, graph.init);
	}

	protected EFSM(EGraph graph, EState curr)
	{
		this.graph = graph;//new EGraph(init, term);
		this.curr = curr;
	}
	
	/*public EndpointState getCurrent()
	{
		return this.curr;
	}*/

	// CHECKME: check if unfolded initial accept is possible, and if it breaks anything
	public boolean isInitial()
	{
		return this.curr.equals(this.graph.init);
	}
	
	public boolean isTerminated()
	{
		return this.curr.isTerminal();
	}

	public EStateKind getStateKind()
	{
		return this.curr.getStateKind();
	}

	public List<EFSM> fireAll(EAction a)
	{
		return this.curr.getSuccessors(a).stream().map((s) -> new EFSM(this.graph, s)).collect(Collectors.toList());
	}

	public List<EAction> getAllFireable()
	{
		return this.curr.getAllActions();
	}
	
	public boolean hasFireable(EAction a)
	{
		return this.curr.hasAction(a);
	}
	
	public boolean isConnectOrWrapClientOnly()
	{
		return this.curr.isConnectOrWrapClientOnly();
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
		if (!(o instanceof EFSM))
		{
			return false;
		}
		EFSM them = (EFSM) o;
		return this.graph.equals(them.graph) && this.curr.equals(them.curr);
	}
	
	@Override
	public String toString()
	{
		return Integer.toString(this.curr.id);
	}
}
