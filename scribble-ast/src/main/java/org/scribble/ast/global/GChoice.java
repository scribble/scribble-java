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

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.Choice;
import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LChoice;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.core.type.kind.Global;
import org.scribble.core.type.name.Role;
import org.scribble.util.RuntimeScribException;
import org.scribble.util.ScribException;

public class GChoice extends Choice<Global> implements GCompoundInteraction
{
	// ScribTreeAdaptor#create constructor
	public GChoice(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	protected GChoice(GChoice node)
	{
		super(node);
	}
	
	@Override
	public GChoice dupNode()
	{
		return new GChoice(this);
	}
	
	@Override
	public List<GProtocolBlock> getBlockChildren()
	{
		List<ScribNode> cs = getChildren();
		return cs.subList(1, cs.size()).stream().map(x -> (GProtocolBlock) x)
				.collect(Collectors.toList());
	}

	// Similar pattern to reconstruct
	// Idea is, if extending the AST class (more fields), then reconstruct/project should also be extended (and called from extended del)
	public LChoice project(AstFactory af, Role self, List<LProtocolBlock> blocks)
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
			RoleNode subj = getSubjectChild();
			// CHECKME: initially keep global subject, and later overwrite as necessary in projections? (algorithm currently checks for DUMMY)
			RoleNode subj1 = self.equals(subj.toName()) 
					? subj.clone()//af)
					: af.DummyProjectionRoleNode();
			List<LChoice> cs = blocks.stream()
					.map(b -> af.LChoice(this.source, subj1, Arrays.asList(b)))
					.collect(Collectors.toList());
				// Hacky: keeping this.source for each LChoice (will end up as the source for the final merged LChoice)
			LChoice merged = cs.get(0);
			try
			{
				for (int i = 1; i < cs.size(); i++)
				{
					merged = merged.merge(af, cs.get(i)); // Merge currently does "nothing"; validation takes direct non-deterministic interpretation -- purpose of syntactic merge is to convert non-det to "equivalent" safe det in certain sitations
				}
			}
			catch (ScribException e)  // HACK
			{
				throw new RuntimeScribException(e);
			}
			projection = merged;
		}

		return projection;
	}
	
	
	
	
	
	
	
	
	
	

	public GChoice(CommonTree source, RoleNode subj, List<GProtocolBlock> blocks)
	{
		super(source, subj, blocks);
	}

	/*@Override
	protected ScribNodeBase copy()
	{
		return new GChoice(this.source, this.subj, getBlocks());
	}
	
	@Override
	public GChoice clone(AstFactory af)
	{
		RoleNode subj = this.subj.clone(af);
		List<GProtocolBlock> blocks = ScribUtil.cloneList(af, getBlocks());
		return af.GChoice(this.source, subj, blocks);
	}*/

	/*@Override
	public GChoice reconstruct(RoleNode subj, List<? extends ProtocolBlock<Global>> blocks)
	{
		ScribDel del = del();
		GChoice gc = new GChoice(this.source, subj, castBlocks(blocks));
		gc = (GChoice) gc.del(del);
		return gc;
	}*/
}
