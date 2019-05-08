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
	protected final Map<RecVar, Optional<Role>> recs = new HashMap<>();
			// Record on entering rec states, to give to InlinedEnablerInferer on nested choice states (for unguarded continues)
			// CHECKME: Optional necessary?  InlinedEnablerInferer may not succeed due to empty/bad contexts, but should that be reflected here?

	protected InlinedExtChoiceSubjFixer()
	{
		
	}
	
	// To override for handling subproto
	// Takes "this" (cf. this.recs), better pattern for extensibility
	protected InlinedEnablerInferer getInferer(InlinedExtChoiceSubjFixer v)//Map<RecVar, Optional<Role>> recs)
	{
		return new InlinedEnablerInferer(this);
	}

	@Override
	public Choice<Local, LSeq> visitChoice(Choice<Local, LSeq> n)
	{
		InlinedEnablerInferer v = getInferer(this);
		Role subj = n.visitWithNoThrow(v).get();  // WF ensures result
		return n.reconstruct(n.getSource(), subj, n.blocks);
	}

	@Override
	public SType<Local, LSeq> visitDo(Do<Local, LSeq> n)
	{
		throw new RuntimeException("Unsupported for Do: " + n);
	}

	@Override
	public Recursion<Local, LSeq> visitRecursion(Recursion<Local, LSeq> n)
	{
		InlinedEnablerInferer v = getInferer(this);  // Pattern: Inferer to access (and copy) this.recs directly
		Optional<Role> res = v.visitSeq(n.body);  // Can be empty or OK
		this.recs.put(n.recvar, res);
		LSeq body = visitSeq(n.body);
		return n.reconstruct(n.getSource(), n.recvar, body);
	}
}

class InlinedEnablerInferer extends STypeAggNoThrow<Local, LSeq, Optional<Role>>
		// Optional.empty signifies "inference" did not succeed, either an outright bad (should be caught by WF somewhere) or an "empty" context
{
	private final Map<RecVar, Optional<Role>> recs;

	public InlinedEnablerInferer(InlinedExtChoiceSubjFixer v)//Map<RecVar, Optional<Role>> recs)
	{
		this.recs = Collections.unmodifiableMap(v.recs);
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
	public Optional<Role> visitDo(Do<Local, LSeq> n)
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