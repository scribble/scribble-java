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
package org.scribble.core.model.endpoint;

import org.scribble.core.model.ModelFactory;
import org.scribble.core.model.endpoint.actions.EAction;
import org.scribble.core.model.global.actions.SAction;
import org.scribble.core.type.name.Op;
import org.scribble.core.type.name.RecVar;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.Payload;

class UnguardedContinueEdge extends EAction
{
	public UnguardedContinueEdge(ModelFactory mf, RecVar rv)
	{
		super(mf, Role.EMPTY_ROLE, new Op(rv.toString()), Payload.EMPTY_PAYLOAD);  // HACK
	}
	
	@Override
	public EAction toDual(Role self)
	{
		throw new RuntimeException("Shouldn't get in here: " + this);
	}

	@Override
	public SAction toGlobal(Role self)
	{
		throw new RuntimeException("Shouldn't get in here: " + this);
	}

	@Override
	protected String getCommSymbol()
	{
		return "#";
	}
	
	@Override
	public int hashCode()
	{
		int hash = 1021;
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
		if (!(o instanceof UnguardedContinueEdge))
		{
			return false;
		}
		return super.equals(o);  // Checks canEquals
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof UnguardedContinueEdge;
	}
}
