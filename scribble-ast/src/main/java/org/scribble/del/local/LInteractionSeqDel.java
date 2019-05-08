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
package org.scribble.del.local;

import org.scribble.del.InteractionSeqDel;

public class LInteractionSeqDel extends InteractionSeqDel implements LDel
{
	/*@Override
	public ScribNode leaveProjectedChoiceDoPruning(ScribNode parent,
			ScribNode child, ProjectedChoiceDoPruner pruner, ScribNode visited)
			throws ScribException
	{
		LInteractionSeq lc = (LInteractionSeq) visited;
		List<LSessionNode> actions = lc.getInteractionChildren().stream()
				.filter(li -> li != null).collect(Collectors.toList());
		return lc.reconstruct(actions);
	}*/
	
	/*// Duplicated from GInteractionSeq
	@Override
	public LInteractionSeq leaveRecRemoval(ScribNode parent, ScribNode child, RecRemover rem, ScribNode visited)
			throws ScribException
	{
		LInteractionSeq lis = (LInteractionSeq) visited;
		List<LSessionNode> lins = lis.getInteractions().stream().flatMap((li) -> 
					(li instanceof LRecursion && rem.toRemove(((LRecursion) li).recvar.toName()))
						? ((LRecursion) li).getBlock().getInteractionSeq().getInteractions().stream()
						: Stream.of(li)
				).collect(Collectors.toList());
		return rem.job.af.LInteractionSeq(lis.getSource(), lins);
	}*/
}
