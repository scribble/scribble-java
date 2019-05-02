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
import org.scribble.del.DelFactory;

public class LProjectionDecl extends LProtoDecl
{
	//public final GProtoName parent;  // FIXME: additional ast child?

	// ScribTreeAdaptor#create constructor
	public LProjectionDecl(Token t)
	{
		super(t);
	}
	
	// Tree#dupNode constructor
	protected LProjectionDecl(LProtoDecl node)
	{
		super(node);
	}

	// Cf. CommonTree#dupNode
	@Override
	public LProtoDecl dupNode()
	{
		return new LProtoDecl(this);
	}
	
	@Override
	public void decorateDel(DelFactory df)
	{
		throw new RuntimeException("TODO: " + this);
	}
	
	@Override
	public String toString()
	{
		return "TODO";
	}
}
