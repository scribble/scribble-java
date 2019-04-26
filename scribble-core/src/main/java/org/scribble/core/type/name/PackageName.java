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

import org.scribble.core.type.kind.PackageKind;

public class PackageName extends QualName<PackageKind>
{
	private static final long serialVersionUID = 1L;
	
	public static final PackageName EMPTY_PACKAGENAME = new PackageName();

	public PackageName(String... elems)
	{
		super(PackageKind.KIND, elems);
	}

	@Override
	public PackageName getPrefix()
	{
		return new PackageName(getPrefixElements());
	}

	@Override
	public PackageName getSimpleName()
	{
		return new PackageName(getLastElement());
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof PackageName))
		{
			return false;
		}
		PackageName n = (PackageName) o;
		return n.canEquals(this) && super.equals(o);
	}
	
	public boolean canEquals(Object o)
	{
		return o instanceof PackageName;
	}

	@Override
	public int hashCode()
	{
		int hash = 2803;
		hash = 31 * super.hashCode();
		return hash;
	}
}
