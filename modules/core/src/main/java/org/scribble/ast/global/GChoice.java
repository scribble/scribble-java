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
package org.scribble.ast.global;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.Choice;
import org.scribble.ast.ProtocolBlock;
import org.scribble.ast.ScribNodeBase;
import org.scribble.ast.local.LChoice;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ScribDel;
import org.scribble.main.RuntimeScribbleException;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.name.Role;
import org.scribble.util.ScribUtil;

public class GChoice extends Choice<Global> implements GCompoundInteractionNode
{
	public GChoice(CommonTree source, RoleNode subj, List<GProtocolBlock> blocks)
	{
		super(source, subj, blocks);
	}
	
	public LChoice project(Role self, List<LProtocolBlock> blocks)
	{
		LChoice projection = null;  // Individual GlobalInteractionNodes become null if not projected -- projected seqs and blocks are never null though
		/*if (blocks.size() == 1)
		{
			if (!blocks.get(0).isEmpty())  // WF allows empty (blocks/seq are never null)
			{
				RoleNode subj = AstFactoryImpl.FACTORY.DummyProjectionRoleNode();
				projection = AstFactoryImpl.FACTORY.LChoice(subj, blocks);
			}
		}
		else //if (blocks.size() > 1)*/
		blocks = blocks.stream().filter((b) -> !b.isEmpty()).collect(Collectors.toList());
		if (!blocks.isEmpty())
		{
			// FIXME? initially keep global subject, and later overwrite as necessary in projections? (algorithm currently checks for DUMMY)
			RoleNode subj = self.equals(this.subj.toName()) ? this.subj.clone() : AstFactoryImpl.FACTORY.DummyProjectionRoleNode();
			List<LChoice> cs = blocks.stream().map((b) -> AstFactoryImpl.FACTORY.LChoice(this.source, subj, Arrays.asList(b))).collect(Collectors.toList());
				// Hacky: keeping this.source for each LChoice (will end up as the source for the final merged LChoice)
			LChoice merged = cs.get(0);
			try
			{
				for (int i = 1; i < cs.size(); i++)
				{
					merged = merged.merge(cs.get(i)); // Merge currently does "nothing"; validation takes direct non-deterministic interpretation -- purpose of syntactic merge is to convert non-det to "equivalent" safe det in certain sitations
				}
			}
			catch (ScribbleException e)  // HACK
			{
				throw new RuntimeScribbleException(e);
			}
			projection = merged;
		}

		return projection;
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new GChoice(this.source, this.subj, getBlocks());
	}
	
	@Override
	public GChoice clone()
	{
		RoleNode subj = this.subj.clone();
		List<GProtocolBlock> blocks = ScribUtil.cloneList(getBlocks());
		return AstFactoryImpl.FACTORY.GChoice(this.source, subj, blocks);
	}

	@Override
	public GChoice reconstruct(RoleNode subj, List<? extends ProtocolBlock<Global>> blocks)
	{
		ScribDel del = del();
		GChoice gc = new GChoice(this.source, subj, castBlocks(blocks));
		gc = (GChoice) gc.del(del);
		return gc;
	}
	
	@Override
	public List<GProtocolBlock> getBlocks()
	{
		return castBlocks(super.getBlocks());
	}
	
	private static List<GProtocolBlock> castBlocks(List<? extends ProtocolBlock<Global>> blocks)
	{
		return blocks.stream().map((b) -> (GProtocolBlock) b).collect(Collectors.toList());
	}

	// FIXME: shouldn't be needed, but here due to Eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=436350
	@Override
	public Global getKind()
	{
		return GCompoundInteractionNode.super.getKind();
	}
}
