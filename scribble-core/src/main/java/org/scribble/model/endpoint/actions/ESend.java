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

import org.scribble.model.endpoint.EModelFactory;
import org.scribble.model.global.SModelFactory;
import org.scribble.model.global.actions.SSend;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;

public class ESend extends EAction
{
	/*protected static final Set<Send> SENDS = new HashSet<>();
	
	public static Send get(Role peer, MessageId<?> mid, Payload payload)
	{
		Send send = new Send(peer, mid, payload, true);
		for (Send s : Send.SENDS)  // FIXME: hashmap
		{
			if (s.equiv(send))
			{
				return s;
			}
		}
		Send.SENDS.add(send);
		return send;
	}
	
	public Send(Role peer, MessageId<?> mid, Payload payload, boolean hack)
	{
		super(peer, mid, payload);
	}*/

	public ESend(EModelFactory ef, Role peer, MessageId<?> mid, Payload payload)
	{
		super(ef, peer, mid, payload);
		//Send.SENDS.add(this);
	}
	
	@Override
	public EReceive toDual(Role self)
	{
		return this.ef.newEReceive(self, this.mid, this.payload);
		//return Receive.get(self, this.mid, this.payload);
	}

	@Override
	//public GModelAction toGlobal(Role self)
	public SSend toGlobal(SModelFactory sf, Role self)
	{
		//return new GModelAction(self, this.peer, this.mid, this.payload);
		////return GModelAction.get(self, this.peer, this.mid, this.payload);
		return sf.newSSend(self, this.peer, this.mid, this.payload);
	}
	
	@Override
	public int hashCode()
	{
		int hash = 953;
		hash = 31 * hash + super.hashCode();
		return hash;
	}
	
	@Override
	public boolean isSend()
	{
		return true;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof ESend))
		{
			return false;
		}
		return ((ESend) o).canEqual(this) && super.equals(o);
	}

	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof ESend;
	}

	@Override
	protected String getCommSymbol()
	{
		return "!";
	}
}
