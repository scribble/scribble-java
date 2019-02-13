package org.scribble.lang;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ast.InteractionSeq;
import org.scribble.type.kind.ProtocolKind;
import org.scribble.type.name.Role;

public abstract class Seq<K extends ProtocolKind>
		extends STypeBase<K>
{
	public final List<? extends SType<K>> elems;  // GType or LType

	public Seq(InteractionSeq<K> source, List<? extends SType<K>> elems)
	{
		super(source);
		this.elems = Collections.unmodifiableList(elems);
	}
	
	public abstract Seq<K> reconstruct(InteractionSeq<K> source,
			List<? extends SType<K>> elems);

	@Override
	public Set<Role> getRoles()
	{
		return this.elems.stream().flatMap(x -> x.getRoles().stream())
				.collect(Collectors.toSet());
	}

	@Override
	public Seq<K> substitute(Substitutions<Role> subs)
	{
		List<? extends SType<K>> elems = this.elems.stream()
				.map(x -> x.substitute(subs)).collect(Collectors.toList());
		return reconstruct(getSource(), elems);
	}
	
	public boolean isEmpty()
	{
		return this.elems.isEmpty();
	}

	@Override
	public InteractionSeq<K> getSource() 
	{
		return (InteractionSeq<K>) super.getSource();
	}
	
	@Override
	public String toString()
	{
		return this.elems.stream().map(x -> x.toString())
				.collect(Collectors.joining("\n"));
	}

	@Override
	public int hashCode()
	{
		int hash = 1483;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.elems.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof Seq))
		{
			return false;
		}
		Seq<?> them = (Seq<?>) o;
		return super.equals(this)  // Does canEquals
				&& this.elems.equals(them.elems);
	}
}
