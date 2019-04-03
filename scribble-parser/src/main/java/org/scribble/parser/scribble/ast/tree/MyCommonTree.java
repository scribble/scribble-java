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
package org.scribble.parser.scribble.ast.tree;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.ScribNodeBase;

@Deprecated
public class MyCommonTree extends ScribNodeBase
{
	public MyCommonTree(CommonTree node)
	{
		super(node);
	}

	public MyCommonTree(Token t)
	{
		super(t);
	}

	@Override
	public ScribNodeBase dupNode()
	{
		return new MyCommonTree(this);
	}

	/*@Override
	protected ScribNodeBase copy()
	{
		return (ScribNodeBase) dupNode();
	}

	@Override
	public ScribNodeBase clone(AstFactory af)
	{
		return (ScribNodeBase) dupNode();
	}*/
}
