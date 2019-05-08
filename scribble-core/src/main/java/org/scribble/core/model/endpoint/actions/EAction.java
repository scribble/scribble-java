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
package org.scribble.core.model.endpoint.actions;

import org.scribble.core.model.MAction;
import org.scribble.core.model.ModelFactory;
import org.scribble.core.model.global.actions.SAction;
import org.scribble.core.type.kind.Local;
import org.scribble.core.type.name.MsgId;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.Payload;

public abstract class EAction extends MAction<Local>
{
	public final Role peer;

	protected final ModelFactory mf;  // Internalising better ensures all constructions points (in a Scrib extension) consistently use the same ef/sf

	protected EAction(ModelFactory mf, Role peer, MsgId<?> mid, Payload pay)
	{
		super(peer, mid, pay);
		this.peer = peer;
		this.mf = mf;
	}
	
	// "self" means self of "this" (self of dual is this.peer) 
	public abstract EAction toDual(Role self);
	public abstract SAction toGlobal(Role self);

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

	public boolean isDisconnect()
	{
		return false;
	}

	public boolean isAccept()
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
