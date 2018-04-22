package org.scribble.ext.go.ast.name.simple;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.name.simple.SimpleNameNode;
import org.scribble.ext.go.type.kind.RPRoleParamKind;
import org.scribble.ext.go.type.name.RPRoleParam;


// Currently used for both "actual params" and int literals
@Deprecated
public class RPRoleParamNode extends SimpleNameNode<RPRoleParamKind>
{
	public RPRoleParamNode(CommonTree source, String identifier)
	{
		super(source, identifier);
	}

	@Override
	protected RPRoleParamNode copy()
	{
		return new RPRoleParamNode(this.source, getIdentifier());
	}
	
	@Override
	public RPRoleParamNode clone(AstFactory af)
	{
		return (RPRoleParamNode) af.SimpleNameNode(this.source, RPRoleParamKind.KIND, getIdentifier());
	}
	
	@Override
	public RPRoleParam toName()
	{
		String id = getIdentifier();
		return new RPRoleParam(id);
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof RPRoleParamNode))
		{
			return false;
		}
		return ((RPRoleParamNode) o).canEqual(this) && super.equals(o);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof RPRoleParamNode;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 7159;
		hash = 31 * super.hashCode();
		return hash;
	}
}
