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
import org.scribble.ast.Recursion;
import org.scribble.core.type.kind.Local;
import org.scribble.del.DelFactory;

public class LRecursion extends Recursion<Local> implements LCompoundSessionNode
{
	// ScribTreeAdaptor#create constructor
	public LRecursion(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	protected LRecursion(LRecursion node)
	{
		super(node);
	}

	@Override
	public LProtoBlock getBlockChild()
	{
		return (LProtoBlock) getChild(Recursion.BODY_CHILD_INDEX);
	}
	
	@Override
	public LRecursion dupNode()
	{
		return new LRecursion(this);
	}
	
	@Override
	public void decorateDel(DelFactory df)
	{
		df.LRecursion(this);
	}
}
