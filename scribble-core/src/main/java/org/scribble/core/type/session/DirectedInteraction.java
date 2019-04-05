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

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.core.type.kind.ProtocolKind;
import org.scribble.core.type.name.MemberName;
import org.scribble.core.type.name.MessageId;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.name.Substitutions;

// Besides directed-ness, also features a Message
public abstract class DirectedInteraction<K extends ProtocolKind, B extends Seq<K, B>>
		extends BasicInteraction<K, B>
{
	public final Role src;
	public final Message msg;
	public final Role dst;

	public DirectedInteraction(CommonTree source,
			Role src, Message msg, Role dst)
	{
		super(source);
		this.src = src;
		this.msg = msg;
		this.dst = dst;
	}
	
	public abstract DirectedInteraction<K, B> reconstruct(
			CommonTree source, Role src, Message msg,
			Role dst);

	@Override
	public Set<Role> getRoles()
	{
		// Includes self
		return Stream.of(this.src, this.dst).collect(Collectors.toSet());
	}

	@Override
	public Set<MessageId<?>> getMessageIds()
	{
		return Stream.of(this.msg.getId()).collect(Collectors.toSet());
	}
	
	@Override
	public DirectedInteraction<K, B> substitute(Substitutions subs)
	{
		Message msg = this.msg;
		if (msg instanceof MemberName)
		{
			MemberName<?> n = (MemberName<?>) msg;
			if (subs.hasArg(n))
			{
				msg = (Message) subs.subsArg(n);
			}
		}
		return reconstruct(getSource(), subs.subsRole(this.src), msg,
				subs.subsRole(this.dst));
	}
	
	@Override
	public CommonTree getSource()
	{
		return (CommonTree) super.getSource();
	}
	
	@Override
	public int hashCode()
	{
		int hash = 10631;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.src.hashCode();
		hash = 31 * hash + this.msg.hashCode();
		hash = 31 * hash + this.dst.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof DirectedInteraction))
		{
			return false;
		}
		DirectedInteraction<?, ?> them = (DirectedInteraction<?, ?>) o;
		return super.equals(this)  // Does canEquals
				&& this.src.equals(them.src) && this.msg.equals(them.msg)
				&& this.dst.equals(them.dst);
	}
}
