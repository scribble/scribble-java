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
package org.scribble.runtime.net;

import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.scribble.sesstype.name.Op;

public class ScribMessage implements Serializable
{
	private static final long serialVersionUID = 1L;

	// add msg source
	public final Op op;
	public final Object[] payload;

	public ScribMessage(Op op, Object... payload)
	{
		this.op = op;
		this.payload = payload;  // FIXME: make defensive array copy?
	}
	
	/*// Unicast
	public MulticastSignature toMulticastSignature(Role src, Role dest, Scope scope)
	{
		return toMulticastSignature(src, Arrays.asList(dest), scope);
	}
	
	public MulticastSignature toMulticastSignature(Role src, List<Role> dests, Scope scope)
	{
		return new MulticastSignature(src, dests, scope, op, getPayloadTypes());
	}

	protected final List<PayloadType> getPayloadTypes()
	{
		List<PayloadType> types = new LinkedList<>();
		for (Object o : this.payload)
		{
			// FIXME: routine should be plugin based on schema
			types.add(new PayloadType(o.getClass().toString()));
		}
		return types;
	}*/

	@Override
	public int hashCode()
	{
		int hash = 73;
		//hash = 31 * hash + super.hashCode();
		hash = 31 * hash + op.hashCode();
		hash = 31 * hash + this.payload.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof ScribMessage))
		{
			return false;
		}
		ScribMessage m = (ScribMessage) o;
		return m.canEqual(this) && this.op.equals(m.op) && this.payload.equals(m.payload);
	}
	
	public boolean canEqual(Object o)
	{
		return o instanceof ScribMessage;
	}
	
	@Override
	public String toString()
	{
		String s = this.op + "(";
		if (this.payload.length > 0)
		{
			s += Arrays.asList(this.payload).stream().map((o) -> o.toString()).collect(Collectors.joining(", "));
		}
		return s + ")";
	}
}
