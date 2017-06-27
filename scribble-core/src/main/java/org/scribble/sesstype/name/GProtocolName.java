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

import org.scribble.sesstype.kind.Global;


public class GProtocolName extends ProtocolName<Global>
{
	private static final long serialVersionUID = 1L;

	public GProtocolName(ModuleName modname, ProtocolName<Global> membname)
	{
		super(Global.KIND, modname, membname);
	}
	
	public GProtocolName(String simpname)
	{
		super(Global.KIND, simpname);
	}

	@Override
	public GProtocolName getSimpleName()
	{
		return new GProtocolName(getLastElement());
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof GProtocolName))
		{
			return false;
		}
		GProtocolName n = (GProtocolName) o;
		return n.canEqual(this) && super.equals(o);
	}
	
	public boolean canEqual(Object o)
	{
		return o instanceof GProtocolName;
	}

	@Override
	public int hashCode()
	{
		int hash = 2777;
		hash = 31 * super.hashCode();
		return hash;
	}
}
