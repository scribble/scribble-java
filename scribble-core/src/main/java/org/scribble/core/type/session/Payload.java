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
package org.scribble.core.type.session;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.scribble.core.type.kind.PayElemKind;
import org.scribble.core.type.name.PayElemType;

public class Payload
{
	public static final Payload EMPTY_PAYLOAD = new Payload(
			Collections.emptyList());
	
	public final List<PayElemType<? extends PayElemKind>> elems;

	public Payload(List<PayElemType<? extends PayElemKind>> elems)
	{
		this.elems = Collections.unmodifiableList(elems);
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
		return "(" + this.elems.stream().map(x -> x.toString())
				.collect(Collectors.joining(", ")) + ")";
	}
	
	/*@Override
	public boolean isParameter()
	{
		return false;
	}*/
}
