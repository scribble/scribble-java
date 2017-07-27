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
package org.scribble.type.name;

import org.scribble.type.kind.OpKind;

public class Op extends AbstractName<OpKind> implements MessageId<OpKind>
{
	private static final long serialVersionUID = 1L;
	
	public static final Op EMPTY_OPERATOR = new Op();

	protected Op()
	{
		super(OpKind.KIND);
	}

	public Op(String text)
	{
		super(OpKind.KIND, text);
	}

	@Override
	public boolean isOp()
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
		if (!(o instanceof Op))
		{
			return false;
		}
		Op n = (Op) o;
		return n.canEqual(this) && super.equals(o);
	}
	
	public boolean canEqual(Object o)
	{
		return o instanceof Op;
	}

	@Override
	public int hashCode()
	{
		int hash = 2801;
		hash = 31 * super.hashCode();
		return hash;
	}
}
