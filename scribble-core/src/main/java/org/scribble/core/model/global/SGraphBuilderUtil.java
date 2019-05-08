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

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.core.model.GraphBuilderUtil;
import org.scribble.core.model.ModelFactory;
import org.scribble.core.model.global.actions.SAction;
import org.scribble.core.type.kind.Global;

public class SGraphBuilderUtil
		extends GraphBuilderUtil<Void, SAction, SState, Global>
{
	private Map<SConfig, SState> states = new HashMap<>();
	
	protected SGraphBuilderUtil(ModelFactory mf)
	{
		super(mf);
		reset();
	}
	
	@Override
	protected void reset()
	{
		this.states.clear();
	}
	
	public SState newState(SConfig c)
	{
		SState s = this.mf.global.SState(c);
		this.states.put(c, s);
		return s;
	}

	// Pre: this.states.containsKey(curr.config)
	public Set<SState> getSuccs(SState curr, SAction a, //List<SConfig> succs)
			Set<SConfig> succs)
			// SConfig.a/sync currently produces a List, but here collapse identical configs for global model (represent non-det "by edges", not "by model states")
	{
		Set<SState> res = new LinkedHashSet<>();  // Takes care of duplicates (o/w should also do "|| res.containsKey(c)" below) 
		for (SConfig c : succs)
		{
			boolean seen = this.states.containsKey(c);
			SState next = seen
					? this.states.get(c)
					: newState(c);
			curr.addEdge(a, next);
			if (!seen)  // Must use cached test, newState changes adds the key
			{
				res.add(next);
			}
		}
		return res;
	}
	
	// s.id -> s
	public Map<Integer, SState> getStates()
	{
		return this.states.values().stream()
				.collect(Collectors.toMap(x -> x.id, x -> x));
	}
}
