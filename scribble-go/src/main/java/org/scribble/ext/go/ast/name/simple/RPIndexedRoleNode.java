package org.scribble.ext.go.ast.name.simple;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.name.simple.SimpleNameNode;
import org.scribble.ext.go.ast.RPAstFactory;
import org.scribble.ext.go.type.index.RPIndexExpr;
import org.scribble.ext.go.type.kind.RPIndexedRoleKind;
import org.scribble.ext.go.type.name.RPIndexedRole;


// FIXME: not currently used, but should use in, e.g., RPGCrossMessageTransfer -- use for both "actual params" and int literals
@Deprecated
public class RPIndexedRoleNode extends SimpleNameNode<RPIndexedRoleKind>  // Not RoleNode, for distinct Kind -- (though distinction maybe unecessary)
{
	public final RPIndexExpr start;  // Use "types" directly -- source syntax parsed directly to types (cf. scrib-assrt, AssrtAntlrToFormulaParser)
	public final RPIndexExpr end;
	
	public RPIndexedRoleNode(CommonTree source, String identifier, RPIndexExpr start, RPIndexExpr end)
	{
		super(source, identifier);
		this.start = start;
		this.end = end;
	}

	@Override
	protected RPIndexedRoleNode copy()
	{
		return new RPIndexedRoleNode(this.source, getIdentifier(), this.start, this.end);
	}
	
	@Override
	public RPIndexedRoleNode clone(AstFactory af)
	{
		return ((RPAstFactory) af).RPIndexedRoleNode(this.source, getIdentifier(), this.start, this.end);
	}
	
	@Override
	public RPIndexedRole toName()
	{
		String id = getIdentifier();
		return new RPIndexedRole(id, this.start, this.end);
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof RPIndexedRoleNode))
		{
			return false;
		}
		RPIndexedRoleNode them = (RPIndexedRoleNode) o;
		return super.equals(o)  // Doesn canEqual
				&& this.start.equals(them.start) && this.end.equals(them.end);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof RPIndexedRoleNode;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 7159;
		hash = 31*hash + super.hashCode();
		hash = 31*hash + this.start.hashCode();
		hash = 31*hash + this.end.hashCode();
		return hash;
	}
}
