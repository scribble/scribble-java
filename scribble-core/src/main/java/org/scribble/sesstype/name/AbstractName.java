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
package org.scribble.sesstype.name;

import java.io.IOException;
import java.util.Arrays;

import org.scribble.sesstype.kind.Kind;

public abstract class AbstractName<K extends Kind> implements Name<K>
{
	private static final long serialVersionUID = 1L;
	
	private K kind;  // non-final for serialization

	private String[] elems;

	protected AbstractName(K kind, String... elems)
	{
		this.kind = kind;
		this.elems = elems;
	}
	
	@Override
	public K getKind()
	{
		return this.kind;
	}

	@Override
	public int getElementCount()
	{
		return this.elems.length;
	}

	@Override
	public boolean isEmpty()
	{
		return this.elems.length == 0;
	}

	@Override
	public boolean isPrefixed()
	{
		return this.elems.length > 1;
	}

	@Override
	public String[] getElements()
	{
		return Arrays.copyOf(this.elems, this.elems.length);
	}
	@Override
	public String[] getPrefixElements()
	{
		return Arrays.copyOfRange(this.elems, 0, this.elems.length - 1);
	}
	
	// Not SimpleName so that e.g. ModuleName can return a simple ModuleName
	@Override
	public String getLastElement()
	{
		return this.elems[this.elems.length - 1];
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof AbstractName))
		{
			return false;
		}
		AbstractName<?> n = (AbstractName<?>) o;
		return n.canEqual(this) && this.kind.equals(n.kind) && Arrays.equals(this.elems, (n.elems));
	}
	
	public abstract boolean canEqual(Object o);

	@Override
	public int hashCode()
	{
		int hash = 2749;
		hash = 31 * hash + this.kind.hashCode();
		hash = 31 * hash + Arrays.hashCode(this.elems);
		return hash;
	}
	
	@Override
	public String toString()
	{
		if (isEmpty())
		{
			return "";
		}
		String name = this.elems[0];
		for (int i = 1; i < this.elems.length; i++)
		{
			name += "." + this.elems[i];
		}
		return name;
	}

	private void writeObject(java.io.ObjectOutputStream out) throws IOException
	{
		out.writeObject(this.kind);
		out.writeObject(this.elems);
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		@SuppressWarnings("unchecked")
		K k = (K) in.readObject();
		this.kind = k;
		//this.kind = readKind();  // TODO: protected abstract -- will this work?
		this.elems = (String[]) in.readObject();
	}
}
