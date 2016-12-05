package org.scribble.ast.name.simple;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.sesstype.kind.ScopeKind;
import org.scribble.sesstype.name.Scope;

// Currently unused (TODO: interruptible)
public class ScopeNode extends SimpleNameNode<ScopeKind>
{
	public ScopeNode(CommonTree source, String identifier)
	{
		super(source, identifier);
	}

	@Override
	protected ScopeNode copy()
	{
		return new ScopeNode(this.source, getIdentifier());
	}
	
	@Override
	public ScopeNode clone()
	{
		return (ScopeNode) AstFactoryImpl.FACTORY.SimpleNameNode(this.source, ScopeKind.KIND, getIdentifier());
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
