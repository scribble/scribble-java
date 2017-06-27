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
package org.scribble.model.global.actions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.scribble.model.MAction;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;

public abstract class SAction extends MAction<Global>
{
	public final Role subj;

	public SAction(Role subj, Role obj, MessageId<?> mid, Payload payload)
	{
		super(obj, mid, payload);
		this.subj = subj; 
	}

	public boolean isConnect()
	{
		return false;
	}

	public boolean isDisconnect()
	{
		return false;
	}

	public boolean isAccept()
	{
		return false;
	}

	public boolean isSend()
	{
		return false;
	}

	public boolean isReceive()
	{
		return false;
	}
	
	public Set<Role> getRoles()
	{
		return new HashSet<>(Arrays.asList(this.subj, this.obj));
	}
	
	public boolean containsRole(Role role)
	{
		return this.subj.equals(role) || this.obj.equals(role);
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
	
	@Override
	public String toString()
	{
		return this.subj + getCommSymbol() + this.obj + ":" + this.mid + this.payload;
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
	public boolean equals(Object o)  // FIXME: kind
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof SAction))
		{
			return false;
		}
		SAction a = (SAction) o;
		return super.equals(o) && this.subj.equals(a.subj);
	}

	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof SAction;
	}
}
