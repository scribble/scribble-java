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

import org.scribble.type.Payload;
import org.scribble.type.name.Op;
import org.scribble.type.name.Role;

public class SWrapClient extends SAction
{
	public SWrapClient(Role subj, Role obj)
	{
		super(subj, obj, Op.EMPTY_OP, Payload.EMPTY_PAYLOAD);
	}
	
	@Override
	public boolean isConnect()
	{
		return true;
	}

	@Override
	public int hashCode()
	{
		int hash = 1069;
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
		if (!(o instanceof SWrapClient))
		{
			return false;
		}
		return ((SWrapClient) o).canEqual(this) && super.equals(o);
	}

	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof SWrapClient;
	}

	@Override
	protected String getCommSymbol()
	{
		return "(->>)";
	}
}
