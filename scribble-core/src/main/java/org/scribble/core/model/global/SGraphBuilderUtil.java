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
import java.util.List;
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
	
	public SGraphBuilderUtil(ModelFactory mf)
	{
		super(mf);
	}
	
	public SState newState(SConfig c)
	{
		SState s = this.mf.newSState(c);
		this.states.put(c, s);
		return s;
	}

	// Pre: this.states.containsKey(curr.config)
	public Set<SState> getSuccs(SState curr, SAction a, List<SConfig> succs)
	{
		Set<SState> res = new LinkedHashSet<>();  // Takes care of duplicates (o/w should also do "|| res.containsKey(c)" below) 
		for (SConfig c : succs)
		{
			if (this.states.containsKey(c))
			{
				curr.addEdge(a, this.states.get(c));
				continue;
			}
			SState s = newState(c);
			curr.addEdge(a, s);
			res.add(s);
		}
		return res;
	}
	
	public Map<Integer, SState> getStates()
	{
		return this.states.values().stream()
				.collect(Collectors.toMap(x -> x.id, x -> x));
	}
}
