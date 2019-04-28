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
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.DelFactory;

// CHECKME: can this be simplified to an interface?  (also NonRoleArg)
public class RoleArg extends DoArg<RoleNode>
{
	// ScribTreeAdaptor#create constructor
	public RoleArg(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public RoleArg(RoleArg node)
	{
		super(node);
	}
	
	@Override
	public RoleNode getArgNodeChild()
	{
		return (RoleNode) getChild(0);
	}
	
	@Override
	public RoleArg dupNode()
	{
		return new RoleArg(this);
	}
	
	@Override
	public void decorateDel(DelFactory df)
	{
		df.RoleArg(this);
	}
}
