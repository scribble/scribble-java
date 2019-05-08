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
package org.scribble.core.model.global.actions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.scribble.core.model.MAction;
import org.scribble.core.type.kind.Global;
import org.scribble.core.type.name.MsgId;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.Payload;

// N.B. these are not exactly global type constructors -- they are "endpoint-oriented" like locals, but record both subj/obj roles (so more like locals with self)
public abstract class SAction extends MAction<Global>
{
	public final Role subj;

	public SAction(Role subj, Role obj, MsgId<?> mid, Payload pay)
	{
		super(obj, mid, pay);
		this.subj = subj; 
	}

	public boolean isSend()
	{
		return false;
	}

	public boolean isReceive()
	{
		return false;
	}

	public boolean isRequest()
	{
		return false;
	}

	public boolean isAccept()
	{
		return false;
	}

	public boolean isDisconnect()
	{
		return false;
	}

	public boolean isClientWrap()
	{
		return false;
	}

	public boolean isServerWrap()
	{
		return false;
	}
	
	public Set<Role> getRoles()
	{
		return new HashSet<>(Arrays.asList(this.subj, this.obj));
	}
	
	/*public boolean containsRole(Role role)
	{
		return this.subj.equals(role) || this.obj.equals(role);
	}*/
	
	@Override
	public String toString()
	{
		return this.subj + getCommSymbol() + this.obj + ":" + this.mid
				+ this.payload;
	}

	@Override
	public int hashCode()
	{
		int hash = 149;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.subj.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof SAction))
		{
			return false;
		}
		SAction them = (SAction) o;
		return super.equals(o)   // Checks canEquals
				&& this.subj.equals(them.subj);
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof SAction;
	}
}
















	
	/*public IOAction project(Role self)
	{
		if (this.subj.equals(self))
		{
			if (this.obj.equals(self))
			{
				throw new RuntimeException("TODO: " + this);
			}
			else
			{
				return new Send(this.obj, this.mid, this.payload);
			}
		}
		else
		{
			if (this.obj.equals(self))
			{
				return new Receive(this.subj, this.mid, this.payload);
				//return Receive.get(this.subj, this.mid, this.payload);
			}
			else
			{
				return null;  // FIXME?
			}
		}
	}*/