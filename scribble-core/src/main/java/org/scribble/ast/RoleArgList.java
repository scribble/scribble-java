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
import org.scribble.sesstype.name.Role;
import org.scribble.util.ScribUtil;

public class RoleArgList extends DoArgList<RoleArg>
{
	public RoleArgList(CommonTree source, List<RoleArg> roles)
	{
		super(source, roles);
	}

	@Override
	protected RoleArgList copy()
	{
		return new RoleArgList(this.source, getDoArgs());
	}
	
	@Override
	public RoleArgList clone(AstFactory af)
	{
		List<RoleArg> roles = ScribUtil.cloneList(af, getDoArgs());
		return af.RoleArgList(this.source, roles);
	}

	@Override
	public RoleArgList reconstruct(List<RoleArg> roles)
	{
		ScribDel del = del();
		RoleArgList rl = new RoleArgList(this.source, roles);
		rl = (RoleArgList) rl.del(del);
		return rl;
	}

	// Move to delegate?
	@Override
	public RoleArgList project(AstFactory af, Role self)
	{
		List<RoleArg> instans =
				getDoArgs().stream().map(ri -> ri.project(af, self)).collect(Collectors.toList());	
		return af.RoleArgList(this.source, instans);
	}

	// The role arguments
	public List<Role> getRoles()
	{
		return getDoArgs().stream().map((ri) -> ri.val.toName()).collect(Collectors.toList());
	}

	@Override
	public String toString()
	{
		return "(" + super.toString() + ")";
	}
}
