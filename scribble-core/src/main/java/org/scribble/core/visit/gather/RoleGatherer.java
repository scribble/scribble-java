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

import java.util.stream.Stream;

import org.scribble.core.type.kind.ProtoKind;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.Choice;
import org.scribble.core.type.session.DirectedInteraction;
import org.scribble.core.type.session.DisconnectAction;
import org.scribble.core.type.session.Seq;

// N.B. does *not* gather do-args, nor follow subprotos
public class RoleGatherer<K extends ProtoKind, B extends Seq<K, B>>
		extends STypeGatherer<K, B, Role>
{

	@Override
	public Stream<Role> visitChoice(Choice<K, B> n)
	{
		return Stream.of(n.subj);
	}

	@Override
	public Stream<Role> visitDirectedInteraction(DirectedInteraction<K, B> n)
	{
		return Stream.of(n.src, n.dst);
	}

	@Override
	public Stream<Role> visitDisconnect(DisconnectAction<K, B> n)
	{
		return Stream.of(n.left, n.right);
	}
}
