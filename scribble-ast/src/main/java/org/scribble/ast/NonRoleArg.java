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
package org.scribble.ast;

import org.antlr.runtime.Token;
import org.scribble.del.DelFactory;

public class NonRoleArg extends DoArg<NonRoleArgNode>
{
	public static final int ARG_NODE_CHILD_INDEX = 0;

	// ScribTreeAdaptor#create constructor
	public NonRoleArg(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public NonRoleArg(NonRoleArg node)
	{
		super(node);
	}
	
	@Override
	public NonRoleArgNode getArgNodeChild()
	{
		return (NonRoleArgNode) getChild(ARG_NODE_CHILD_INDEX);
	}
	
	@Override
	public NonRoleArg dupNode()
	{
		return new NonRoleArg(this);
	}
	
	@Override
	public void decorateDel(DelFactory df)
	{
		df.NonRoleArg(this);
	}
}
