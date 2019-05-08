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

import java.util.Optional;
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

// Refactor as special case of Agg?
public abstract class STypeGatherer<K extends ProtoKind, B extends Seq<K, B>, T>
{
	// Pass this to SType.gather, e.g., n.gather(new RoleGatherer<Global, GSeq>()::visit)
	public Stream<T> visit(SType<K, B> n)
	{
		return typeSwitch(n).get();
	}

	// Can override by adding new cases when super call returns empty
	protected Optional<Stream<T>> typeSwitch(SType<K, B> n)
	{
		return (n instanceof DirectedInteraction<?, ?>) 
					? Optional.of(visitDirectedInteraction((DirectedInteraction<K, B>) n))
			: (n instanceof DisconnectAction<?, ?>)
					? Optional.of(visitDisconnect((DisconnectAction<K, B>) n))
			: (n instanceof Choice<?, ?>)    
					? Optional.of(visitChoice((Choice<K, B>) n))
			: (n instanceof Seq<?, ?>)       
					? Optional.of(visitSeq((Seq<K, B>) n))
			: (n instanceof Continue<?, ?>)  
					? Optional.of(visitContinue((Continue<K, B>) n))
			: (n instanceof Recursion<?, ?>) 
					? Optional.of(visitRecursion((Recursion<K, B>) n))
			: (n instanceof Do<?, ?>)     
					? Optional.of(this.visitDo((Do<K, B>) n))
			: Optional.empty(); 
					// Better for extensibility than "manually" throwing Exception (e.g., for overriding)
					// N.B. currently causes exception on get in visit
	}

	public Stream<T> visitContinue(Continue<K, B> n)
	{
		return Stream.of();
	}

	public Stream<T> visitChoice(Choice<K, B> n)
	{
		return Stream.of();
	}

	// CHECKME: split into ConnectionAction and MessageTransfer? cf. NonProtoDepsCollector -- and how about locals?
	// This is the "level" of reconstruct, though
	// Or offer all, with ConnectionAction and MessageTransfer delegating to DirectedInteraction by default?  (must order correctly in typeSwitch)
	public Stream<T> visitDirectedInteraction(DirectedInteraction<K, B> n)
	{
		return Stream.of();
	}

	public Stream<T> visitDisconnect(DisconnectAction<K, B> n)
	{
		return Stream.of();
	}

	public Stream<T> visitDo(Do<K, B> n)
	{
		return Stream.of();
	}

	public Stream<T> visitRecursion(Recursion<K, B> n)
	{
		return Stream.of();
	}

	public Stream<T> visitSeq(Seq<K, B> n)
	{
		return Stream.of();
	}
}
