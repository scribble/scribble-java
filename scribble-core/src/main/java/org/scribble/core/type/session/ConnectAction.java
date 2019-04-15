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

import org.antlr.runtime.tree.CommonTree;
import org.scribble.core.type.kind.ProtoKind;
import org.scribble.core.type.name.Role;

public abstract class ConnectAction<K extends ProtoKind, B extends Seq<K, B>>
		extends DirectedInteraction<K, B>
{
	public ConnectAction(CommonTree source,  // BaseInteractionNode not ideal
			Msg msg, Role src, Role dst)
	{
		super(source, msg, src, dst);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*@Override
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
				else if (p.isGDelegationType())
				{
					throw new RuntimeException("Shouldn't get in here: " + this);
				}
				else
				{
					throw new RuntimeException("[TODO]: " + this);
				}
			}
		}
		return res;
	}*/
}
