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

import java.io.IOException;
import java.io.Serializable;

import org.scribble.sesstype.kind.Local;

// FIXME: factor out of name package?  (and then PayloadType also needs to be moved out of name?)
public class GDelegationType implements PayloadType<Local>, Serializable
{
	private static final long serialVersionUID = 1L;
	
	private GProtocolName proto;  // Cannot be final, for Serializable
	private Role role;

	public GDelegationType(GProtocolName proto, Role role)
	{
		this.proto = proto;
		this.role = role;
	}

	@Override
	public boolean isGDelegationType()
	{
		return true;
	}
	
	public GProtocolName getGlobalProtocol()
	{
		return this.proto;
	}

	public Role getRole()
	{
		return this.role;
	}

	@Override
	public Local getKind()
	{
		return Local.KIND;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof GDelegationType))
		{
			return false;
		}
		GDelegationType them = (GDelegationType) o;
		return them.canEqual(this) && this.proto.equals(them.proto) && this.role.equals(them.role);
	}
	
	public boolean canEqual(Object o)
	{
		return o instanceof GDelegationType;
	}

	@Override
	public int hashCode()
	{
		int hash = 1381;
		hash = 31 * this.proto.hashCode();
		hash = 31 * this.role.hashCode();
		return hash;
	}

	private void writeObject(java.io.ObjectOutputStream out) throws IOException
	{
		out.writeObject(this.proto);
		out.writeObject(this.role);
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		this.proto = (GProtocolName) in.readObject();
		this.role = (Role) in.readObject();
	}
}
