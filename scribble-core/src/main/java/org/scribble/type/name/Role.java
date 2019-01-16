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

import java.util.Comparator;

import org.scribble.type.kind.RoleKind;


public class Role extends AbstractName<RoleKind>
{

	private static final long serialVersionUID = 1L;

	public static final Role EMPTY_ROLE = new Role();

	public static final Comparator<Role> COMPARATOR = new Comparator<Role>()
			{
				@Override
				public int compare(Role o1, Role o2)
				{
					return o1.toString().compareTo(o2.toString());
				}
			};

	protected Role()
	{
		super(RoleKind.KIND);
	}

	public Role(String text)
	{
		super(RoleKind.KIND, text);
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof Role))
		{
			return false;
		}
		return super.equals(o);  // Does canEqual
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof Role;
	}

	@Override
	public int hashCode()
	{
		int hash = 2741;
		hash = 31 * super.hashCode();
		return hash;
	}
}
