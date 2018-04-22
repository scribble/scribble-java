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

public class RPGDotMessageTransfer extends RPGMessageTransfer
{
	public RPGDotMessageTransfer(CommonTree source, RoleNode src, MessageNode msg, RoleNode dest, 
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
	protected RPGDotMessageTransfer copy()
	{
		return new RPGDotMessageTransfer(this.source, this.src, this.msg, getDestinations().get(0),
				this.srcRangeStart, this.srcRangeEnd, this.destRangeStart, this.destRangeEnd);
	}
	
	@Override
	public RPGDotMessageTransfer clone(AstFactory af)
	{
		RoleNode src = this.src.clone(af);
		MessageNode msg = this.msg.clone(af);
		RoleNode dest = getDestinations().get(0).clone(af);
		return ((RPAstFactory) af).ParamGDotMessageTransfer(this.source, src, msg, dest,
				this.srcRangeStart, this.srcRangeEnd, this.destRangeStart, this.destRangeEnd);
	}

	@Override
	public RPGDotMessageTransfer reconstruct(RoleNode src, MessageNode msg, RoleNode dest, 
			//ParamRoleParamNode srcRangeStart, ParamRoleParamNode srcRangeEnd, ParamRoleParamNode destRangeStart, ParamRoleParamNode destRangeEnd)
			RPIndexExpr srcRangeStart, RPIndexExpr srcRangeEnd, RPIndexExpr destRangeStart, RPIndexExpr destRangeEnd)
	{
		ScribDel del = del();
		RPGDotMessageTransfer gmt = new RPGDotMessageTransfer(this.source, src, msg, dest, 
				srcRangeStart, srcRangeEnd, destRangeStart, destRangeEnd);
		gmt = (RPGDotMessageTransfer) gmt.del(del);
		return gmt;
	}

	@Override
	public String toString()
	{
		return this.msg + " " + Constants.FROM_KW + " " + rangesToString();
	}
}
