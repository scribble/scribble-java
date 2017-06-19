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
package org.scribble.sesstype.name;

import org.scribble.sesstype.Message;
import org.scribble.sesstype.kind.SigKind;


// The name of a declared (imported) message signature member
public class MessageSigName extends MemberName<SigKind> implements Message, MessageId<SigKind>
{
	private static final long serialVersionUID = 1L;
	
	public MessageSigName(ModuleName modname, MessageSigName simplename)
	{
		super(SigKind.KIND, modname, simplename);
	}

	public MessageSigName(String simplename)
	{
		super(SigKind.KIND, simplename);
	}

	@Override
	public SigKind getKind()
	{
		return SigKind.KIND;  // Same as this.kind
	}

	@Override
	public MessageSigName getSimpleName()
	{
		return new MessageSigName(getLastElement());
	}
	
	@Override 
	public MessageId<SigKind> getId()
	{
		return this;  // FIXME: should be resolved to a canonical name
	}

	@Override
	public boolean isMessageSigName()
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
		if (!(o instanceof MessageSigName))
		{
			return false;
		}
		MessageSigName n = (MessageSigName) o;
		return n.canEqual(this) && super.equals(o);
	}
	
	public boolean canEqual(Object o)
	{
		return o instanceof MessageSigName;
	}

	@Override
	public int hashCode()
	{
		int hash = 2791;
		hash = 31 * super.hashCode();
		return hash;
	}
}
