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
import org.scribble.ast.ProtoDef;
import org.scribble.core.type.kind.Global;
import org.scribble.del.DelFactory;

public class GProtoDef extends ProtoDef<Global> implements GScribNode
{
	// ScribTreeAdaptor#create constructor
	public GProtoDef(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	protected GProtoDef(GProtoDef node)
	{
		super(node);
	}

	@Override
	public GProtoBlock getBlockChild()
	{
		return (GProtoBlock) getChild(ProtoDef.BLOCK_CHILD_INDEX);
	}
	
	@Override
	public GProtoDef dupNode()
	{
		return new GProtoDef(this);
	}
	
	@Override
	public void decorateDel(DelFactory df)
	{
		df.GProtoDef(this);
	}
}
