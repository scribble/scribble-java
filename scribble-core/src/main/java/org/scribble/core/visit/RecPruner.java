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
import org.scribble.core.type.session.Recursion;
import org.scribble.core.type.session.SType;
import org.scribble.core.type.session.Seq;

// Assumes no shadowing (e.g., use after inlining recvar disamb)
public class RecPruner<K extends ProtoKind, B extends Seq<K, B>>
		extends STypeVisitorNoThrow<K, B>
{
	@Override
	public SType<K, B> visitRecursion(Recursion<K, B> n)
	{
		// Assumes no shadowing (e.g., use after SType#getInlined recvar disamb)
		Set<RecVar> rvs = n.body.gather(new RecVarGatherer<K, B>()::visit)
				.collect(Collectors.toSet());
		return rvs.contains(n.recvar)
				? n  // FIXME: doesn't do a recursive call -- this is just a wrapper for checking getRecVars ... ?
				: n.body;  // i.e., return a Seq, to be "inlined" by Seq.pruneRecs -- N.B. must handle empty Seq case
						// By itself, this can leave an empty choice case (pruned rec under a choice) -- rely on inlining/projection use sites to take care of that
	}

	@Override
	public B visitSeq(B n)
	{
		List<SType<K, B>> elems = new LinkedList<>();
		for (SType<K, B> e : n.elems)
		{
			SType<K, B> e1 = (SType<K, B>) e.visitWithNoThrow(this);
			if (e1 instanceof Seq<?, ?>)  // cf. visitRecursion
			{
				elems.addAll(((Seq<K, B>) e1).getElements());  // Handles empty Seq case
			}
			else
			{
				elems.add(e1);
			}
		}
		return n.reconstruct(n.getSource(), elems);
	}
}
