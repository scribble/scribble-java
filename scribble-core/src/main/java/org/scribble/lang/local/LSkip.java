package org.scribble.lang.local;

import org.scribble.lang.STypeBase;
import org.scribble.lang.STypeInliner;
import org.scribble.lang.Substitutions;
import org.scribble.model.endpoint.EGraphBuilderUtil2;
import org.scribble.type.kind.Local;
import org.scribble.type.name.Role;

// Used during projection -- filtered out by GSeq::projection
public class LSkip extends STypeBase<Local> implements LType
{
	public static final LSkip SKIP = new LSkip();
	
	private LSkip()
	{
		super(null);
	}

	@Override
	public LSkip substitute(Substitutions<Role> subs)
	{
		return this;
	}

	@Override
	public LSkip getInlined(STypeInliner i)
	{
		return this;
	}

	@Override
	public org.scribble.ast.local.LNode getSource()
	{
		return null;
	}

	@Override
	public void buildGraph(EGraphBuilderUtil2 b)
	{
		throw new RuntimeException("Unsupported for: " + this);
	}

	@Override
	public String toString()
	{
		return "[skip];";
	}

	@Override
	public int hashCode()
	{
		int hash = 2833;
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
		if (!(o instanceof LSkip))
		{
			return false;
		}
		return ((LSkip) o).canEquals(this);
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof LSkip;
	}
}

