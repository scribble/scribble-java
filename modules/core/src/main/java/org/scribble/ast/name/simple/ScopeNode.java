package org.scribble.ast.name.simple;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.sesstype.kind.ScopeKind;
import org.scribble.sesstype.name.Scope;

public class ScopeNode extends SimpleNameNode<ScopeKind>
{
	public ScopeNode(String identifier)
	{
		super(identifier);
	}

	@Override
	protected ScopeNode copy()
	{
		return new ScopeNode(getIdentifier());
	}
	
	@Override
	public ScopeNode clone()
	{
		return (ScopeNode) AstFactoryImpl.FACTORY.SimpleNameNode(ScopeKind.KIND, getIdentifier());
	}

	@Override
	public Scope toName()
	{
		return new Scope(getIdentifier());
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof ScopeNode))
		{
			return false;
		}
		return ((ScopeNode) o).canEqual(this) && super.equals(o);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof ScopeNode;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 359;
		hash = 31 * super.hashCode();
		return hash;
	}
}
