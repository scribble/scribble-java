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
import org.antlr.runtime.tree.CommonTree;
import org.scribble.core.type.name.Role;

public class NonRoleArg extends DoArg<NonRoleArgNode>
{
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
	public NonRoleArgNode getValChild()
	{
		return (NonRoleArgNode) getChild(0);
	}
	
	@Override
	public NonRoleArg dupNode()
	{
		return new NonRoleArg(this);
	}
	
	@Override
	public NonRoleArg project(AstFactory af, Role self)
	{
		return af.NonRoleArg(this.source, getValChild());  // arg needs projection?
	}
	
	
	
	
	
	
	
	
	
	public NonRoleArg(CommonTree source, NonRoleArgNode arg)
	{
		super(source, arg);
	}

	/*@Override
	protected ScribNodeBase copy()
	{
		return new NonRoleArg(this.source, getValChild());
	}
	
	@Override
	public NonRoleArg clone(AstFactory af)
	{
		NonRoleArgNode arg = (NonRoleArgNode) getValChild().clone(af);
		return af.NonRoleArg(this.source, arg);
	}

	@Override
	public NonRoleArg reconstruct(NonRoleArgNode arg)
	{
		ScribDel del = del();
		NonRoleArg ai = new NonRoleArg(this.source, arg);
		ai = (NonRoleArg) ai.del(del);
		return ai;
	}*/
}
