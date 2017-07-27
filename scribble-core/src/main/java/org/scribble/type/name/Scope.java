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
package org.scribble.type.name;

import org.scribble.type.kind.ScopeKind;

// Should be a compound name?
public class Scope extends AbstractName<ScopeKind>
{
	private static final long serialVersionUID = 1L;

	public static final String IMPLICIT_SCOPE_PREFIX = "__scope";
	public static final Scope EMPTY_SCOPE = new Scope();
	public static final Scope ROOT_SCOPE = new Scope("__root");

	protected Scope(String... elems)
	{
		super(ScopeKind.KIND, elems);
	}
	
	public Scope(String name)
	{
		this(new String[] { name });
	}

	public Scope(Scope prefix, Name<ScopeKind> name)
	{
		this(compileScope(prefix, name));
	}
	
	// Also done by QualifiedName
	public Scope getPrefix()
	{
		return new Scope(getPrefixElements());
	}

	public Scope getSimpleName()
	{
		return new Scope(getLastElement());
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof Scope))
		{
			return false;
		}
		Scope n = (Scope) o;
		return n.canEqual(this) && super.equals(o);
	}
	
	public boolean canEqual(Object o)
	{
		return o instanceof Scope;
	}

	@Override
	public int hashCode()
	{
		int hash = 2833;
		hash = 31 * super.hashCode();
		return hash;
	}
	
	private static String[] compileScope(Scope prefix, Name<ScopeKind> name)
	{
		String[] tmp = prefix.getElements();
		String[] elems = new String[tmp.length + 1];
		System.arraycopy(tmp, 0, elems, 0, tmp.length);
		elems[elems.length - 1] = name.toString();
		return elems;
	}
}
