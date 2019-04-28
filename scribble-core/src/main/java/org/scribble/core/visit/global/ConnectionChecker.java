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
package org.scribble.core.visit.global;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.core.type.kind.Global;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.Choice;
import org.scribble.core.type.session.ConnectAction;
import org.scribble.core.type.session.DirectedInteraction;
import org.scribble.core.type.session.DisconnectAction;
import org.scribble.core.type.session.Do;
import org.scribble.core.type.session.SType;
import org.scribble.core.type.session.global.GSeq;
import org.scribble.core.visit.STypeVisitor;
import org.scribble.util.ScribException;

// Pre: use on inlined or later (unsupported for Do, also Protocol)
// CHECKME: connection check at global vs. local level?
public class ConnectionChecker extends STypeVisitor<Global, GSeq>
{
	protected enum Status { CONN, DIS, AMBIG }
	
	public final Set<Role> roles;
	public final boolean implicit;

	protected final Map<Set<Role>, Status> conns;

	protected ConnectionChecker(Set<Role> roles, boolean implicit)
	{
		this.roles = Collections.unmodifiableSet(roles);
		this.implicit = implicit;
		this.conns = roles.stream()
				.flatMap(x -> roles.stream().filter(y -> !y.equals(x))
						.map(y -> Stream.of(x, y).collect(Collectors.toSet())))
				.distinct()
				.collect(
						Collectors.toMap(x -> x, x -> implicit ? Status.CONN : Status.DIS));
	}

	// Copy constructor
	protected ConnectionChecker(ConnectionChecker v)
	{
		this.roles = v.roles;
		this.implicit = v.implicit;
		this.conns = new HashMap<>(v.conns);
	}
	
	@Override
	public SType<Global, GSeq> visitChoice(Choice<Global, GSeq> n)
			throws ScribException
	{
		List<Map<Set<Role>, Status>> blocks = new LinkedList<>();
		for (GSeq block : n.blocks)
		{
			ConnectionChecker nested = new ConnectionChecker(this);
			block.visitWith(nested);
			blocks.add(nested.conns);
		}
		blocks.stream()
				.reduce((x, y) -> x.keySet().stream()
						.collect(Collectors.toMap(k -> k,
								k -> x.get(k).equals(y.get(k)) ? x.get(k) : Status.AMBIG)))
				.get().entrySet()
				.forEach(x -> this.conns.put(x.getKey(), x.getValue()));
		return n;  // super would do reconstruct
	}

	@Override
	public SType<Global, GSeq> visitDirectedInteraction(
			DirectedInteraction<Global, GSeq> n) throws ScribException
	{
		Set<Role> rs = Stream.of(n.src, n.dst).collect(Collectors.toSet());
		if (n instanceof ConnectAction<?, ?>)
		{
			switch (this.conns.get(rs))
			{
				case AMBIG: throw new ScribException("Roles may already be connected: " + n);
				case CONN: throw new ScribException("Roles already connected: " + n);
				default: this.conns.put(rs, Status.CONN);
			}
		}
		else
		{
			switch (this.conns.get(rs))
			{
				case AMBIG: throw new ScribException("Roles may be disconnected: " + n);
				case DIS: throw new ScribException("Roles disconnected: " + n);
				default:
			}
		}
		return super.visitDirectedInteraction(n);
	}

	@Override
	public SType<Global, GSeq> visitDisconnect(DisconnectAction<Global, GSeq> n)
			throws ScribException
	{
		Set<Role> rs = Stream.of(n.left, n.right).collect(Collectors.toSet());
		switch (this.conns.get(rs))
		{
			case AMBIG: throw new ScribException("Roles may already be disconnected: " + n);
			case DIS: throw new ScribException("Roles already disconnected: " + n);
			default: this.conns.put(rs, Status.DIS);
		}
		return super.visitDisconnect(n);
	}

	@Override
	public final SType<Global, GSeq> visitDo(Do<Global, GSeq> n) throws ScribException
	{
		throw new RuntimeException(this.getClass() + " unsupported for Do: " + n);
	}
	
}
