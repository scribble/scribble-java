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
import org.scribble.del.ScribDel;
import org.scribble.util.ScribException;
import org.scribble.visit.AstVisitor;

// TODO: rename better? DoArg, DoArgNode, ...
// CHECKME: make DoArg into an interface and have DoArgNode as direct children of Do?
// Cf. NameDeclNode/HeaderParameterDecl, i.e. wrappers for param names/arg values
// Simpler than NameDeclNode, doesn't constrain node-type correspondence for names
public abstract class DoArg<T extends DoArgNode> extends ScribNodeBase
{
	// ScribTreeAdaptor#create constructor
	public DoArg(Token t)
	{
		super(t);
		this.val = null;
	}

	// Tree#dupNode constructor
	public DoArg(DoArg<T> node)
	{
		super(node);
		this.val = null;
	}
	
	public abstract T getValChild();
	
	public abstract DoArg<T> dupNode();

	public DoArg<T> reconstruct(T arg)
	{
		DoArg<T> sig = dupNode();
		sig.addChild(arg);
		ScribDel del = del();
		sig.setDel(del);  // No copy
		return sig;
	}
	
	@Override
	public DoArg<T> visitChildren(AstVisitor nv) throws ScribException
	{
		ScribNode visited = visitChild(getValChild(), nv);  // Disambiguation will replace AmbiguousNameNodes
				// CHECKME: use visitChildWithClassEqualityCheck?
		if (!(visited instanceof DoArgNode))
		{
			throw new RuntimeException("Shouldn't get in here: " + visited);
		}
		@SuppressWarnings("unchecked")
		T arg = (T) visited;
		return reconstruct(arg);
	}

	public abstract DoArg<T> project(AstFactory af, Role self);
	
	@Override
	public String toString()
	{
		return getValChild().toString();
	}
	
	
	
	
	
	
	
	
	
	
	
	private final T val;

	protected DoArg(CommonTree source, T arg)
	{
		super(source);
		this.val = arg;
	}

}
