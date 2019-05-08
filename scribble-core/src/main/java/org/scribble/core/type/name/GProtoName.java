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

import org.scribble.core.type.kind.Global;


public class GProtoName extends ProtoName<Global>
{
	private static final long serialVersionUID = 1L;

	public GProtoName(ModuleName modname, ProtoName<Global> membname)
	{
		super(Global.KIND, modname, membname);
	}
	
	public GProtoName(String simpname)
	{
		super(Global.KIND, simpname);
	}

	@Override
	public GProtoName getSimpleName()
	{
		return new GProtoName(getLastElement());
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof GProtoName))
		{
			return false;
		}
		GProtoName n = (GProtoName) o;
		return n.canEquals(this) && super.equals(o);
	}
	
	public boolean canEquals(Object o)
	{
		return o instanceof GProtoName;
	}

	@Override
	public int hashCode()
	{
		int hash = 2777;
		hash = 31 * super.hashCode();
		return hash;
	}
}
