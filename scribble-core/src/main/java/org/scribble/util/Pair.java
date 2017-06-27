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
package org.scribble.util;

public class Pair<T1, T2>
{
	public final T1 left;
	public final T2 right;

	public Pair(T1 t1, T2 t2)
	{
		this.left = t1;
		this.right = t2;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 11;
		hash = 31 * hash + this.left.hashCode();
		hash = 31 * hash + this.right.hashCode();
		return hash;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof Pair))
		{
			return false;
		}
		@SuppressWarnings("rawtypes")
		Pair p = (Pair) o;  // Could store T1.class and T2.class as fields, but probably better to do "structurally" as here
		return this.left.equals(p.left) && this.right.equals(p.right);
	}
}
