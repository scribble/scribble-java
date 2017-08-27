package org.scribble.ext.go.ast.global;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.Constants;
import org.scribble.ast.MessageNode;
import org.scribble.ast.local.LNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ScribDel;
import org.scribble.ext.go.ast.ParamAstFactory;
import org.scribble.ext.go.type.index.ParamIndexExpr;
import org.scribble.type.name.Role;

public class ParamGDotMessageTransfer extends ParamGMessageTransfer
{
	public ParamGDotMessageTransfer(CommonTree source, RoleNode src, MessageNode msg, RoleNode dest, 
			//ParamRoleParamNode srcRangeStart, ParamRoleParamNode srcRangeEnd, ParamRoleParamNode destRangeStart, ParamRoleParamNode destRangeEnd)
			ParamIndexExpr srcRangeStart, ParamIndexExpr srcRangeEnd, ParamIndexExpr destRangeStart, ParamIndexExpr destRangeEnd)
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
	protected ParamGDotMessageTransfer copy()
	{
		return new ParamGDotMessageTransfer(this.source, this.src, this.msg, getDestinations().get(0),
				this.srcRangeStart, this.srcRangeEnd, this.destRangeStart, this.destRangeEnd);
	}
	
	@Override
	public ParamGDotMessageTransfer clone(AstFactory af)
	{
		RoleNode src = this.src.clone(af);
		MessageNode msg = this.msg.clone(af);
		RoleNode dest = getDestinations().get(0).clone(af);
		return ((ParamAstFactory) af).ParamGDotMessageTransfer(this.source, src, msg, dest,
				this.srcRangeStart, this.srcRangeEnd, this.destRangeStart, this.destRangeEnd);
	}

	@Override
	public ParamGDotMessageTransfer reconstruct(RoleNode src, MessageNode msg, RoleNode dest, 
			//ParamRoleParamNode srcRangeStart, ParamRoleParamNode srcRangeEnd, ParamRoleParamNode destRangeStart, ParamRoleParamNode destRangeEnd)
			ParamIndexExpr srcRangeStart, ParamIndexExpr srcRangeEnd, ParamIndexExpr destRangeStart, ParamIndexExpr destRangeEnd)
	{
		ScribDel del = del();
		ParamGDotMessageTransfer gmt = new ParamGDotMessageTransfer(this.source, src, msg, dest, 
				srcRangeStart, srcRangeEnd, destRangeStart, destRangeEnd);
		gmt = (ParamGDotMessageTransfer) gmt.del(del);
		return gmt;
	}

	@Override
	public String toString()
	{
		return this.msg + " " + Constants.FROM_KW + " " + rangesToString();
	}
}
