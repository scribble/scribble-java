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
import org.scribble.ast.Choice;
import org.scribble.ast.ScribNode;
import org.scribble.core.type.kind.Global;
import org.scribble.del.DelFactory;

public class GChoice extends Choice<Global> implements GCompoundSessionNode
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
	public List<GProtoBlock> getBlockChildren()
	{
		List<? extends ScribNode> cs = getChildren();
		return cs.subList(Choice.BLOCK_CHILDREN_START_INDEX, cs.size()).stream()
				.map(x -> (GProtoBlock) x).collect(Collectors.toList());
	}
	
	@Override
	public GChoice dupNode()
	{
		return new GChoice(this);
	}
	
	@Override
	public void decorateDel(DelFactory df)
	{
		df.GChoice(this);
	}
}
