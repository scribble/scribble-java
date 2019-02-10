package org.scribble.lang.global;

import java.util.Deque;

import org.scribble.ast.ProtocolKindNode;
import org.scribble.lang.Continue;
import org.scribble.lang.Substitutions;
import org.scribble.type.SubprotoSig;
import org.scribble.type.kind.Global;
import org.scribble.type.name.RecVar;
import org.scribble.type.name.Role;

public class GContinue extends Continue<Global> implements GType
{
	public GContinue(//org.scribble.ast.Continue<Global> source, 
			ProtocolKindNode<Global> source,  // Due to inlining, do -> continue
			RecVar recvar)
	{
		super(source, recvar);
	}
	
	@Override
	public GContinue reconstruct(org.scribble.ast.ProtocolKindNode<Global> source,
			RecVar recvar)
	{
		return new GContinue(source, recvar);
	}

	@Override
	public GContinue substitute(Substitutions<Role> subs)
	{
		return (GContinue) super.substitute(subs);
	}

	@Override
	public GContinue getInlined(GTypeTranslator t, Deque<SubprotoSig> stack)
	{
		return (GContinue) super.getInlined(t, stack);
	}

	@Override
	public int hashCode()
	{
		int hash = 3457;
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
		if (!(o instanceof GContinue))
		{
			return false;
		}
		return super.equals(o);
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof GContinue;
	}
}
