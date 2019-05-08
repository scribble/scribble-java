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
package org.scribble.core.type.name;

import org.scribble.core.type.kind.IdKind;

public class Id extends AbstractName<IdKind>
{
	private static final long serialVersionUID = 1L;
	
	public Id(String text)
	{
		super(IdKind.KIND, text);
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof Id))
		{
			return false;
		}
		Id n = (Id) o;
		return n.canEquals(this) && super.equals(o);
	}
	
	public boolean canEquals(Object o)
	{
		return o instanceof Id;
	}

	@Override
	public int hashCode()
	{
		int hash = 2753;
		hash = 31 * super.hashCode();
		return hash;
	}
}
