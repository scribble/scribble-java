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
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.core.job.Core;
import org.scribble.core.lang.SubprotoSig;
import org.scribble.core.lang.local.LProjection;
import org.scribble.core.type.kind.Local;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.DirectedInteraction;
import org.scribble.core.type.session.DisconnectAction;
import org.scribble.core.type.session.Do;
import org.scribble.core.type.session.SType;
import org.scribble.core.type.session.local.LSeq;
import org.scribble.core.visit.STypeAggNoThrow;
import org.scribble.core.visit.Substitutor;

// Cf. InlinedEnablerInferer (InlinedExtChoiceSubjFixer)
// Results for locals will contain "self"
class SubprotoRoleCollector extends STypeAggNoThrow<Local, LSeq, Set<Role>>
{
	protected final Core core;

	protected Deque<SubprotoSig> stack = new LinkedList<>();
	
	public SubprotoRoleCollector(Core core)
	{
		this.core = core;
	}
	
	@Override
	public Set<Role> visitDirectedInteraction(DirectedInteraction<Local, LSeq> n)
	{
		return Stream.of(n.src, n.dst).collect(Collectors.toSet());
	}

	public Set<Role> visitDisconnect(DisconnectAction<Local, LSeq> n)
	{
		return Stream.of(n.left, n.right).collect(Collectors.toSet());
	}

	public Set<Role> visitDo(Do<Local, LSeq> n)
	{
		SubprotoSig sig = new SubprotoSig(n.proto, n.roles, n.args);
		if (this.stack.contains(sig))
		{
			return unit(n);  // empty set
		}
		this.stack.push(sig);
		LProjection target = (LProjection) n.getTarget(this.core);
		List<Role> tmp = target.roles.stream()
				.map(x -> x.equals(target.self) ? Role.SELF : x)  // FIXME: self roledecl not actually being a self role is a mess
				.collect(Collectors.toList());
		Substitutor<Local, LSeq> subs = this.core.config.vf
				.Substitutor(tmp, n.roles, target.params, n.args);  // CHECKME: prune args?
		Set<Role> res = target.def.visitWithNoThrow(subs).visitWithNoThrow(this);
		this.stack.pop();
		return res;
	}

	@Override
	protected Set<Role> unit(SType<Local, LSeq> n)
	{
		return Collections.emptySet();
	}

	@Override
	protected Set<Role> agg(SType<Local, LSeq> n, Stream<Set<Role>> ts)
	{
		return ts.flatMap(x -> x.stream()).collect(Collectors.toSet());
	}
}