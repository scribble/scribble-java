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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.ScribNodeBase;
import org.scribble.type.kind.Kind;
import org.scribble.type.name.Named;

// Kind parameter used for typing help, but NameNodes don't record kind as state (not part of the syntax) -- so kind doesn't affect e.g. equals (i.e. name nodes of different kinds are still only compared syntactically)
public abstract class NameNode<K extends Kind> extends ScribNodeBase
		implements Named<K>
{
	// ScribTreeAdaptor#create constructor
	public NameNode(Token t)
	{
		super(t);
		this.elems = null;
	}

	// Tree#dupNode constructor
	protected NameNode(NameNode<K> node)//, String...elems)
	{
		super(node);
		this.elems = null;//elems;
	}
	
	public abstract NameNode<K> dupNode();
	
	public String[] getElements()
	{
		//return Arrays.copyOf(this.elems, this.elems.length);
		return getSimpleNameList().toArray(new String[0]);
	}
	
	protected List<String> getSimpleNameList()
	{
		return ((List<?>) getChildren()).stream()
				.map(x -> ((CommonTree) x).getText()).collect(Collectors.toList());  
				// CHECKME: currently AmbigNameNode(?)
				// CHECKME: factor out getText?
	}

	public int getElementCount()
	{
		//return this.elems.length;
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
	
	protected String[] getPrefixElements()
	{
		//return Arrays.copyOfRange(this.elems, 0, this.elems.length - 1);
		if (getElementCount() <= 1)
		{
			return new String[0];
		}
		List<String> names = getSimpleNameList();
		return names.subList(0, names.size()-1).toArray(new String[0]);
	}
	
	protected String getLastElement()
	{
		//return getElements()[getElementCount() - 1];
		List<String> names = getSimpleNameList();
		return names.get(names.size() - 1);
	}
	
	@Override
	public boolean equals(Object o)  
			// CHECKME: should NameNodes ever be used in an equality checking context? (cf. other AST nodes) -- this work should be done using sesstype.Name instead?
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof NameNode<?>))
		{
			return false;
		}
		NameNode<?> nn = (NameNode<?>) o;
		return nn.canEqual(this) && Arrays.equals(this.elems, nn.elems);
	}
	
	public abstract boolean canEqual(Object o);
	
	@Override
	public int hashCode()
	{
		int hash = 317;
		hash = 31 * hash + Arrays.hashCode(getElements());  
				// Hash the String values, not the actual Tree nodes
		return hash;
	}

	@Override
	public String toString()
	{
		return toName().toString();
	}
	
	
	
	
	
	

	
	
	private final String[] elems;
	
	public NameNode(CommonTree source, String... elems)
	{
		super(source);
		if (source == null)
		{
			throw new RuntimeException("CHECKME");
		}
		this.elems = elems;
	}
	
	/*@Override
	public abstract NameNode<K> clone(AstFactory af);*/
}
