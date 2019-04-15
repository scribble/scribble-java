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

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.core.type.kind.ProtoKind;
import org.scribble.core.type.name.Role;
import org.scribble.core.visit.STypeAgg;
import org.scribble.core.visit.STypeAggNoThrow;
import org.scribble.util.ScribException;

public abstract class Choice<K extends ProtoKind, B extends Seq<K, B>>
		extends STypeBase<K, B> implements SType<K, B>
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
			//List<? extends Seq<K, B>> blocks);
	
	@Override
	public <T> T visitWith(STypeAgg<K, B, T> v) throws ScribException
	{
		return v.visitChoice(this);
	}
	
	@Override
	public <T> T visitWithNoThrow(STypeAggNoThrow<K, B, T> v)
	{
		return v.visitChoice(this);
	}
	
	@Override
	public <T> Stream<T> gather(Function<SType<K, B>, Stream<T>> f)
	{
		return Stream.concat(f.apply(this),
				this.blocks.stream().flatMap(x -> x.gather(f)));
	}
	
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*

	@Override
	public SType<K, B> visitWith(STypeVisitor<K, B> v) throws ScribException
	{
		return v.visitChoice(this);
	}
	
	@Override
	public SType<K, B> visitWithNoEx(STypeVisitorNoEx<K, B> v)
	{
		return v.visitChoice(this);
	}

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

	@Override
	public Choice<K, B> getInlined(STypeInliner v)
	{
		CommonTree source = getSource();  // CHECKME: or empty source?
		List<B> blocks = this.blocks.stream().map(x -> x.getInlined(v))
				.collect(Collectors.toList());
		return reconstruct(source, this.subj, blocks);
	}

	@Override
	public Choice<K, B> unfoldAllOnce(STypeUnfolder<K> u)
	{
		CommonTree source = getSource();  // CHECKME: or empty source?
		List<B> blocks = this.blocks.stream().map(x -> x.unfoldAllOnce(u))
				.collect(Collectors.toList());
		return reconstruct(source, this.subj, blocks);
	}
	
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

	@Override
	public Choice<K, B> substitute(Substitutions subs)
	{
		List<B> blocks = this.blocks.stream().map(x -> x.substitute(subs))
				.collect(Collectors.toList());
		return reconstruct(getSource(), subs.subsRole(this.subj), blocks);
	}

	@Override
	public Choice<K, B> pruneRecs()
	{
		List<B> blocks = this.blocks.stream().map(x -> x.pruneRecs())
				.collect(Collectors.toList());
		return reconstruct(getSource(), this.subj, blocks);
	}
	*/
}
