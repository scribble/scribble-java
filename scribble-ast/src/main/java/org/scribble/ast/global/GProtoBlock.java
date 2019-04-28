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

import org.antlr.runtime.Token;
import org.scribble.ast.ProtoBlock;
import org.scribble.core.type.kind.Global;
import org.scribble.del.DelFactory;

public class GProtoBlock extends ProtoBlock<Global> implements GScribNode
{
	// ScribTreeAdaptor#create constructor
	public GProtoBlock(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	protected GProtoBlock(GProtoBlock node)
	{
		super(node);
	}

	@Override
	public GInteractionSeq getInteractSeqChild()
	{
		return (GInteractionSeq) getChild(ProtoBlock.SEQ_CHILD_INDEX);
	}

	@Override
	public GProtoBlock dupNode()
	{
		return new GProtoBlock(this);
	}

	@Override
	public void decorateDel(DelFactory df)
	{
		df.GProtoBlock(this);
	}
}
