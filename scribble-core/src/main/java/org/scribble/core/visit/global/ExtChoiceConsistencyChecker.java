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
package org.scribble.core.visit.global;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.core.type.kind.Global;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.Choice;
import org.scribble.core.type.session.DirectedInteraction;
import org.scribble.core.type.session.Do;
import org.scribble.core.type.session.SType;
import org.scribble.core.type.session.global.GSeq;
import org.scribble.core.visit.STypeVisitor;
import org.scribble.util.ScribException;

// Pre: use on inlined or later (unsupported for Do, also Protocol)
public class ExtChoiceConsistencyChecker extends STypeVisitor<Global, GSeq>
{
	private Map<Role, Role> enablers;  // Invariant: unmodifiable

	protected ExtChoiceConsistencyChecker(Map<Role, Role> enabled)
	{
		setEnablers(enabled);
	}
	
	public SType<Global, GSeq> visitChoice(Choice<Global, GSeq> n)
			throws ScribException
	{
		Map<Role, Role> subj = Stream.of(n.subj)
				.collect(Collectors.toMap(x -> x, x -> x));
		List<Map<Role, Role>> blocks = new LinkedList<>();
		ExtChoiceConsistencyChecker nested = new ExtChoiceConsistencyChecker(subj);  
				// Arg redundant, but better to keep a single constructor, for factory pattern
		for (GSeq block : n.blocks)
		{
			nested.setEnablers(subj);
			nested.visitSeq(block);
			blocks.add(nested.getEnablers());
		}
		Map<Role, Role> res = new HashMap<>(getEnablers());
		Set<Entry<Role, Role>> all = blocks.stream()
				.flatMap(x -> x.entrySet().stream()).collect(Collectors.toSet());
		for (Entry<Role, Role> e : all)
		{
			Role enabled = e.getKey();
			Role enabler = e.getValue();
			if (all.stream().anyMatch(
					x -> x.getKey().equals(enabled) && !x.getValue().equals(enabler)))
			{
				throw new ScribException(
						"Inconsistent external choice subjects for " + enabled + ": "
								+ all.stream().filter(x -> x.getKey().equals(enabled))
										.collect(Collectors.toList()));
			}
			if (!res.containsKey(enabled))
			{
				res.put(enabled, enabler);
			}
		}
		setEnablers(res);
		return n;
	}

	@Override
	public SType<Global, GSeq> visitDirectedInteraction(
			DirectedInteraction<Global, GSeq> n) throws ScribException
	{
		Map<Role, Role> enablers = getEnablers();
		if (enablers.containsKey(n.dst))

		{
			return n;
		}
		Map<Role, Role> res = new HashMap<>(enablers);
		res.put(n.dst, n.src);
		setEnablers(res);
		return n;
	}

	@Override
	public final SType<Global, GSeq> visitDo(Do<Global, GSeq> n) throws ScribException
	{
		throw new RuntimeException(this.getClass() + " unsupported for Do: " + n);
	}
	
	protected Map<Role, Role> getEnablers()
	{
		return this.enablers;
	}
	
	// Guards this.enablers unmodifiable
	protected void setEnablers(Map<Role, Role> enabled)
	{
		this.enablers = Collections.unmodifiableMap(enabled);
	}
}
