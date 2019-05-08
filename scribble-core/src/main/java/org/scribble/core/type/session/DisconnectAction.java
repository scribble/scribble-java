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

import java.util.function.Function;
import java.util.stream.Stream;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.core.type.kind.ProtoKind;
import org.scribble.core.type.name.Role;
import org.scribble.core.visit.STypeAgg;
import org.scribble.core.visit.STypeAggNoThrow;
import org.scribble.util.ScribException;

// Base class would be "SymmetricInteraction" (cf., DirectedInteraction)
public abstract class DisconnectAction<K extends ProtoKind, B extends Seq<K, B>>
		extends BasicInteraction<K, B>
{
	public final Role left;
	public final Role right;

	public DisconnectAction(CommonTree source,
			Role left, Role right)
	{
		super(source);
		this.left = left;
		this.right = right;
	}
	
	public abstract DisconnectAction<K, B> reconstruct(
			CommonTree source, Role src, Role dst);
	
	@Override
	public <T> T visitWith(STypeAgg<K, B, T> v) throws ScribException
	{
		return v.visitDisconnect(this);
	}
	
	@Override
	public <T> T visitWithNoThrow(STypeAggNoThrow<K, B, T> v)
	{
		return v.visitDisconnect(this);
	}
	
	@Override
	public <T> Stream<T> gather(Function<SType<K, B>, Stream<T>> f)
	{
		return f.apply(this);
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
		DisconnectAction<?, ?> them = (DisconnectAction<?, ?>) o;
		return super.equals(this)  // Does canEquals
				&& this.left.equals(them.left) && this.right.equals(them.right);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	/*

	@Override
	public SType<K, B> visitWith(STypeVisitor<K, B> v) throws ScribException
	{
		return v.visitDisconnect(this);
	}

	@Override
	public SType<K, B> visitWithNoEx(STypeVisitorNoEx<K, B> v)
	{
		return v.visitDisconnect(this);
	}
	
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
	public List<MemberName<?>> getNonProtoDependencies()
	{
		return Collections.emptyList();
	}

	@Override
	public DisconnectAction<K, B> substitute(Substitutions subs)
	{
		return reconstruct(getSource(), subs.subsRole(this.left), 
				subs.subsRole(this.right));
	}
	*/
}
