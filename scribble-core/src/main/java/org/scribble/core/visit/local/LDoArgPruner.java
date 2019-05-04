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

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.core.job.Core;
import org.scribble.core.lang.SubprotoSig;
import org.scribble.core.lang.global.GProtocol;
import org.scribble.core.lang.local.LProjection;
import org.scribble.core.type.kind.Local;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.Do;
import org.scribble.core.type.session.local.LSeq;
import org.scribble.core.visit.STypeVisitorNoThrow;
import org.scribble.core.visit.Substitutor;

public class LDoArgPruner extends STypeVisitorNoThrow<Local, LSeq>
{
	private final Core core;
	
	public LDoArgPruner(Core core)
	{
		this.core = core;
	}
	
	// CHECKME: vf?
	protected PreSubprotoRoleCollector newPreRoleCollector()  // N.B. "Pre" role collector
	{
		return new PreSubprotoRoleCollector(this.core);
	}
	
	// TODO: rename class?  also fixes projection role decls
	// TODO: refactor into LProjection/LProtocol -- cf. GProtocol
	public LProjection visitProjection(
			LProjection n)
	{
		Set<Role> used = n.def.visitWithNoThrow(newPreRoleCollector());  // CHECKME: vf?
		List<Role> rs = n.roles.stream()
				.filter(x -> used.contains(x) || x.equals(n.self))  // FIXME: self roledecl not actually being a self role is a mess
				.collect(Collectors.toList());
		LSeq pruned = visitSeq(n.def);
		return n.reconstruct(n.getSource(), n.mods, n.fullname, rs, n.self,
				n.params, pruned);
				// CHECKME: prune params?
	}

	public Do<Local, LSeq> visitDo(Do<Local, LSeq> n)
	{
		LProjection target = (LProjection) n.getTarget(this.core);
		GProtocol inlined = this.core.getContext().getInlined(target.global); 
				// n.roles arity currently matches inlined.roles (cf. InlinedProjector.visitDo)
		/*Iterator<Role> curr = n.roles.iterator();*/
		List<Role> pruned = new LinkedList<>();
		/*for (Role r : inlined.roles)
		{
			Role next = curr.next();
			if (target.roles.contains(r))
			{
				pruned.add(next);
			}
		}*/
		Set<Role> rs = n.visitWithNoThrow(newPreRoleCollector());  // N.B. does subproto visiting, unlike RoleGather
		pruned = inlined.roles.stream()
				.filter(x -> rs.contains(x) || x.equals(target.self))
				.map(x -> x.equals(target.self) ? Role.SELF : x)		// FIXME: self roledecl not actually being a self role is a mess
				.collect(Collectors.toList());
		return n.reconstruct(n.getSource(), n.proto, pruned, n.args);  // CHECKME: prune args?
	}
}


// Role collector for pre do-arg pruning: do-arg arity doesn't yet match target
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
				.stream().map(x -> x.equals(target.self) ? Role.SELF : x)  // FIXME: self roledecl not actually being a self role is a mess
				.collect(Collectors.toList());
		Substitutor<Local, LSeq> subs = this.core.config.vf
				.Substitutor(tmp, n.roles, target.params, n.args);  // CHECKME: prune args?
		Set<Role> res = target.def.visitWithNoThrow(subs).visitWithNoThrow(this);
		this.stack.pop();
		return res;
	}
}
