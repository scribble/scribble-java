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
package org.scribble.model;

import org.scribble.sesstype.Payload;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;

public abstract class MAction<K extends ProtocolKind>
{
	/*private static int count = 0;
	
	public final int id;  // Was using for trace enumeration, but breaks isAcceptable -- but need for non-det models*/
	
	public final Role obj;
	public final MessageId<?> mid;
	public final Payload payload;  // EMPTY_PAYLOAD for MessageSigNames
	
	protected MAction(Role obj, MessageId<?> mid, Payload payload)
	{
		//this.id = ModelAction.count++;

		this.obj = obj;
		this.mid = mid;
		this.payload = payload;
	}
	
	@Override
	public String toString()
	{
		return this.obj + getCommSymbol() + this.mid + this.payload;
	}

	public String toStringWithMessageIdHack()
	{
		String m = this.mid.isMessageSigName() ? "^" + this.mid : this.mid.toString();  // HACK
		return this.obj + getCommSymbol() + m + this.payload;
	}
	
	protected abstract String getCommSymbol();
	
	@Override
	public int hashCode()
	{
		int hash = 919;
		hash = 31 * hash + this.obj.hashCode();
		hash = 31 * hash + this.mid.hashCode();
		hash = 31 * hash + this.payload.hashCode();
		return hash;
	}

	/*@Override
	public final int hashCode()
	{
		int hash = 79;
		hash = 31 * hash + this.id;
		return hash;
	}*/
	
	/*public boolean equiv(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof ModelAction))
		{
			return false;
		}
		ModelAction<?> a = (ModelAction<?>) o;  // Refactor as "compatible"
		return a.canEqual(this) && 
				this.obj.equals(a.obj) && this.mid.equals(a.mid) && this.payload.equals(a.payload);
	}*/

	@Override
	public boolean equals(Object o)  // FIXME: kind
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof MAction))
		{
			return false;
		}
		MAction<?> a = (MAction<?>) o;  // Refactor as "compatible"
		return a.canEqual(this) && 
				this.obj.equals(a.obj) && this.mid.equals(a.mid) && this.payload.equals(a.payload);
		//return this.id == ((ModelAction<?>) o).id;
	}
	
	public abstract boolean canEqual(Object o);
}
