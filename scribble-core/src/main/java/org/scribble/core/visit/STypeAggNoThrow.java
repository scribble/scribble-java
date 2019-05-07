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

// Worth it to maintain alongside STypeAgg? -- does touch a lot of places 
public abstract class STypeAggNoThrow<K extends ProtoKind, B extends Seq<K, B>, T>
		//extends STypeAgg<K, B, T>  // Not worth it, ex/no-ex method variants easily confused
{
	// Internal use
	// Pre: agg(Stream.of(unit())) = unit()
	protected abstract T unit(SType<K, B> n);

	// Internal use -- by default, agg is applied to the "compound" cases (choice, recursion, seq)
	// Pre: agg(Stream.of(unit())) = unit()
	protected abstract T agg(SType<K, B> n, Stream<T> ts);  // Cf. generic varargs, heap pollution issue
	
	public T visitContinue(Continue<K, B> n)
	{
		return unit(n);
	}

	public T visitChoice(Choice<K, B> n)
	{
		return agg(n, n.blocks.stream().map(x -> visitSeq(x)));
	}

	public T visitDirectedInteraction(DirectedInteraction<K, B> n)
	{
		return unit(n);
	}

	public T visitDisconnect(DisconnectAction<K, B> n)
	{
		return unit(n);
	}

	public T visitDo(Do<K, B> n)
	{
		return unit(n);
	}

	public T visitRecursion(Recursion<K, B> n)
	{
		return agg(n, Stream.of(visitSeq(n.body)));
	}

	// Param "hardcoded" to B (cf. Seq, or SType return) -- this visitor pattern depends on B for Choice/Recursion/etc reconstruction
	public T visitSeq(B n)
	{
		return agg(n, n.elems.stream().map(x -> x.visitWithNoThrow(this)));
	}
}




















/*@FunctionalInterface
interface STypeVisitorFunction<K extends ProtocolKind, B extends Seq<K, B>, R extends Stream<?>>
{
	R f(SType<K, B> n, STypeVisitor<K, B> v);
}*/
