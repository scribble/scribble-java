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

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.ScribNodeBase;
import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.name.Named;

// Kind parameter used for typing help, but NameNodes don't record kind as state (not part of the syntax) -- so kind doesn't affect e.g. equals (i.e. names nodes of different kinds are still only compared syntactically)
public abstract class NameNode<K extends Kind> extends ScribNodeBase implements Named<K>
{
	protected final String[] elems;

	public NameNode(CommonTree source, String... elems)
	{
		super(source);
		this.elems = elems;
	}
	
	@Override
	public abstract NameNode<K> clone();
	
	public String[] getElements()
	{
		return Arrays.copyOf(this.elems, this.elems.length);
	}

	public int getElementCount()
	{
		return this.elems.length;
	}
	
	public boolean isEmpty()
	{
		return this.elems.length == 0;
	}
	
	protected boolean isPrefixed()
	{
		return this.elems.length > 1;
	}
	
	protected String[] getPrefixElements()
	{
		return Arrays.copyOfRange(this.elems, 0, this.elems.length - 1);
	}
	
	protected String getLastElement()
	{
		return this.elems[this.elems.length - 1];
	}
	
	@Override
	public boolean equals(Object o)  // FIXME: should NameNodes ever be used in an equality checking context? (cf. other AST nodes) -- this work should be done using sesstype.Name instead?
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
		hash = 31 * hash + this.elems.hashCode();
		return hash;
	}

	@Override
	public String toString()
	{
		return toName().toString();
	}
}
