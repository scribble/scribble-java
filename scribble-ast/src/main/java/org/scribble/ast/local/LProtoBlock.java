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

import org.antlr.runtime.Token;
import org.scribble.ast.ProtoBlock;
import org.scribble.core.type.kind.Local;
import org.scribble.del.DelFactory;

public class LProtoBlock extends ProtoBlock<Local> implements LScribNode
{
	// ScribTreeAdaptor#create constructor
	public LProtoBlock(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	protected LProtoBlock(LProtoBlock node)
	{
		super(node);
	}

	@Override
	public LInteractionSeq getInteractSeqChild()
	{
		return (LInteractionSeq) getChild(ProtoBlock.SEQ_CHILD_INDEX);
	}

	@Override
	public LProtoBlock dupNode()
	{
		return new LProtoBlock(this);
	}

	@Override
	public void decorateDel(DelFactory df)
	{
		df.LProtoBlock(this);
	}
}
