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

import java.util.List;
import java.util.stream.Collectors;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.Choice;
import org.scribble.ast.ScribNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.core.type.kind.Global;

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
