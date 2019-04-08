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
import java.util.stream.Collectors;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.Choice;
import org.scribble.ast.ScribNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.core.type.kind.Local;

public class LChoice extends Choice<Local> implements LCompoundInteraction
{
	// ScribTreeAdaptor#create constructor
	public LChoice(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	protected LChoice(LChoice node)
	{
		super(node);
	}
	
	@Override
	public LChoice dupNode()
	{
		return new LChoice(this);
	}
	
	@Override
	public List<LProtocolBlock> getBlockChildren()
	{
		List<ScribNode> cs = getChildren();
		return cs.subList(1, cs.size()).stream().map(x -> (LProtocolBlock) x)
				.collect(Collectors.toList());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public LChoice(CommonTree source, RoleNode subj, List<LProtocolBlock> blocks)
	{
		super(source, subj, blocks);
	}

	/*@Override
	protected ScribNodeBase copy()
	{
		return new LChoice(this.source, this.subj, getBlockChildren());
	}
	
	@Override
	public LChoice clone(AstFactory af)
	{
		RoleNode subj = this.subj.clone(af);
		List<LProtocolBlock> blocks = ScribUtil.cloneList(af, getBlockChildren());
		return af.LChoice(this.source, subj, blocks);
	}*/

	/*@Override
	public LChoice reconstruct(RoleNode subj, List<? extends ProtocolBlock<Local>> blocks)
	{
		ScribDel del = del();
		LChoice lc = new LChoice(this.source, subj, castBlocks(blocks));
		lc = (LChoice) lc.del(del);
		return lc;
	}*/
}
