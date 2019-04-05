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

public class PackageKind extends AbstractKind
{
	public static final PackageKind KIND = new PackageKind();
	
	protected PackageKind()
	{
		super("Package");
	}

	@Override
	public boolean equals(Object o)
	{
		if (o == this)
		{
			return true;
		}
		if (!(o instanceof PackageKind))
		{
			return false;
		}
		return ((PackageKind) o).canEqual(this);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof PackageKind;
	}
}
