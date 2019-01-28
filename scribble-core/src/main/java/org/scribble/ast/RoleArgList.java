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

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.scribble.type.name.Role;

public class RoleArgList extends DoArgList<RoleArg>
{
	// ScribTreeAdaptor#create constructor
	public RoleArgList(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public RoleArgList(RoleArgList node)
	{
		super(node);
	}

	@Override
	public List<RoleArg> getArgChildren()
	{
		return getRawArgChildren().stream().map(x -> (RoleArg) x)
				.collect(Collectors.toList());
	}
	
	@Override
	public RoleArgList dupNode()
	{
		return new RoleArgList(this);
	}

	// Move to delegate?
	@Override
	public RoleArgList project(AstFactory af, Role self)
	{
		List<RoleArg> args =
				getArgChildren().stream().map(ri -> ri.project(af, self))
						.collect(Collectors.toList());
		return af.RoleArgList(this.source, args);
	}

	// The role arguments
	public List<Role> getRoles()
	{
		return getArgChildren().stream().map(ri -> ri.getValChild().toName())
				.collect(Collectors.toList());
	}

	@Override
	public String toString()
	{
		return "(" + super.toString() + ")";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public RoleArgList(CommonTree source, List<RoleArg> roles)
	{
		super(source, roles);
	}

	/*@Override
	protected RoleArgList copy()
	{
		return new RoleArgList(this.source, getArgChildren());
	}
	
	@Override
	public RoleArgList clone(AstFactory af)
	{
		List<RoleArg> roles = ScribUtil.cloneList(af, getArgChildren());
		return af.RoleArgList(this.source, roles);
	}

	@Override
	public RoleArgList reconstruct(List<RoleArg> roles)
	{
		ScribDel del = del();
		RoleArgList rl = new RoleArgList(this.source, roles);
		rl = (RoleArgList) rl.del(del);
		return rl;
	}*/
}
