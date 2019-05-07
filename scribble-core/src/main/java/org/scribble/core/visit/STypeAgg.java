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

import org.scribble.core.type.kind.ProtoKind;
import org.scribble.core.type.session.Choice;
import org.scribble.core.type.session.Continue;
import org.scribble.core.type.session.DirectedInteraction;
import org.scribble.core.type.session.DisconnectAction;
import org.scribble.core.type.session.Do;
import org.scribble.core.type.session.Recursion;
import org.scribble.core.type.session.SType;
import org.scribble.core.type.session.Seq;
import org.scribble.util.ScribException;

public abstract class STypeAgg<K extends ProtoKind, B extends Seq<K, B>, T>
{
	// Internal use
	// Pre: agg(Stream.of(unit())) = unit()
	protected abstract T unit(SType<K, B> n) throws ScribException;

	// Internal use -- by default, agg is applied to the "compound" cases (choice, recursion, seq)
	// Pre: agg(Stream.of(unit())) = unit()
	protected abstract T agg(SType<K, B> n, Stream<T> ts) throws ScribException;  // Cf. generic varargs, heap pollution issue

	public T visitChoice(Choice<K, B> n) throws ScribException
	{
		List<T> blocks = new LinkedList<>();
		for (B b : n.blocks)
		{
			//blocks.add(b.visitWith(this));
			blocks.add(visitSeq(b));
		}
		return agg(n, blocks.stream());
	}
	
	public T visitContinue(Continue<K, B> n) throws ScribException
	{
		return unit(n);
	}

	public T visitDirectedInteraction(DirectedInteraction<K, B> n)
			throws ScribException
	{
		return unit(n);
	}

	public T visitDisconnect(DisconnectAction<K, B> n) throws ScribException
	{
		return unit(n);
	}

	public T visitDo(Do<K, B> n) throws ScribException
	{
		return unit(n);
	}

	public T visitRecursion(Recursion<K, B> n) throws ScribException
	{
		//return agg(n, Stream.of(n.body.visitWith(this)));
		return agg(n, Stream.of(visitSeq(n.body)));
	}

	// Param "hardcoded" to B (cf. Seq, or SType return) -- this visitor pattern depends on B for Choice/Recursion/etc reconstruction
	public T visitSeq(B n) throws ScribException
	{
		List<T> elems = new LinkedList<>();
		for (SType<K, B> e : n.elems)
		{
			elems.add(e.visitWith(this));
		}
		return agg(n, elems.stream());
	}
}




















/*@FunctionalInterface
interface STypeVisitorFunction<K extends ProtocolKind, B extends Seq<K, B>, R extends Stream<?>>
{
	R f(SType<K, B> n, STypeVisitor<K, B> v);
}*/
