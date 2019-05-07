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
import org.scribble.core.lang.local.LProtocol;
import org.scribble.core.type.kind.Local;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.DirectedInteraction;
import org.scribble.core.type.session.DisconnectAction;
import org.scribble.core.type.session.Do;
import org.scribble.core.type.session.SType;
import org.scribble.core.type.session.local.LSeq;
import org.scribble.core.visit.STypeAggNoThrow;
import org.scribble.core.visit.STypeVisitorNoThrow;
import org.scribble.core.visit.Substitutor;

// Pre: SubprotoProjector -- "self" fixed for interactions, choice-subjs and do role-args
// Prunes LProto/Proj role decls and do role-args
public class LRoleDeclAndDoArgPruner extends STypeVisitorNoThrow<Local, LSeq>
{
	private final Core core;
	
	protected LRoleDeclAndDoArgPruner(Core core)
	{
		this.core = core;
	}
	
	// CHECKME: vf?
	protected PreSubprotoRoleCollector newPreRoleCollector()  // N.B. "Pre" role collector
	{
		return new PreSubprotoRoleCollector(this.core);
	}
	
	public LProtocol visitLProtocol(LProtocol n)
	{
		// N.B. must use PreRoleCollector (on orig), standard subproto traversal not possible yet because target proto role decls not yet fixed (being done now)
		Set<Role> used = n.def.visitWithNoThrow(newPreRoleCollector());  // CHECKME: vf?
		List<Role> rs = n.roles.stream()
				.filter(x -> used.contains(x) || x.equals(n.self))  // FIXME: self roledecl not actually being a self role is a mess
				.collect(Collectors.toList());
		// N.B. *role decls* (cf. do-args) don't feature "self" (cf. LSelfDecl)
		LSeq pruned = visitSeq(n.def);
		return n.reconstruct(n.getSource(), n.mods, n.fullname, rs, n.self,
				n.params, pruned);  // CHECKME: prune params?
	}

	public Do<Local, LSeq> visitDo(Do<Local, LSeq> n)
	{
		List<Role> fixed = new LinkedList<>();
		Set<Role> rs = n.visitWithNoThrow(newPreRoleCollector());  // N.B. does subproto visiting, unlike RoleGatherer
		fixed = n.roles.stream()
				/*.filter(x -> rs.contains(x) || x.equals(this.self))
				.map(x -> x.equals(this.self) ? Role.SELF : x)		// FIXME: self roledecl not actually being a self role is a mess*/
				.filter(x -> rs.contains(x) || x.equals(Role.SELF))
				.collect(Collectors.toList());
		return n.reconstruct(n.getSource(), n.proto, fixed, n.args);  // CHECKME: prune args?
	}
}


// Role collector for pre do-arg pruning (i.e., only for use by LDoArgPruner):
// do-arg arity doesn't yet match target, and "self" not done in do-args (see InlinedProjector.visitDo)
class PreSubprotoRoleCollector extends SubprotoRoleCollector
{
	public PreSubprotoRoleCollector(Core core)
	{
		super(core);
	}

	@Override
	public Set<Role> visitDo(Do<Local, LSeq> n)
	{
		SubprotoSig sig = new SubprotoSig(n.proto, n.roles, n.args);
		if (this.stack.contains(sig))
		{
			return unit(n);  // empty set
		}

		this.stack.push(sig);
		LProjection target = (LProjection) n.getTarget(this.core);
		List<Role> tmp = this.core.getContext().getInlined(target.global).roles
						// Currently, do-args arity matches that of inlined, i.e., not necessarily the arity of the actual local target (cf. InlinedProjector.visitDo)
				.stream()//.map(x -> x.equals(target.self) ? Role.SELF : x)  
						// "self" for do-args not done yet (cf., SubprotoProjector.visitDo), now being fixed by LDoArgPruner
						// Cf., LSubprotoVisitorNoThrow.prepareSubprotoForVisit
				.collect(Collectors.toList());
		Substitutor<Local, LSeq> subs = this.core.config.vf
				.Substitutor(tmp, n.roles, target.params, n.args, true);  // true (passive) to ignore "self"  // CHECKME: prune args?
		Set<Role> res = target.def.visitWithNoThrow(subs).visitWithNoThrow(this);
		this.stack.pop();
		return res;
	}
}


// CHECKME: simply integrate into one with PreSubprotoRoleCollector?  Or maybe still separately useful?
class SubprotoRoleCollector extends STypeAggNoThrow<Local, LSeq, Set<Role>> 
		implements LSubprotoVisitorNoThrow<Set<Role>>
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

	@Override
	public Set<Role> visitDisconnect(DisconnectAction<Local, LSeq> n)
	{
		return Stream.of(n.left, n.right).collect(Collectors.toSet());
	}

	@Override
	public Set<Role> visitDo(Do<Local, LSeq> n)
	{
		SubprotoSig sig = new SubprotoSig(n.proto, n.roles, n.args);
		if (this.stack.contains(sig))
		{
			return unit(n);  // empty set
		}

		// Cf. LDoPruner.visitDo
		this.stack.push(sig);
		Set<Role> res = prepareSubprotoForVisit(this.core, n)  // Non-passive, cf. PreSubprotoRoleCollector
				.visitWithNoThrow(this);
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
