package org.scribble.core.visit;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.scribble.core.type.kind.ProtocolKind;
import org.scribble.core.type.name.ProtocolName;
import org.scribble.core.type.name.RecVar;
import org.scribble.core.type.session.Recursion;
import org.scribble.core.type.session.SType;
import org.scribble.core.type.session.Seq;

public abstract class Unf<K extends ProtocolKind, B extends Seq<K, B>>
		extends STypeVisitor<K, B, ProtocolName<K>>
{
	private final Map<RecVar, Seq<K, ?>> recs = new HashMap<>(); 

	@Override
	public SType<K, B> visitRecursion(Recursion<K, B> n)
	{
		if (!hasRec(n.recvar))  // N.B. doesn't work if recvars shadowed
		{
			pushRec(n.recvar, n.body);
			SType<K, B> unf = n.body.visitWith(this);
			popRec(n.recvar);  
					// Needed for, e.g., repeat do's in separate choice cases -- cf. stack.pop in GDo::getInlined, must pop sig there for Seqs
			return unf;
		}
		return n;
	}

	@Override
	public B visitSeq(B n)
	{
		List<SType<K, B>> elems = new LinkedList<>();
		for (SType<K, B> e : n.elems)
		{
			SType<K, B> e1 = e.visitWith(this);
			if (e1 instanceof Seq<?, ?>)
			{
				elems.addAll(((Seq<K, B>) e1).elems);
			}
			else
			{
				elems.add(e1);
			}
		}
		return n.reconstruct(n.getSource(), elems);
	}

	public void pushRec(RecVar rv, Seq<K, ?> body)
	{
		if (this.recs.containsKey(rv))
		{
			throw new RuntimeException("Shouldn't get here: " + rv);
		}
		this.recs.put(rv, body);
	}

	public boolean hasRec(RecVar rv)
	{
		return this.recs.containsKey(rv);
	}
	
	public Seq<K, ?> getRec(RecVar rv)
	{
		return this.recs.get(rv);
	}
	
	public void popRec(RecVar rv)
	{
		this.recs.remove(rv);
	}
}
