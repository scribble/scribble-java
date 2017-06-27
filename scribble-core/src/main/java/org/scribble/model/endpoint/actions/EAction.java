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
package org.scribble.model.endpoint.actions;

import org.scribble.model.MAction;
import org.scribble.model.endpoint.EModelFactory;
import org.scribble.model.global.SModelFactory;
import org.scribble.model.global.actions.SAction;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;

public abstract class EAction extends MAction<Local>
{
	public final Role peer;
	/*public final MessageId<?> mid;
	public final Payload payload;  // Empty for MessageSigNames*/

	protected EModelFactory ef;

	protected EAction(EModelFactory ef, Role peer, MessageId<?> mid, Payload payload)
	{
		/*this.mid = mid;
		this.payload = payload;*/
		super(peer, mid, payload);
		this.peer = peer;

		this.ef = ef;
	}
	
	public abstract EAction toDual(Role self);

	//public abstract GModelAction toGlobal(Role self);
	public abstract SAction toGlobal(SModelFactory sf, Role self);

	public boolean isSend()
	{
		return false;
	}
	
	public boolean isReceive()
	{
		return false;
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

	public boolean isWrapClient()
	{
		return false;
	}

	public boolean isWrapServer()
	{
		return false;
	}
	
	/*@Override
	public String toString()
	{
		return this.peer + getCommSymbol() + this.mid + this.payload;
	}
	
	protected abstract String getCommSymbol();*/
	
	/*@Override
	public int hashCode()
	{
		int hash = 919;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.peer.hashCode();  // No: peer is this.obj
		return hash;
	}*/

	/*@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof IOAction))
		{
			return false;
		}
		IOAction a = (IOAction) o;
		return a.canEqual(this) && 
				this.peer.equals(a.peer) && this.mid.equals(a.mid) && this.payload.equals(a.payload);
	}
	
	public abstract boolean canEqual(Object o);*/
}
