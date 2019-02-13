package org.scribble.lang.global;

import org.scribble.ast.ProtocolKindNode;
import org.scribble.lang.Recursion;
import org.scribble.lang.STypeInliner;
import org.scribble.lang.STypeUnfolder;
import org.scribble.lang.Substitutions;
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
	public GRecursion getInlined(STypeInliner i)//, Deque<SubprotoSig> stack)
	{
		org.scribble.ast.ProtocolKindNode<Global> source = getSource();  // CHECKME: or empty source?
		GSeq body = this.body.getInlined(i);//, stack);
		RecVar rv = i.makeRecVar(//stack.peek(), 
				this.recvar);  // FIXME: make GTypeInliner, and record recvars to check freshness (e.g., rec X in two choice cases)
		return reconstruct(source, rv, body);
	}

	@Override
	public GType unfoldAllOnce(STypeUnfolder<Global> u)
	{
		if (!u.hasRec(this.recvar))
		{
			u.pushRec(this.recvar, this.body);  // Never "popped", relying on recvar disamb by inliner -- cf. stack.pop in GDo::getInlined, must pop sig there for Seqs
			GType unf = (GType) this.body.unfoldAllOnce(u);
			return unf;
		}
		return this;
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
