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

import org.scribble.sesstype.kind.Local;


public class LProtocolName extends ProtocolName<Local> implements PayloadElemType<Local> //-- not needed, deleg elems currently have to be (Global@Role)
{
	private static final long serialVersionUID = 1L;

	public LProtocolName(ModuleName modname, ProtocolName<Local> membname)
	{
		super(Local.KIND, modname, membname);
	}
	
	public LProtocolName(String simpname)
	{
		super(Local.KIND, simpname);
	}
	
	/*@Override
	public boolean isLDelegationType()
	{
		return true;
	}*/

	@Override
	public LProtocolName getSimpleName()
	{
		return new LProtocolName(getLastElement());
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof LProtocolName))
		{
			return false;
		}
		LProtocolName n = (LProtocolName) o;
		return n.canEqual(this) && super.equals(o);
	}
	
	public boolean canEqual(Object o)
	{
		return o instanceof LProtocolName;
	}

	@Override
	public int hashCode()
	{
		int hash = 2789;
		hash = 31 * super.hashCode();
		return hash;
	}
}
