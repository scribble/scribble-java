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
import org.scribble.core.model.global.actions.SWrapClient;
import org.scribble.core.type.name.Op;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.Payload;

// Duplicated from Disconnect
public class EWrapClient extends EAction
{
	public EWrapClient(ModelFactory ef, Role peer)
	{
		super(ef, peer, Op.EMPTY_OP, Payload.EMPTY_PAYLOAD);  // Must correspond with GWrap.UNIT_MESSAGE_SIG_NODE
	}
	
	@Override
	public EWrapServer toDual(Role self)
	{
		return this.ef.newEWrapServer(self);
	}

	@Override
	public SWrapClient toGlobal(ModelFactory sf, Role self)
	{
		return sf.newSWrapClient(self, this.peer);
	}
	
	@Override
	public int hashCode()
	{
		int hash = 1061;
		hash = 31 * hash + super.hashCode();
		return hash;
	}
	
	@Override
	public boolean isWrapClient()
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
		if (!(o instanceof EWrapClient))
		{
			return false;
		}
		return ((EWrapClient) o).canEqual(this) && super.equals(o);
	}

	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof EWrapClient;
	}

	@Override
	protected String getCommSymbol()
	{
		return "(!!)";
	}
}
