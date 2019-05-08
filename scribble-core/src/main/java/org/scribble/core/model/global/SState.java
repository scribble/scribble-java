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
package org.scribble.core.model.global;

import java.util.Collections;
import java.util.Set;

import org.scribble.core.model.MPrettyState;
import org.scribble.core.model.MState;
import org.scribble.core.model.global.actions.SAction;
import org.scribble.core.type.kind.Global;

// CHECKME: make a WFModel front-end class? (cf. EGraph)
// N.B. only uses MState.id cosmetically, cf. MState equals/hashCode -- overrides equals/hashCode based on this.config (maybe extending MState is a bit misleading)
public class SState extends MPrettyState<Void, SAction, SState, Global>
{
	public final SConfig config;
	
	protected SState(SConfig config)  // CHECKME? now publically mutable (for mf imple), same for EState
	{
		super(Collections.emptySet());
		this.config = config;
	}
	
  // For access from SGraphBuilderUtil
	@Override
	protected void addEdge(SAction a, SState s)
	{
		super.addEdge(a, s);
	}
	
	public SStateErrors getErrors()  // Means safety (i.e., individual state) errors
	{
		return new SStateErrors(this);
	}
	
	@Override
	protected String getNodeLabel()
	{
		String labs = this.config.toString();
		return "label=\"" + this.id + ":" + labs.substring(1, labs.length() - 1)
				+ "\"";
	}

	@Override
	public Set<SState> getReachableStates()
	{
		return getReachableStatesAux(this);
	}
	
	@Override
	public String toString()
	{
		return this.id + ":" + this.config.toString();
	}
	
	// N.B. does not use super.hashCode, need "semantic" equality of configs for model construction
	@Override
	public int hashCode()
	{
		int hash = 79;
		hash = 31 * hash + this.config.hashCode();
		return hash;
	}

	// FIXME? doesn't use this.id, cf. super.equals
	// Not using id, cf. ModelState -- FIXME? use a factory pattern that associates unique states and ids? -- use id for hash, and make a separate "semantic equals"
	// Care is needed if hashing, since mutable (OK to use immutable config -- cf., ModelState.id)
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof SState))
		{
			return false;
		}
		SState them = (SState) o;
		return them.canEquals(this) && this.config.equals(them.config);  // N.B. does not do super.equals (cf. hashCode)
	}

	@Override
	protected boolean canEquals(MState<?, ?, ?, ?> s)
	{
		return s instanceof SState;
	}
}











	
	/*// Based on config semantics, not "static" graph edges (cf., super.getAllActions) -- used to build global model graph
	public Map<Role, List<EAction>> getFireable()
	{
		return this.config.getFireable();
	}
	
	public List<SConfig> fire(Role r, EAction a)
	{
		return this.config.fire(r, a);
	}

	// "Synchronous version" of fire
	public List<SConfig> sync(Role r1, EAction a1, Role r2, EAction a2)
	{
		return this.config.sync(r1, a1, r2, a2);
	}*/