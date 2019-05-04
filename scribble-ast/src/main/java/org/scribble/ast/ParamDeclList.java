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
import org.scribble.core.type.kind.ParamKind;
import org.scribble.util.ScribException;
import org.scribble.visit.AstVisitor;


// RoleKind or (NonRole)ParamKind
public abstract class ParamDeclList<K extends ParamKind> extends ScribNodeBase 
{
	// ScribTreeAdaptor#create constructor
	public ParamDeclList(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public ParamDeclList(ParamDeclList<K> node)
	{
		super(node);
	}
	
	public abstract List<? extends ParamDecl<K>> getDeclChildren();

	// "add", not "set"
	public void addScribChildren(List<? extends ParamDecl<? extends K>> ds)
	{
		// Cf. above getters and Scribble.g children order
		super.addChildren(ds);
	}
	
	@Override
	public abstract ParamDeclList<K> dupNode();
	
	public ParamDeclList<K> reconstruct(
			List<? extends ParamDecl<K>> ds)
	{
		ParamDeclList<K> dup = dupNode();
		dup.addScribChildren(ds);
		dup.setDel(del());  // No copy
		return dup;
	}
	
	@Override
	public ParamDeclList<? extends K> visitChildren(AstVisitor v)
			throws ScribException
	{
		List<? extends ParamDecl<K>> ps = 
				visitChildListWithClassEqualityCheck(this, getDeclChildren(), v);
		return reconstruct(ps);
	}
		
	public final int length()
	{
		return getDeclChildren().size();
	}

	public final boolean isEmpty()
	{
		return length() == 0;
	}

	// Without enclosing braces -- added by subclasses
	@Override
	public String toString()
	{
		return getDeclChildren().stream().map(x -> x.toString())
				.collect(Collectors.joining(", "));
	}
}
