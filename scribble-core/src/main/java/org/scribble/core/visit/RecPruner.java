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
package org.scribble.core.visit;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.core.type.kind.ProtoKind;
import org.scribble.core.type.name.RecVar;
import org.scribble.core.type.session.Choice;
import org.scribble.core.type.session.Recursion;
import org.scribble.core.type.session.SType;
import org.scribble.core.type.session.Seq;
import org.scribble.core.visit.gather.RecVarGatherer;

// Assumes no shadowing, e.g., use after inlining recvar disamb (also used after projection)
public class RecPruner<K extends ProtoKind, B extends Seq<K, B>>
		extends STypeVisitorNoThrow<K, B>
{
	protected RecPruner()
	{
		
	}

	@Override
	public SType<K, B> visitChoice(Choice<K, B> n)
	{
		List<B> blocks = n.blocks.stream().map(x -> visitSeq(x))
				.filter(x -> !x.isEmpty()).collect(Collectors.toList());
		if (blocks.isEmpty())
		{
			return n.blocks.get(0).reconstruct(null, blocks);  // N.B. returning a Seq -- handled by visitSeq (similar to LSkip for locals)
		}
		return n.reconstruct(n.getSource(), n.subj, blocks);
	}

	@Override
	public SType<K, B> visitRecursion(Recursion<K, B> n)
	{
		// Assumes no shadowing (e.g., use after SType#getInlined recvar disamb)
		Set<RecVar> rvs = n.body.gather(new RecVarGatherer<K, B>()::visit)
				.collect(Collectors.toSet());
		return rvs.contains(n.recvar)
				? n.reconstruct(n.getSource(), n.recvar, visitSeq(n.body))
				: n.body;  // i.e., return a Seq, to be "inlined" by Seq.pruneRecs -- N.B. must handle empty Seq case
	}

	@Override
	public B visitSeq(B n)
	{
		List<SType<K, B>> elems = new LinkedList<>();
		for (SType<K, B> e : n.elems)
		{
			SType<K, B> e1 = (SType<K, B>) e.visitWithNoThrow(this);
			if (e1 instanceof Seq<?, ?>)  // cf. visitRecursion  (also cf. LSkip)
			{
				elems.addAll(((Seq<K, B>) e1).elems);//getElements());  // Handles empty Seq case
			}
			else
			{
				elems.add(e1);
			}
		}
		return n.reconstruct(n.getSource(), elems);
	}
}
