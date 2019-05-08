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
package org.scribble.core.visit.gather;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import org.scribble.core.type.kind.ProtoKind;
import org.scribble.core.type.name.DataName;
import org.scribble.core.type.name.GDelegType;
import org.scribble.core.type.name.MemberName;
import org.scribble.core.type.name.PayElemType;
import org.scribble.core.type.name.SigName;
import org.scribble.core.type.session.Choice;
import org.scribble.core.type.session.ConnectAction;
import org.scribble.core.type.session.DirectedInteraction;
import org.scribble.core.type.session.Do;
import org.scribble.core.type.session.Payload;
import org.scribble.core.type.session.Seq;
import org.scribble.core.type.session.SigLit;


// Result should not contain duplicates (i.e., due to Choice/Seq)
// Result does not necessarily contain root proto (protodecl is not an SType), but may do so via dependencies
// N.B. delegation payloads currently gathered here, not by getProtoDependencies -- CHECKME: refactor?
public class NonProtoDepsGatherer<K extends ProtoKind, B extends Seq<K, B>>
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
			if (n.msg.isSigName())
			{
				res.add((SigName) n.msg);
			}
			else //if (this.msg.isMessageSig)
			{
				Payload pay = ((SigLit) n.msg).payload;
				for (PayElemType<?> p : pay.elems)
				{
					if (p.isDataName())
					{
						res.add((DataName) p);
					}
					else if (p.isGDelegationType())  // TODO FIXME: should be projected to local name
					{
						if (n instanceof ConnectAction<?, ?>)
						{
							throw new RuntimeException("Shouldn't get in here: " + n);
						}
						res.add(((GDelegType) p).getGlobalProtocol());
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
	public Stream<MemberName<?>> visitDo(Do<K, B> n)
	{
		return n.args.stream()
				.filter(x -> (x instanceof SigLit) || (x instanceof DataName))  // CHECKME: refactor?
				.map(x -> (MemberName<?>) x);
	}
}
