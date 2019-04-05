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
package org.scribble.type.session;

import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.type.kind.ProtocolKind;
import org.scribble.type.name.DataType;
import org.scribble.type.name.GDelegationType;
import org.scribble.type.name.MemberName;
import org.scribble.type.name.MessageSigName;
import org.scribble.type.name.PayloadElemType;
import org.scribble.type.name.Role;

public abstract class MessageTransfer<K extends ProtocolKind>
		extends DirectedInteraction<K>
{
	public MessageTransfer(CommonTree source,
			Role src, Message msg, Role dst)
	{
		super(source, src, msg, dst);
	}

	@Override
	public List<MemberName<?>> getNonProtoDependencies()
	{
		List<MemberName<?>> res = new LinkedList<>();
		if (this.msg.isMessageSigName())
		{
			res.add((MessageSigName) this.msg);
		}
		else //if (this.msg.isMessageSig)
		{
			Payload pay = ((MessageSig) this.msg).payload;
			for (PayloadElemType<?> p : pay.elems)
			{
				if (p.isDataType())
				{
					res.add((DataType) p);
				}
				else if (p.isGDelegationType())  // TODO FIXME: should be projected to local name
				{
					res.add(((GDelegationType) p).getGlobalProtocol());
				}
				else
				{
					throw new RuntimeException("[TODO]: " + this);
				}
			}
		}
		return res;
	}
}
