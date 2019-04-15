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
package org.scribble.core.visit;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import org.scribble.core.type.kind.ProtocolKind;
import org.scribble.core.type.name.DataType;
import org.scribble.core.type.name.GDelegationType;
import org.scribble.core.type.name.MemberName;
import org.scribble.core.type.name.MessageSigName;
import org.scribble.core.type.name.PayloadElemType;
import org.scribble.core.type.name.ProtocolName;
import org.scribble.core.type.session.Choice;
import org.scribble.core.type.session.ConnectAction;
import org.scribble.core.type.session.DirectedInteraction;
import org.scribble.core.type.session.Do;
import org.scribble.core.type.session.MessageSig;
import org.scribble.core.type.session.Payload;
import org.scribble.core.type.session.Seq;


// Result should not contain duplicates (i.e., due to Choice/Seq)
// Result does not necessarily contain root proto (protodecl is not an SType), but may do so via dependencies
// N.B. delegation payloads currently gathered here, not by getProtoDependencies -- CHECKME: refactor?
public class NonProtoDepsGatherer<K extends ProtocolKind, B extends Seq<K, B>>
		extends STypeGatherer<K, B, MemberName<?>>
{
	@Override
	public Stream<MemberName<?>> visitChoice(Choice<K, B> n)
	{
		return super.visitChoice(n).distinct();
	}

	@Override
	public Stream<MemberName<?>> visitSeq(Seq<K, B> n)
	{
		return super.visitSeq(n).distinct();
	}

	@Override
	public Stream<MemberName<?>> visitDirectedInteraction(DirectedInteraction<K, B> n)
	{
		{
			List<MemberName<?>> res = new LinkedList<>();
			if (n.msg.isMessageSigName())
			{
				res.add((MessageSigName) n.msg);
			}
			else //if (this.msg.isMessageSig)
			{
				Payload pay = ((MessageSig) n.msg).payload;
				for (PayloadElemType<?> p : pay.elems)
				{
					if (p.isDataType())
					{
						res.add((DataType) p);
					}
					else if (p.isGDelegationType())  // TODO FIXME: should be projected to local name
					{
						if (n instanceof ConnectAction<?, ?>)
						{
							throw new RuntimeException("Shouldn't get in here: " + n);
						}
						res.add(((GDelegationType) p).getGlobalProtocol());
					}
					else
					{
						throw new RuntimeException("[TODO]: " + this);
					}
				}
			}
			return res.stream();
		}
	}

	@Override
	public <N extends ProtocolName<K>> Stream<MemberName<?>> visitDo(
			Do<K, B, N> n)
	{
		return n.args.stream()
				.filter(x -> (x instanceof MessageSig) || (x instanceof DataType))  // CHECKME: refactor?
				.map(x -> (MemberName<?>) x);
	}
}
