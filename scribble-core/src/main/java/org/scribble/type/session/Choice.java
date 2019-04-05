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

import org.antlr.runtime.tree.CommonTree;
import org.scribble.type.kind.ProtocolKind;
import org.scribble.type.name.MemberName;
import org.scribble.type.name.MessageId;
import org.scribble.type.name.ProtocolName;
import org.scribble.type.name.RecVar;
import org.scribble.type.name.Role;

public abstract class Choice<K extends ProtocolKind, B extends Seq<K>>
		extends STypeBase<K> implements SType<K>
{
	public final Role subj;
	public final List<B> blocks;  // Pre: size > 0
			// CHECKME: rename?

	public Choice(CommonTree source, Role subj,
			List<B> blocks)
	{
		super(source);
		this.subj = subj;
		this.blocks = Collections.unmodifiableList(blocks);
	}

	public abstract Choice<K, B> reconstruct(CommonTree source, Role subj,
			List<B> blocks);
			//List<? extends Seq<K>> blocks);
	
	@Override
	public Set<Role> getRoles()
	{
		Set<Role> res = Stream.of(this.subj).collect(Collectors.toSet());
		this.blocks.forEach(x -> res.addAll(x.getRoles()));
		return res;
	}

	@Override
	public Set<MessageId<?>> getMessageIds()
	{
		return this.blocks.stream().flatMap(x -> x.getMessageIds().stream())
				.collect(Collectors.toSet());
	}
	
	@Override
	public Set<RecVar> getRecVars()
	{
		return this.blocks.stream().flatMap(x -> x.getRecVars().stream())
				.collect(Collectors.toSet());
	}

	/*@Override
	public Choice<K, B> getInlined(STypeInliner v)
	{
		CommonTree source = getSource();  // CHECKME: or empty source?
		List<Seq<K>> blocks = this.blocks.stream().map(x -> x.getInlined(v))
				.collect(Collectors.toList());
		return reconstruct(source, this.subj, blocks);
	}

	@Override
	public Choice<K, B> unfoldAllOnce(STypeUnfolder<K> u)
	{
		CommonTree source = getSource();  // CHECKME: or empty source?
		List<Seq<K>> blocks = this.blocks.stream().map(x -> x.unfoldAllOnce(u))
				.collect(Collectors.toList());
		return reconstruct(source, this.subj, blocks);
	}*/

	@Override
	public List<ProtocolName<K>> getProtoDependencies()
	{
		return this.blocks.stream().flatMap(x -> x.getProtoDependencies().stream())
				.distinct().collect(Collectors.toList());
	}

	@Override
	public List<MemberName<?>> getNonProtoDependencies()
	{
		return this.blocks.stream()
				.flatMap(x -> x.getNonProtoDependencies().stream()).distinct()
				.collect(Collectors.toList());
	}

	//public abstract List<B> getBlocks();
	
	@Override
	public String toString()
	{
		return "choice at " + this.subj + " "
				+ this.blocks.stream().map(x -> "{\n" + x.toString() + "\n}")
						.collect(Collectors.joining(" or "));
	}

	@Override
	public int hashCode()
	{
		int hash = 1487;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.subj.hashCode();
		hash = 31 * hash + this.blocks.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof Choice))
		{
			return false;
		}
		Choice<?, ?> them = (Choice<?, ?>) o;
		return super.equals(this)  // Does canEquals
				&& this.subj.equals(them.subj) && this.blocks.equals(them.blocks);
	}
}
