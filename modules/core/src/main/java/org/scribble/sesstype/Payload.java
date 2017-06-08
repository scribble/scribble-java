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
package org.scribble.sesstype;

import java.util.Collections;
import java.util.List;

import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.kind.PayloadTypeKind;
import org.scribble.sesstype.name.PayloadType;

public class Payload
{
	public static final Payload EMPTY_PAYLOAD = new Payload(Collections.emptyList());
	
	public final List<PayloadType<? extends PayloadTypeKind>> elems;
	
	public Payload(List<PayloadType<? extends PayloadTypeKind>> payload)
	{
		this.elems = payload;
	}
	
	public boolean isEmpty()
	{
		return this.elems.isEmpty();
	}

	@Override
	public int hashCode()
	{
		int hash = 577;
		hash = 31 * hash + this.elems.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof Payload))
		{
			return false;
		}
		return this.elems.equals(((Payload) o).elems);
	}
	
	@Override
	public String toString()
	{
		if (this.elems.isEmpty())
		{
			return "()";
		}
		String payload = "(" + this.elems.get(0);
		for (PayloadType<? extends Kind> pt : this.elems.subList(1, this.elems.size()))
		{
			payload+= ", " + pt;
		}
		return payload + ")";
	}
	
	/*@Override
	public boolean isParameter()
	{
		return false;
	}*/
}
