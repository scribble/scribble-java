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
import org.scribble.core.type.name.Role;
import org.scribble.del.DelFactory;

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

	// The role arguments
	public List<Role> getRoles()
	{
		return getArgChildren().stream().map(ri -> ri.getArgNodeChild().toName())
				.collect(Collectors.toList());
	}
	
	@Override
	public RoleArgList dupNode()
	{
		return new RoleArgList(this);
	}
	
	@Override
	public void decorateDel(DelFactory df)
	{
		df.RoleArgList(this);
	}

	@Override
	public String toString()
	{
		return "(" + super.toString() + ")";
	}
}
