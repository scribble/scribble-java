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

import org.scribble.core.model.ModelFactory;
import org.scribble.core.model.global.actions.SAcc;
import org.scribble.core.type.name.MsgId;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.Payload;

public class EAcc extends EAction
{
	public EAcc(ModelFactory mf, Role peer, MsgId<?> mid, Payload pay)
	{
		super(mf, peer, mid, pay);
	}
	
	@Override
	public EReq toDual(Role self)
	{
		return this.mf.local.EReq(self, this.mid, this.payload);
	}

	@Override
	public SAcc toGlobal(Role self)
	{
		return this.mf.global.SAcc(self, this.peer, this.mid, this.payload);
	}
	
	@Override
	public int hashCode()
	{
		int hash = 937;
		hash = 31 * hash + super.hashCode();
		return hash;
	}
	
	@Override
	public boolean isAccept()
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
		if (!(o instanceof EAcc))
		{
			return false;
		}
		return super.equals(o);  // Does canEquals
	}

	public boolean canEquals(Object o)
	{
		return o instanceof EAcc;
	}

	@Override
	protected String getCommSymbol()
	{
		return "??";
	}
}
