package org.scribble.ext.go.ast.global;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.Constants;
import org.scribble.ast.MessageNode;
import org.scribble.ast.local.LNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ScribDel;
import org.scribble.ext.go.ast.RPAstFactory;
import org.scribble.ext.go.type.index.RPIndexExpr;
import org.scribble.type.name.Role;

// FIXME: rename, and use RPIndexedRoleNode
// Subsumes scatter/gather, src/dest range length one
public class RPGCrossMessageTransfer extends RPGMessageTransfer
{
	public RPGCrossMessageTransfer(CommonTree source, RoleNode src, MessageNode msg, RoleNode dest, 
			//ParamRoleParamNode srcRangeStart, ParamRoleParamNode srcRangeEnd, ParamRoleParamNode destRangeStart, ParamRoleParamNode destRangeEnd)
			RPIndexExpr srcRangeStart, RPIndexExpr srcRangeEnd, RPIndexExpr destRangeStart, RPIndexExpr destRangeEnd)
	{
		super(source, src, msg, dest, srcRangeStart, srcRangeEnd, destRangeStart, destRangeEnd);
	}
	
	@Override
	public LNode project(AstFactory af, Role self)
	{
		//return super.project(af, self);  // FIXME
		throw new RuntimeException("[param] TODO: " + this);  // Not just project, but most passes after parsing
	}

	@Override
	protected RPGCrossMessageTransfer copy()
	{
		return new RPGCrossMessageTransfer(this.source, this.src, this.msg, getDestinations().get(0),
				this.srcRangeStart, this.srcRangeEnd, this.destRangeStart, this.destRangeEnd);
	}
	
	@Override
	public RPGCrossMessageTransfer clone(AstFactory af)
	{
		RoleNode src = this.src.clone(af);
		MessageNode msg = this.msg.clone(af);
		RoleNode dest = getDestinations().get(0).clone(af);
		return ((RPAstFactory) af).ParamGCrossMessageTransfer(this.source, src, msg, dest,
				this.srcRangeStart, this.srcRangeEnd, this.destRangeStart, this.destRangeEnd);
	}

	@Override
	public RPGCrossMessageTransfer reconstruct(RoleNode src, MessageNode msg, RoleNode dest, 
			//ParamRoleParamNode srcRangeStart, ParamRoleParamNode srcRangeEnd, ParamRoleParamNode destRangeStart, ParamRoleParamNode destRangeEnd)
			RPIndexExpr srcRangeStart, RPIndexExpr srcRangeEnd, RPIndexExpr destRangeStart, RPIndexExpr destRangeEnd)
	{
		ScribDel del = del();
		RPGCrossMessageTransfer gmt = new RPGCrossMessageTransfer(this.source, src, msg, dest, 
				srcRangeStart, srcRangeEnd, destRangeStart, destRangeEnd);
		gmt = (RPGCrossMessageTransfer) gmt.del(del);
		return gmt;
	}

	@Override
	public String toString()
	{
		return this.msg + " " + Constants.FROM_KW + " " + rangesToString();
	}
}
