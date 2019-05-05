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
import java.util.stream.Collectors;

import org.scribble.core.job.Core;
import org.scribble.core.lang.Protocol;
import org.scribble.core.lang.local.LProjection;
import org.scribble.core.type.kind.Local;
import org.scribble.core.type.name.LProtoName;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.Do;
import org.scribble.core.type.session.SType;
import org.scribble.core.type.session.local.LSeq;
import org.scribble.core.visit.Substitutor;

// Pre: LDoArgPruner (for basic subproto visiting pattern), LDoPruner (to avoid infinite looping when "stackless" subproto visiting)
// super takes care of rec/continue, here just additionally handle "do"
// A "stackless" subproto visitor, c.f. LDoPruner
public class SubprotoExtChoiceSubjFixer extends InlinedExtChoiceSubjFixer
{
	protected final Core core;
	
	protected final Map<LProtoName, Optional<Role>> protos = new HashMap<>();

	protected SubprotoExtChoiceSubjFixer(Core core)
	{
		this.core = core;
	}
	
	// To visit top-level proto, reconstruct for fixed subjs -- nested visting (by Inferer) only does passive inference (no reconstruct)
	// Must enter here, for initial this.protos entry
	// TODO: refactor into LProjection/LProtocol -- cf. GProtocol
	public Protocol<Local, LProtoName, LSeq> visitProtocol(
			Protocol<Local, LProtoName, LSeq> n)
	{
		this.protos.clear();  // Reusable
		
		// Cf. InlinedExtChoiceSubjFixer.visitRecursion
		InlinedEnablerInferer v = getInferer(this);
		Optional<Role> res = v.visitSeq(n.def);
		this.protos.put(n.fullname, res);
		LSeq def = visitSeq(n.def);
		return n.reconstruct(n.getSource(), n.mods, n.fullname, n.roles, n.params,
				def);
				// N.B. this reconstruct implicitly retains n.self
	}
	
	@Override
	protected SubprotoEnablerInferer getInferer(InlinedExtChoiceSubjFixer v)
	{
		return new SubprotoEnablerInferer(v);
	}

	@Override
	public SType<Local, LSeq> visitDo(Do<Local, LSeq> n)
	{
		return unit(n);  // From STypeAggNoThrow
	}
}

class SubprotoEnablerInferer extends InlinedEnablerInferer  // super takes care of rec/continue
{
	private final Core core;

	private final Map<LProtoName, Optional<Role>> protos;

	public SubprotoEnablerInferer(InlinedExtChoiceSubjFixer v)
	{
		super(v);
		SubprotoExtChoiceSubjFixer cast = (SubprotoExtChoiceSubjFixer) v;
		this.core = cast.core;
		this.protos = Collections.unmodifiableMap(cast.protos);
	}

	@Override
	public Optional<Role> visitDo(Do<Local, LSeq> n)
	{
		if (this.protos.containsKey(n.proto))
		{
			return this.protos.get(n.proto);
		}

		// Cf. LDoPruner.visitDo
		LProjection imed = (LProjection) n.getTarget(this.core);
		// FIXME: factor out with LDoPruner
		List<Role> tmp = imed.roles.stream()
				.map(x -> x.equals(imed.self) ? Role.SELF : x)  // FIXME: self roledecl not actually being a self role is a mess
				.collect(Collectors.toList());
		Substitutor<Local, LSeq> subs = this.core.config.vf.Substitutor(tmp,
				n.roles, imed.params, n.args, true);  // true (passive) for fixing ext-choice subjs (e.g., bad.liveness.roleprog.unfair.Test06)
		return visitSeq(subs.visitSeq(imed.def)); 
	}
}
