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
package org.scribble.core.type.kind;

public abstract class AbstractKind implements Kind
{
	protected String kind; // Not final for easier serialiazation (OpKind)

  // For serialization
	protected AbstractKind()
	{
		
	}

	public AbstractKind(String kind)
	{
		this.kind = kind;
	}

	@Override
	public int hashCode()
	{
		int hash = 7901;
		hash = 31 * hash + this.kind.hashCode();
		return hash;
	}
	
	@Override
	public abstract boolean equals(Object o);

	public abstract boolean canEqual(Object o);  // Not really needed due to singleton pattern

	@Override
	public String toString()
	{
		String s = this.getClass().toString();
		return s.substring("class org.sribble.type.kind.".length() + 1, s.length());  // FIXME: factor out by reflection (in initialiser)
	}
}
