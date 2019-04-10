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
import org.scribble.del.ScribDel;
import org.scribble.util.ScribException;
import org.scribble.visit.AstVisitor;


// RoleKind or (NonRole)ParamKind
public abstract class HeaderParamDeclList<K extends ParamKind> extends ScribNodeBase 
{
	// ScribTreeAdaptor#create constructor
	public HeaderParamDeclList(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public HeaderParamDeclList(HeaderParamDeclList<K> node)
	{
		super(node);
	}
	
	public abstract List<? extends HeaderParamDecl<K>> getParamDeclChildren();
	
	public final int length()
	{
		return getParamDeclChildren().size();
	}

	public final boolean isEmpty()
	{
		return length() == 0;
	}
	
	@Override
	public abstract HeaderParamDeclList<K> dupNode();
	
	public HeaderParamDeclList<K> reconstruct(
			List<? extends HeaderParamDecl<K>> decls)
	{
		HeaderParamDeclList<K> sig = dupNode();
		sig.addChildren(decls);
		ScribDel del = del();
		sig.setDel(del);  // No copy
		return sig;
	}
	
	@Override
	public HeaderParamDeclList<? extends K> visitChildren(AstVisitor v)
			throws ScribException
	{
		List<? extends HeaderParamDecl<K>> ps = 
				visitChildListWithClassEqualityCheck(this, getParamDeclChildren(), v);
		return reconstruct(ps);
	}

	// Without enclosing braces -- added by subclasses
	@Override
	public String toString()
	{
		return getParamDeclChildren().stream().map(x -> x.toString())
				.collect(Collectors.joining(", "));
	}
}
