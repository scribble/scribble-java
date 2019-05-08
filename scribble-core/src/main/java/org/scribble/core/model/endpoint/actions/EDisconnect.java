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
import org.scribble.core.model.global.actions.SDisconnect;
import org.scribble.core.type.name.Op;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.Payload;

public class EDisconnect extends EAction
{
	public EDisconnect(ModelFactory ef, Role peer)
	{
		super(ef, peer, Op.EMPTY_OP, Payload.EMPTY_PAYLOAD);  // Must correspond with GDisconnect.UNIT_MESSAGE_SIG_NODE
	}
	
	@Override
	public EDisconnect toDual(Role self)
	{
		return this;
	}

	@Override
	public SDisconnect toGlobal(Role self)
	{
		return this.mf.global.SDisconnect(self, this.peer);
	}
	
	@Override
	public boolean isDisconnect()
	{
		return true;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 1009;
		hash = 31 * hash + super.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof EDisconnect))
		{
			return false;
		}
		return super.equals(o);  // Does canEquals
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof EDisconnect;
	}

	@Override
	protected String getCommSymbol()
	{
		//return "\u00A1\u00A1";
		return "-/-";
	}
}
