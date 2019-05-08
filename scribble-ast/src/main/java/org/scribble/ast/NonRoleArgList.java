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
import org.scribble.core.type.kind.NonRoleArgKind;
import org.scribble.core.type.kind.NonRoleParamKind;
import org.scribble.core.type.name.DataName;
import org.scribble.core.type.name.SigName;
import org.scribble.core.type.session.Arg;
import org.scribble.core.type.session.SigLit;
import org.scribble.del.DelFactory;

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
	
	public List<NonRoleArgNode> getArgNodes()
	{
		return getArgChildren().stream().map(x -> x.getArgNodeChild())
				.collect(Collectors.toList());
	}
	
	public boolean isEmpty()
	{
		return getArgChildren().isEmpty();
	}

	// Can return a mix of arg kinds
	public List<Arg<? extends NonRoleArgKind>> getArguments()
	{
		return getArgChildren().stream()
				.map(x -> (Arg<?>) x.getArgNodeChild().toArg())
				.collect(Collectors.toList());
	}
	
	// Cast all, assuming no Ambig
	public List<Arg<? extends NonRoleParamKind>> getParamKindArgs()
	{
		List<Arg<? extends NonRoleParamKind>> cast = new LinkedList<>();
		for (Arg<? extends NonRoleArgKind> a : getArguments())
		{
			if (a instanceof SigLit)
			{
				cast.add((SigLit) a);
			}
			else if (a instanceof DataName)
			{
				cast.add((DataName) a);
			}
			else if (a instanceof SigName)
			{
				cast.add((SigName) a);
			}
			else
			{
				throw new RuntimeException("TODO: " + a);
			}
		}
		return cast;
	}
	
	@Override
	public NonRoleArgList dupNode()
	{
		return new NonRoleArgList(this);
	}

	@Override
	public void decorateDel(DelFactory df)
	{
		df.NonRoleArgList(this);
	}

	@Override
	public String toString()
	{
		return getArgChildren().isEmpty()
				? ""
				: "<" + super.toString() + ">";
	}
}
