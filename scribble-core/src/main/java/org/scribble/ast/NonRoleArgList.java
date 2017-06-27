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

import java.util.List;
import java.util.stream.Collectors;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.Arg;
import org.scribble.sesstype.kind.NonRoleArgKind;
import org.scribble.sesstype.name.Role;
import org.scribble.util.ScribUtil;

// Cf. NonRoleParamDeclList
public class NonRoleArgList extends DoArgList<NonRoleArg>
{
	public NonRoleArgList(CommonTree source, List<NonRoleArg> args)
	{
		super(source, args);
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new NonRoleArgList(this.source, getDoArgs());
	}
	
	@Override
	public NonRoleArgList clone(AstFactory af)
	{
		List<NonRoleArg> args = ScribUtil.cloneList(af, getDoArgs());
		return af.NonRoleArgList(this.source, args);
	}

	@Override
	public DoArgList<NonRoleArg> reconstruct(List<NonRoleArg> instans)
	{
		ScribDel del = del();
		NonRoleArgList ail = new NonRoleArgList(this.source, instans);
		ail = (NonRoleArgList) ail.del(del);
		return ail;
	}

	@Override
	public NonRoleArgList project(AstFactory af, Role self)
	{
		List<NonRoleArg> instans =
				getDoArgs().stream().map(ai -> ai.project(af, self)).collect(Collectors.toList());	
		return af.NonRoleArgList(this.source, instans);
	}
	
	public boolean isEmpty()
	{
		return getDoArgs().isEmpty();
	}
	
	public List<NonRoleArgNode> getArgumentNodes()
	{
		return getDoArgs().stream().map((ai) -> ai.val).collect(Collectors.toList());
	}

	public List<Arg<? extends NonRoleArgKind>> getArguments()
	{
		return getDoArgs().stream().map((ai) -> ai.val.toArg()).collect(Collectors.toList());
	}

	@Override
	public String toString()
	{
		return (getDoArgs().isEmpty())
				? ""
				: "<" + super.toString() + ">";
	}
}
