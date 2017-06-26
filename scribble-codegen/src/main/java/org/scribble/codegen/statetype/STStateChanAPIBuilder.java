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
package org.scribble.codegen.statetype;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.scribble.main.Job;
import org.scribble.model.MState;
import org.scribble.model.endpoint.EGraph;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.Role;

public abstract class STStateChanAPIBuilder
{
	public final Job job;
	
	public final GProtocolName gpn;
	public final Role role;
	public final EGraph graph;

	public final STOutputStateBuilder ob;
	public final STReceiveStateBuilder rb;
	public final STBranchStateBuilder bb;
	public final STCaseBuilder cb;
	public final STEndStateBuilder eb;

	private Map<Integer, String> names = new HashMap<>();
	
	protected STStateChanAPIBuilder(Job job, GProtocolName gpn, Role role, EGraph graph,
			STOutputStateBuilder ob, STReceiveStateBuilder rb, STBranchStateBuilder bb, STCaseBuilder cb, STEndStateBuilder eb)
	{
		this.job = job;

		this.gpn = gpn;
		this.role = role;
		this.graph = graph;

		this.ob = ob;
		this.rb = rb;
		this.bb = bb;
		this.cb = cb;
		this.eb = eb;
	}
	
	public abstract Map<String, String> buildSessionAPI();  // filepath -> source  // FIXME: factor out
	
	public Map<String, String> build()  // filepath -> source
	{
		Map<String, String> api = new HashMap<>();
		Set<EState> states = new LinkedHashSet<>();
		states.add(this.graph.init);
		states.addAll(MState.getReachableStates(this.graph.init));
		for (EState s : states)
		{
			switch (s.getStateKind())
			{
				case ACCEPT:      throw new RuntimeException("TODO");
				case OUTPUT:      api.put(getFilePath(getStateChanName(s)), this.ob.build(this, s)); break;
				case POLY_INPUT: 
				{
					api.put(getFilePath(getStateChanName(s)), this.bb.build(this, s));
					api.put(getFilePath(this.cb.getCaseStateChanName(this, s)), this.cb.build(this, s));  // FIXME: factor out
					break;
				}
				case TERMINAL:    api.put(getFilePath(getStateChanName(s)), this.eb.build(this, s)); break;  // FIXME: without subpackages, all roles share same EndSocket
				case UNARY_INPUT:
				{
					api.put(getFilePath(getStateChanName(s)), this.rb.build(this, s));
					//api.put(getFilePath(getStateChanName(s) + "_Cases"), this.cb.build(this, s));  // FIXME: factor out
					break;
				}
				case WRAP_SERVER: throw new RuntimeException("TODO");
				default:          throw new RuntimeException("Shouldn't get in here: " + s);
			}
		}
		return api;
	}
	
	public abstract String getFilePath(String name);

	public abstract String getPackage();
	
	public String getStateChanName(EState s)
	{
		String name = this.names.get(s.id);
		if (name == null)
		{
			name = makeSTStateName(s);
			this.names.put(s.id, name);
		}
		return name;
	}
	
	// Should only be called from getSTStateName
	protected abstract String makeSTStateName(EState s);

	public abstract String buildAction(STActionBuilder ab, EState curr, EAction a);

	public abstract String getChannelName(STStateChanAPIBuilder api, EAction a);

	public abstract String buildActionReturn(STActionBuilder ab, EState curr, EState succ);  // FIXME: refactor action builders as interfaces and use generic parameter for kind
}
