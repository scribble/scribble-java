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
import org.scribble.core.job.ScribbleException;
import org.scribble.core.type.name.Role;
import org.scribble.del.ScribDel;
import org.scribble.visit.AstVisitor;

// Cf. HeaderParameterDeclList -- but not kinded, because cannot determine Arg kind directly from node syntax itself (kinding for ModelNodes is to supplement syntactic information, not "typing" work)
// DoArgList (NonRoleArgList) can be of mixed kinds, so DoArg (NonRoleArg) used as "wildcard" wrapper
// "? extends InstantiationNode" not enforced here (e.g. can put "? extends ModelNode"), because ultimately any instantiation of this class needs an actual instance of "Instantiation" which has to have a parameter that extends "InstantiationNode"
public abstract class DoArgList<T extends DoArg<?>> extends ScribNodeBase  
{
	// ScribTreeAdaptor#create constructor
	public DoArgList(Token t)
	{
		super(t);
		this.args = null;
	}

	// Tree#dupNode constructor
	public DoArgList(DoArgList<T> node)
	{
		super(node);
		this.args = null;
	}
	
	protected List<ScribNode> getRawArgChildren()
	{
		return getChildren();
	}
	
	public abstract List<T> getArgChildren();
	
	@Override
	public abstract DoArgList<T> dupNode();

	public DoArgList<T> reconstruct(List<T> args)
	{
		DoArgList<T> argList = dupNode();
		argList.addChildren(args);
		ScribDel del = del();
		argList.setDel(del);  // No copy
		return argList;
	}
	
	public abstract DoArgList<T> project(AstFactory af, Role self);
	
	@Override
	public DoArgList<T> visitChildren(AstVisitor nv) throws ScribbleException
	{
		List<T> nds = 
				visitChildListWithClassEqualityCheck(this, getArgChildren(), nv);
		return reconstruct(nds);
	}

	public int length()
	{
		return getChildCount();
	}
	
	// Like HeaderParamDeclList, without enclosing braces -- added by subclasses
	@Override
	public String toString()
	{
		return getArgChildren().stream().map(a -> a.toString())
				.collect(Collectors.joining(", "));
	}
	
	
	
	
	
	
	
	
	

	private final List<T> args;

	public DoArgList(CommonTree source, List<T> is)
	{
		super(source);
		this.args = new LinkedList<>(is);
	}
	
}
