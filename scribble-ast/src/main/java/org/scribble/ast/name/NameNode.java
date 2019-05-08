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
package org.scribble.ast.name;

import java.util.List;
import java.util.stream.Collectors;

import org.antlr.runtime.Token;
import org.scribble.ast.ScribNodeBase;
import org.scribble.ast.name.simple.IdNode;
import org.scribble.core.type.kind.Kind;
import org.scribble.core.type.name.Named;

// Children are IdNode elements (each IdNode records a String text element)
// Kind parameter used for typing help, but NameNodes don't record kind as state (not part of the syntax) -- so kind doesn't affect e.g. equals (i.e. name nodes of different kinds are still only compared syntactically)
public abstract class NameNode<K extends Kind> extends ScribNodeBase
		implements Named<K>
{
	// ScribTreeAdaptor#create constructor
	public NameNode(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	protected NameNode(NameNode<K> node)
	{
		super(node);
	}
	
	@Override
	public List<IdNode> getChildren()
	{
		return super.getChildren().stream().map(x -> (IdNode) x)
				.collect(Collectors.toList());
	}
	
	public abstract NameNode<K> dupNode();
	
	// Factored abstractly from getChildren, for SimpleNameNode to override (SimpleNameNode has no children)
	public List<String> getElements()
	{
		return getChildren().stream()
				.map(x -> x.getText()).collect(Collectors.toList());  
	}
	
	protected final List<String> getPrefixElements()
	{
		List<String> elems = getElements();
		return elems.subList(0, elems.size()-1);
	}
	
	protected final String getLastElement()
	{
		List<String> names = getElements();
		return names.get(names.size() - 1);
	}

	public int getElementCount()
	{
		return getChildCount();
	}
	
	public boolean isEmpty()
	{
		return getElementCount() == 0;
	}
	
	protected boolean isPrefixed()
	{
		return getElementCount() > 1;
	}

	@Override
	public String toString()
	{
		return toName().toString();
	}
	
  // CHECKME: is equals/hashCode actually needed for these ScribNodes? (cf. Name)
	// CHECKME: should NameNodes ever be used in an equality checking context? (cf. other AST nodes) -- this work should be done using sesstype.Name instead?
	@Override
	public boolean equals(Object o)  
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof NameNode<?>))
		{
			return false;
		}
		NameNode<?> them = (NameNode<?>) o;
		return them.canEquals(this)
				&& getElements().equals(them.getElements());
	}
	
	public abstract boolean canEquals(Object o);
	
	@Override
	public int hashCode()
	{
		int hash = 317;
		hash = 31 * hash + getElements().hashCode();  
				// Hash the String values, not the actual Tree nodes
		return hash;
	}
}
