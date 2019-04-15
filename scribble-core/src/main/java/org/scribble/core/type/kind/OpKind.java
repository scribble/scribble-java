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
package org.scribble.core.type.kind;

import java.io.IOException;
import java.io.Serializable;

public class OpKind extends AbstractKind implements MsgIdKind, Serializable
{
	private static final long serialVersionUID = 1L;

	public static final OpKind KIND = new OpKind();
	
	public OpKind()
	{
		super("Op");
	}
	
	private void writeObject(java.io.ObjectOutputStream stream) throws IOException
	{
		stream.writeObject(this.kind);
	}

	private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException
	{
		this.kind = (String) stream.readObject();  // i.e. "Op"
	}

	@Override
	public boolean equals(Object o)
	{
		if (o == this)
		{
			return true;
		}
		if (!(o instanceof OpKind))
		{
			return false;
		}
		return ((OpKind) o).canEqual(this);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof OpKind;
	}
}
