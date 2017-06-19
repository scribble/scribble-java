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
package org.scribble.ast.local;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.Parallel;
import org.scribble.ast.ProtocolBlock;
import org.scribble.ast.ScribNodeBase;
import org.scribble.del.ScribDel;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.Message;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.context.ProjectedChoiceSubjectFixer;

public class LParallel extends Parallel<Local> implements LCompoundInteractionNode
{
	public LParallel(CommonTree source, List<LProtocolBlock> blocks)
	{
		super(source, blocks);
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new LParallel(this.source, getBlocks());
	}
	
	@Override
	public LParallel clone()
	{
		//List<:ProtocolBlock> blocks = ScribUtil.cloneList(getBlocks());
		throw new RuntimeException("TODO: " + this);
	}
	
	@Override
	public LParallel reconstruct(List<? extends ProtocolBlock<Local>> blocks)
	{
		ScribDel del = del();
		LParallel lp = new LParallel(this.source, castBlocks(blocks));
		lp = (LParallel) lp.del(del);
		return lp;
	}

	@Override
	public List<LProtocolBlock> getBlocks()
	{
		return castBlocks(super.getBlocks());
	}

	@Override
	public Role inferLocalChoiceSubject(ProjectedChoiceSubjectFixer fixer)
	{
		return getBlocks().get(0).getInteractionSeq().getInteractions().get(0).inferLocalChoiceSubject(fixer);
	}

	// FIXME: shouldn't be needed, but here due to Eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=436350
	@Override
	public Local getKind()
	{
		return LCompoundInteractionNode.super.getKind();
	}
	
	private static List<LProtocolBlock> castBlocks(List<? extends ProtocolBlock<Local>> blocks)
	{
		return blocks.stream().map((b) -> (LProtocolBlock) b).collect(Collectors.toList());
	}

	@Override
	public LInteractionNode merge(LInteractionNode ln) throws ScribbleException
	{
		throw new RuntimeException("TODO: " + this);
	}

	@Override
	public boolean canMerge(LInteractionNode ln)
	{
		throw new RuntimeException("TODO: " + this);
	}

	@Override
	public Set<Message> getEnabling()
	{
		throw new RuntimeException("TODO: " + this);
	}
}
