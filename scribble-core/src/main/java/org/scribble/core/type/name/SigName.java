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
package org.scribble.core.type.name;

import org.scribble.core.type.kind.SigKind;
import org.scribble.core.type.session.Msg;


// The name of a declared (imported) message signature member
public class SigName extends MemberName<SigKind> implements Msg, MsgId<SigKind>
{
	private static final long serialVersionUID = 1L;
	
	public SigName(ModuleName modname, SigName simplename)
	{
		super(SigKind.KIND, modname, simplename);
	}

	public SigName(String simplename)
	{
		super(SigKind.KIND, simplename);
	}

	@Override
	public SigKind getKind()
	{
		return SigKind.KIND;  // Same as this.kind
	}

	@Override
	public SigName getSimpleName()
	{
		return new SigName(getLastElement());
	}
	
	@Override 
	public MsgId<SigKind> getId()
	{
		return this;  // FIXME: should be resolved to a canonical name
	}

	@Override
	public boolean isSigName()
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
		if (!(o instanceof SigName))
		{
			return false;
		}
		SigName n = (SigName) o;
		return n.canEquals(this) && super.equals(o);
	}
	
	public boolean canEquals(Object o)
	{
		return o instanceof SigName;
	}

	@Override
	public int hashCode()
	{
		int hash = 2791;
		hash = 31 * super.hashCode();
		return hash;
	}
}
