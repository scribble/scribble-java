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
import org.scribble.core.type.name.ProtoName;
import org.scribble.core.type.session.Choice;
import org.scribble.core.type.session.Do;
import org.scribble.core.type.session.Seq;

// Result should not contain duplicates (i.e., due to Choice/Seq)
// Result does not necessarily contain root proto (protodecl is not an SType), but may do so via dependencies
public class ProtoDepsCollector<K extends ProtoKind, B extends Seq<K, B>>
		extends STypeGatherer<K, B, ProtoName<K>>
{

	@Override
	public Stream<ProtoName<K>> visitChoice(Choice<K, B> n)
	{
		return super.visitChoice(n).distinct();
	}

	@Override
	public Stream<ProtoName<K>> visitSeq(Seq<K, B> n)
	{
		return super.visitSeq(n).distinct();
	}

	@Override
	public Stream<ProtoName<K>> visitDo(Do<K, B> n)
	{
		return Stream.of(n.proto);  // Should be full names
	}
}
