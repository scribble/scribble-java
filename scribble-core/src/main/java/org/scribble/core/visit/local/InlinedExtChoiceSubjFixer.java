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
package org.scribble.core.visit.local;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.core.type.kind.Local;
import org.scribble.core.type.name.ProtoName;
import org.scribble.core.type.name.RecVar;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.Choice;
import org.scribble.core.type.session.Continue;
import org.scribble.core.type.session.DirectedInteraction;
import org.scribble.core.type.session.Do;
import org.scribble.core.type.session.Recursion;
import org.scribble.core.type.session.SType;
import org.scribble.core.type.session.local.LSeq;
import org.scribble.core.visit.STypeAggNoThrow;
import org.scribble.core.visit.STypeVisitorNoThrow;

// Easier to separate from InlinedProjector (i.e., finish projection first, then fix)
// (Doing "at the time" during projection requires, e.g., recursion body projections for "inference" on recursion entry, but the body can't really be projected yet without that upfront "inference")
public class InlinedExtChoiceSubjFixer extends STypeVisitorNoThrow<Local, LSeq>
{
	private Map<RecVar, Optional<Role>> recs = new HashMap<>();
			// Record on entering rec states, to give to InlinedEnablerInferer on nested choice states (for unguarded continues)

	@Override
	public Choice<Local, LSeq> visitChoice(Choice<Local, LSeq> n)
	{
		InlinedEnablerInferer v = new InlinedEnablerInferer(this.recs);
		Optional<Role> subj = n.visitWithNoThrow(v);
		return n.reconstruct(n.getSource(), subj.get(), n.blocks);
	}

	@Override
	public <N extends ProtoName<Local>> SType<Local, LSeq> visitDo(
			Do<Local, LSeq, N> n)
	{
		throw new RuntimeException("Unsupported for Do: " + n);
	}

	@Override
	public Recursion<Local, LSeq> visitRecursion(Recursion<Local, LSeq> n)
	{
		Optional<Role> r = new InlinedEnablerInferer(this.recs).visitSeq(n.body);  // recs unnecessary?
		this.recs.put(n.recvar, r);  // null, empty or OK
		return n.reconstruct(n.getSource(), n.recvar, visitSeq(n.body));
	}
}

class InlinedEnablerInferer
		extends STypeAggNoThrow<Local, LSeq, Optional<Role>>
		// Optional.empty signifies inference did not succeed, either outright bad (should be caught by WF somewhere) or an "empty" context
{
	private Map<RecVar, Optional<Role>> recs;

	public InlinedEnablerInferer(Map<RecVar, Optional<Role>> recs)
	{
		this.recs = Collections.unmodifiableMap(recs);
	}

	@Override
	public Optional<Role> visitChoice(Choice<Local, LSeq> n)
	{
		// If inference does not succeed, keep the original
		// Such bad cases due, e.g., any inconsistency, will be caught as bad WF somewhere else (e.g., reachability)
		// Otherwise, should be "empty" cases, so keep original as a default
		List<Optional<Role>> enablers = n.blocks.stream().map(x -> visitSeq(x))
				.collect(Collectors.toList());  // Each elem is null, empty or isPresent
		if (enablers.stream().anyMatch(x -> !x.isPresent()))
		{
			//return Optional.empty();
			return Optional.of(n.subj);
					// CHECKME: can enablers be empty? i.e., choice was "empty" somehow?
					// Yes, when not assuming, e.g., empty block filtering by InlinedProject
		}
		Set<Role> rs = enablers.stream().map(x -> x.get())
				.collect(Collectors.toSet());
		return Optional.of(rs.size() > 1 ? n.subj : rs.iterator().next());
	}

	@Override
	public Optional<Role> visitContinue(Continue<Local, LSeq> n)
	{
		return this.recs.containsKey(n.recvar)
				? this.recs.get(n.recvar)
				: Optional.empty();
						// Empty corner case, e.g., bad sequence after unguarded continue (will be caught be reachability, e.g., bad.reach.globals.gdo.Test04)
						// Empty allows (bad) Seq to continue to next element
	}

	@Override
	public Optional<Role> visitDirectedInteraction(
			DirectedInteraction<Local, LSeq> n)
	{
		return Optional.of(n.src);
	}

	@Override
	public <N extends ProtoName<Local>> Optional<Role> visitDo(
			Do<Local, LSeq, N> n)
	{
		throw new RuntimeException("Unsupported for Do: " + n);
	}

	@Override
	public Optional<Role> visitSeq(LSeq n)
	{
		for (SType<Local, LSeq> e : n.elems)
		{
			Optional<Role> res = e.visitWithNoThrow(this);
			if (res.isPresent())
			{
				return res;
			}
		}
		return Optional.empty();
	}

	@Override
	protected Optional<Role> unit(SType<Local, LSeq> n)
	{
		return Optional.empty();
	}

	// Currently only used by Recursion, Choice/Seq overrides don't use
	@Override
	protected Optional<Role> agg(SType<Local, LSeq> n, Stream<Optional<Role>> ts)
	{
		List<Optional<Role>> enabs = ts.collect(Collectors.toList());
		if (enabs.stream().anyMatch(x -> !x.isPresent()))
		{
			return Optional.empty();
					// CHECKME: can enablers be empty? i.e., choice was "empty" somehow?
					// Yes, when not assuming, e.g., empty block filtering by InlinedProject
		}
		Set<Role> rs = enabs.stream().map(x -> x.get())
				.collect(Collectors.toSet());
		return rs.size() > 1 ? Optional.empty() : Optional.of(rs.iterator().next());
	}
}