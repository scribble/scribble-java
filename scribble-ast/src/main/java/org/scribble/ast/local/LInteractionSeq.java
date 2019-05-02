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
import org.scribble.ast.InteractionSeq;
import org.scribble.core.type.kind.Local;
import org.scribble.del.DelFactory;

public class LInteractionSeq extends InteractionSeq<Local>
		implements LSessionNode
{
	// ScribTreeAdaptor#create constructor
	public LInteractionSeq(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public LInteractionSeq(LInteractionSeq node)
	{
		super(node);
	}
	
	@Override
	public List<LSessionNode> getInteractionChildren()
	{
		return getChildren().stream().map(n -> (LSessionNode) n)
				.collect(Collectors.toList());
	}
	
	@Override
	public LInteractionSeq dupNode()
	{
		return new LInteractionSeq(this);
	}
	
	@Override
	public void decorateDel(DelFactory df)
	{
		df.LInteractionSeq(this);
	}
}
