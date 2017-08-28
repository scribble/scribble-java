package org.scribble.ext.go.ast.name.simple;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.name.simple.SimpleNameNode;
import org.scribble.ext.go.type.kind.ParamRoleParamKind;
import org.scribble.ext.go.type.name.ParamRoleParam;


// Currently used for both "actual params" and int literals
@Deprecated
public class ParamRoleParamNode extends SimpleNameNode<ParamRoleParamKind>
{
	public ParamRoleParamNode(CommonTree source, String identifier)
	{
		super(source, identifier);
	}

	@Override
	protected ParamRoleParamNode copy()
	{
		return new ParamRoleParamNode(this.source, getIdentifier());
	}
	
	@Override
	public ParamRoleParamNode clone(AstFactory af)
	{
		return (ParamRoleParamNode) af.SimpleNameNode(this.source, ParamRoleParamKind.KIND, getIdentifier());
	}
	
	@Override
	public ParamRoleParam toName()
	{
		String id = getIdentifier();
		return new ParamRoleParam(id);
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof ParamRoleParamNode))
		{
			return false;
		}
		return ((ParamRoleParamNode) o).canEqual(this) && super.equals(o);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof ParamRoleParamNode;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 7159;
		hash = 31 * super.hashCode();
		return hash;
	}
}
