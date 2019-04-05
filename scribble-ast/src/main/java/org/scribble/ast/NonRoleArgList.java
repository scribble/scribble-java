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

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.scribble.core.type.kind.NonRoleArgKind;
import org.scribble.core.type.kind.NonRoleParamKind;
import org.scribble.core.type.name.DataType;
import org.scribble.core.type.name.MessageSigName;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.Arg;
import org.scribble.core.type.session.MessageSig;

// Cf. NonRoleParamDeclList
public class NonRoleArgList extends DoArgList<NonRoleArg>
{
	// ScribTreeAdaptor#create constructor
	public NonRoleArgList(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public NonRoleArgList(NonRoleArgList node)
	{
		super(node);
	}

	@Override
	public List<NonRoleArg> getArgChildren()
	{
		return getRawArgChildren().stream().map(x -> (NonRoleArg) x)
				.collect(Collectors.toList());
	}
	
	@Override
	public NonRoleArgList dupNode()
	{
		return new NonRoleArgList(this);
	}

	@Override
	public NonRoleArgList project(AstFactory af, Role self)
	{
		List<NonRoleArg> args = getArgChildren().stream()
				.map(ai -> ai.project(af, self)).collect(Collectors.toList());
		return af.NonRoleArgList(this.source, args);
	}
	
	public boolean isEmpty()
	{
		return getArgChildren().isEmpty();
	}
	
	public List<NonRoleArgNode> getArgumentNodes()
	{
		return getArgChildren().stream().map(ai -> ai.getValChild())
				.collect(Collectors.toList());
	}

	// Can return a mix of arg kinds
	public List<Arg<? extends NonRoleArgKind>> getArguments()
	{
		return getArgChildren().stream()
				.map(ai -> (Arg<?>) ai.getValChild().toArg())
				.collect(Collectors.toList());
	}
	
	// Cast all, assuming no Ambig
	public List<Arg<? extends NonRoleParamKind>> getParamKindArgs()
	{
		List<Arg<? extends NonRoleParamKind>> cast = new LinkedList<>();
		for (Arg<? extends NonRoleArgKind> a : getArguments())
		{
			if (a instanceof MessageSig)
			{
				cast.add((MessageSig) a);
			}
			else if (a instanceof DataType)
			{
				cast.add((DataType) a);
			}
			else if (a instanceof MessageSigName)
			{
				cast.add((MessageSigName) a);
			}
			else
			{
				throw new RuntimeException("TODO: " + a);
			}
		}
		return cast;
	}

	@Override
	public String toString()
	{
		return getArgChildren().isEmpty()
				? ""
				: "<" + super.toString() + ">";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public NonRoleArgList(CommonTree source, List<NonRoleArg> args)
	{
		super(source, args);
	}

	/*@Override
	protected ScribNodeBase copy()
	{
		return new NonRoleArgList(this.source, getArgChildren());
	}
	
	@Override
	public NonRoleArgList clone(AstFactory af)
	{
		List<NonRoleArg> args = ScribUtil.cloneList(af, getArgChildren());
		return af.NonRoleArgList(this.source, args);
	}

	@Override
	public DoArgList<NonRoleArg> reconstruct(List<NonRoleArg> instans)
	{
		ScribDel del = del();
		NonRoleArgList ail = new NonRoleArgList(this.source, instans);
		ail = (NonRoleArgList) ail.del(del);
		return ail;
	}*/
}
