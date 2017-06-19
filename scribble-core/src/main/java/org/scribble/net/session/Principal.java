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
package org.scribble.net.session;

import java.io.Serializable;

import org.scribble.sesstype.name.Role;

@Deprecated
public class Principal implements Serializable
{
	private static final long serialVersionUID = 1L;

	public final Role role;
	/*public final String host;
	public final int port;  // The "session port"*/

	public Principal(Role role)//, String host, int port)
	{
		this.role = role;
		//this.host = host;
		//this.port = port;
	}

	@Override
	public int hashCode()
	{
		int hash = 859;
		hash = 31 * hash + this.role.hashCode();
		//hash = 31 * hash + this.host.hashCode();
		//hash = 31 * hash + this.port;
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof Principal))
		{
			return false;
		}
		Principal p = (Principal) o;
		return this.role.equals(p.role);//this.host.equals(p.host) && this.port == p.port;
	}
	
	@Override
	public String toString()
	{
		//return this.role + "@" + this.host + ":" + this.port;
		return this.role.toString();
	}
}
