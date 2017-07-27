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

import org.antlr.runtime.tree.CommonTree;
import org.scribble.del.ScribDel;
import org.scribble.type.name.Role;

public class NonRoleArg extends DoArg<NonRoleArgNode>
{
	public NonRoleArg(CommonTree source, NonRoleArgNode arg)
	{
		super(source, arg);
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new NonRoleArg(this.source, getVal());
	}
	
	@Override
	public NonRoleArg clone(AstFactory af)
	{
		NonRoleArgNode arg = (NonRoleArgNode) getVal().clone(af);
		return af.NonRoleArg(this.source, arg);
	}

	@Override
	public NonRoleArg reconstruct(NonRoleArgNode arg)
	{
		ScribDel del = del();
		NonRoleArg ai = new NonRoleArg(this.source, arg);
		ai = (NonRoleArg) ai.del(del);
		return ai;
	}
	
	@Override
	public NonRoleArgNode getVal()
	{
		return (NonRoleArgNode) super.getVal();
	}
	
	@Override
	public NonRoleArg project(AstFactory af, Role self)
	{
		return af.NonRoleArg(this.source, getVal());  // arg needs projection?
	}
}
