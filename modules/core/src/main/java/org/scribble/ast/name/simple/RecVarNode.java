package org.scribble.ast.name.simple;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.sesstype.kind.RecVarKind;
import org.scribble.sesstype.name.RecVar;

public class RecVarNode extends SimpleNameNode<RecVarKind>
{
	public RecVarNode(String identifier)
	{
		super(identifier);
	}

	@Override
	protected RecVarNode copy()
	{
		return new RecVarNode(getIdentifier());
	}
	
	@Override
	public RecVarNode clone()
	{
		return (RecVarNode) AstFactoryImpl.FACTORY.SimpleNameNode(RecVarKind.KIND, getIdentifier());
	}

	@Override
	public RecVar toName()
	{
		return new RecVar(getIdentifier());
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof RecVarNode))
		{
			return false;
		}
		return ((RecVarNode) o).canEqual(this) && super.equals(o);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof RecVarNode;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 349;
		hash = 31 * super.hashCode();
		return hash;
	}
}
