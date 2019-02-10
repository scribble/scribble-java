package org.scribble.lang.global;

import java.util.Deque;

import org.scribble.ast.ProtocolKindNode;
import org.scribble.lang.Recursion;
import org.scribble.lang.Substitutions;
import org.scribble.type.SubprotoSig;
import org.scribble.type.kind.Global;
import org.scribble.type.name.RecVar;
import org.scribble.type.name.Role;

public class GRecursion extends Recursion<Global, GSeq> implements GType
{
	public GRecursion(//org.scribble.ast.Recursion<Global> source, 
			ProtocolKindNode<Global> source,  // Due to inlining, protocoldecl -> rec
			RecVar recvar, GSeq body)
	{
		super(source, recvar, body);
	}
	
	@Override
	public GRecursion reconstruct(org.scribble.ast.ProtocolKindNode<Global> source,
			RecVar recvar, GSeq block)
	{
		return new GRecursion(source, recvar, block);
	}
	
	@Override
	public GRecursion substitute(Substitutions<Role> subs)
	{
		return reconstruct(getSource(), this.recvar, this.body.substitute(subs));
	}

	@Override
	public GRecursion getInlined(GTypeTranslator t, Deque<SubprotoSig> stack)
	{
		org.scribble.ast.ProtocolKindNode<Global> source = getSource();  // CHECKME: or empty source?
		GSeq body = this.body.getInlined(t, stack);
		RecVar rv = t.makeRecVar(stack.peek(), this.recvar);
		return reconstruct(source, rv, body);
	}

	@Override
	public int hashCode()
	{
		int hash = 2309;
		hash = 31 * hash + super.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof GRecursion))
		{
			return false;
		}
		return super.equals(o);
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof GRecursion;
	}
}
