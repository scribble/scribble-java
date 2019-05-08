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

import org.scribble.core.type.kind.Local;


public class LProtoName extends ProtoName<Local> 
		implements PayElemType<Local> // @Deprecated -- not used, deleg elems currently have to be (Global@Role)
{
	private static final long serialVersionUID = 1L;

	public LProtoName(ModuleName modname, ProtoName<Local> membname)
	{
		super(Local.KIND, modname, membname);
	}
	
	public LProtoName(String simpname)
	{
		super(Local.KIND, simpname);
	}
	
	/*@Override
	public boolean isLDelegationType()
	{
		return true;
	}*/

	@Override
	public LProtoName getSimpleName()
	{
		return new LProtoName(getLastElement());
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof LProtoName))
		{
			return false;
		}
		LProtoName n = (LProtoName) o;
		return n.canEquals(this) && super.equals(o);
	}
	
	public boolean canEquals(Object o)
	{
		return o instanceof LProtoName;
	}

	@Override
	public int hashCode()
	{
		int hash = 2789;
		hash = 31 * super.hashCode();
		return hash;
	}
}
