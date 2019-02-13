package org.scribble.lang;

import org.scribble.ast.ProtocolKindNode;
import org.scribble.type.kind.ProtocolKind;
import org.scribble.type.name.RecVar;

public abstract class Recursion<K extends ProtocolKind, B extends Seq<K>>
		extends STypeBase<K> implements SType<K>
{
	public final RecVar recvar;
	public final B body;

	public Recursion(//org.scribble.ast.Recursion<K> source, 
			ProtocolKindNode<K> source,  // Due to inlining, protodecl -> rec
			RecVar recvar, B body)
	{
		super(source);
		this.recvar = recvar;
		this.body = body;
	}

	public abstract Recursion<K, B> reconstruct(
			org.scribble.ast.ProtocolKindNode<K> source, RecVar recvar, B body);
	
	@Override
	public String toString()
	{
		return "rec " + this.recvar + " {\n" + this.body + "\n}";
	}

	@Override
	public int hashCode()
	{
		int hash = 1487;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.recvar.hashCode();
		hash = 31 * hash + this.body.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof Recursion))
		{
			return false;
		}
		Recursion<?, ?> them = (Recursion<?, ?>) o;
		return super.equals(this)  // Does canEquals
				&& this.recvar.equals(them.recvar) && this.body.equals(them.body);
	}
}
