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
package org.scribble.model.global;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.scribble.model.MPrettyState;
import org.scribble.model.MState;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.model.endpoint.actions.EReceive;
import org.scribble.model.endpoint.actions.ESend;
import org.scribble.model.global.actions.SAction;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.name.Role;

// FIXME? make a WFModel front-end class? (cf. EGraph)
// Only uses MState.id cosmetically, cf. MState equals/hash -- overrides equals/hash based on this.config (maybe extending MState is a bit misleading)
public class SState extends MPrettyState<Void, SAction, SState, Global>
{
	public final SConfig config;
	
	//protected SState(SConfig config)
	protected SState(SConfig config)  // FIXME: now publically mutable
	{
		super(Collections.emptySet());
		this.config = config;
	}
	
	@Override
	protected void addEdge(SAction a, SState s)  // For access from SGraphBuilderUtil
	{
		super.addEdge(a, s);
	}
	
	// Based on config semantics, not "static" graph edges (cf., super.getAllActions) -- used to build global model graph
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
	}
	
	public SStateErrors getErrors()
	{
		Map<Role, EReceive> stuck = this.config.getStuckMessages();
		Set<Set<Role>> waitfor = this.config.getWaitForErrors();
		//Set<Set<Role>> waitfor = Collections.emptySet();
		Map<Role, Set<ESend>> orphs = this.config.getOrphanMessages();
		Map<Role, EState> unfinished = this.config.getUnfinishedRoles();
		return new SStateErrors(stuck, waitfor, orphs, unfinished);
	}
	
	@Override
	protected String getNodeLabel()
	{
		String labs = this.config.toString();
		return "label=\"" + this.id + ":" + labs.substring(1, labs.length() - 1) + "\"";
	}
	
	// FIXME? doesn't use super.hashCode (cf., equals)
	@Override
	public int hashCode()
	{
		int hash = 79;
		//int hash = super.hashCode();
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
		return ((SState) o).canEquals(this) && this.config.equals(((SState) o).config);
	}

	@Override
	protected boolean canEquals(MState<?, ?, ?, ?> s)
	{
		return s instanceof SState;
	}
	
	@Override
	public String toString()
	{
		return this.id + ":" + this.config.toString();
	}
}
