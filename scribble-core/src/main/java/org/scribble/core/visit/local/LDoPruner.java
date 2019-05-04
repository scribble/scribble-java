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
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.core.job.Core;
import org.scribble.core.lang.Protocol;
import org.scribble.core.type.kind.Local;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.Choice;
import org.scribble.core.type.session.Do;
import org.scribble.core.type.session.Recursion;
import org.scribble.core.type.session.SType;
import org.scribble.core.type.session.Seq;
import org.scribble.core.type.session.local.LSeq;
import org.scribble.core.visit.STypeVisitorNoThrow;

// Cf. RecPruner
public class LDoPruner //extends DoPruner<Local, LSeq>
		extends STypeVisitorNoThrow<Local, LSeq>
{
	protected final Core core;

	protected LDoPruner(Core core)
	{
		this.core = core;
	}
	
	// CHECKME: vf?
	protected SubprotoRoleCollector newRoleCollector()
	{
		return new SubprotoRoleCollector(this.core);
	}

	@Override
	public SType<Local, LSeq> visitDo(Do<Local, LSeq> n)
	{
		Protocol<Local, ?, LSeq> target = n.getTarget(this.core);
		Set<Role> rs = target.def.visitWithNoThrow(newRoleCollector());  // N.B. does subproto visiting, unlike RoleGather

		System.out.println("aaa: " + rs);

		if (rs.isEmpty())
		{
			return this.core.config.tf.local.LSeq(null, Collections.emptyList());  // Cf. LSkip (currently, no GSkip -- CHECKME: make one?)
		}

		return n;  // Cf. unit(n)
	}

	@Override
	public SType<Local, LSeq> visitChoice(Choice<Local, LSeq> n)
	{
		List<LSeq> blocks = n.blocks.stream().map(x -> visitSeq(x))
				.filter(x -> !x.isEmpty()).collect(Collectors.toList());
		if (blocks.isEmpty())
		{
			return n.blocks.get(0).reconstruct(null, blocks);  // N.B. returning a Seq -- handled by visitSeq (similar to LSkip for locals)
		}
		return n.reconstruct(n.getSource(), n.subj, blocks);
	}

	@Override
	public SType<Local, LSeq> visitRecursion(Recursion<Local, LSeq> n)
	{
		LSeq body = visitSeq(n.body);
		return body.isEmpty() ? body : n.reconstruct(n.getSource(), n.recvar, body);
	}

	@Override
	public LSeq visitSeq(LSeq n)
	{
		List<SType<Local, LSeq>> elems = new LinkedList<>();
		for (SType<Local, LSeq> e : n.elems)
		{
			SType<Local, LSeq> e1 = (SType<Local, LSeq>) e.visitWithNoThrow(this);
			if (e1 instanceof Seq<?, ?>)  // cf. visitDo, empty  (also cf. LSkip)
			{
				elems.addAll(((Seq<Local, LSeq>) e1).elems);  // Handles empty Seq case (actually, the only nested Seq case)
			}
			else
			{
				elems.add(e1);
			}
		}
		return n.reconstruct(n.getSource(), elems);
	}
}