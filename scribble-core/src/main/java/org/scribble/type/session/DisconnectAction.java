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

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.lang.Substitutions;
import org.scribble.type.kind.ProtocolKind;
import org.scribble.type.name.MemberName;
import org.scribble.type.name.MessageId;
import org.scribble.type.name.Role;

// Base class would be "SymmetricInteraction" (cf., DirectedInteraction)
public abstract class DisconnectAction<K extends ProtocolKind>
		extends BasicInteraction<K>
{
	public final Role left;
	public final Role right;

	public DisconnectAction(org.scribble.ast.DisconnectAction<K> source,
			Role left, Role right)
	{
		super(source);
		this.left = left;
		this.right = right;
	}
	
	public abstract DisconnectAction<K> reconstruct(
			org.scribble.ast.DisconnectAction<K> source, Role src, Role dst);

	@Override
	public Set<Role> getRoles()
	{
		// Includes self
		return Stream.of(this.left, this.right).collect(Collectors.toSet());
	}

	@Override
	public Set<MessageId<?>> getMessageIds()
	{
		return Collections.emptySet();
	}
	
	@Override
	public DisconnectAction<K> substitute(Substitutions subs)
	{
		return reconstruct(getSource(), subs.subsRole(this.left), 
				subs.subsRole(this.right));
	}

	@Override
	public List<MemberName<?>> getNonProtoDependencies()
	{
		return Collections.emptyList();
	}
	
	@Override
	public org.scribble.ast.DisconnectAction<K> getSource()
	{
		return (org.scribble.ast.DisconnectAction<K>) super.getSource();
	}
	
	@Override
	public int hashCode()
	{
		int hash = 10663;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.left.hashCode();
		hash = 31 * hash + this.right.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof DisconnectAction))
		{
			return false;
		}
		DisconnectAction<?> them = (DisconnectAction<?>) o;
		return super.equals(this)  // Does canEquals
				&& this.left.equals(them.left) && this.right.equals(them.right);
	}
}
