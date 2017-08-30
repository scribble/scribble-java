package org.scribble.ext.go.ast.global;

import java.util.Arrays;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.Constants;
import org.scribble.ast.MessageNode;
import org.scribble.ast.MessageTransfer;
import org.scribble.ast.global.GMessageTransfer;
import org.scribble.ast.local.LNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ScribDel;
import org.scribble.ext.go.ast.ParamAstFactory;
import org.scribble.ext.go.type.index.ParamIndexExpr;
import org.scribble.ext.go.type.index.ParamIndexVar;
import org.scribble.main.ScribbleException;
import org.scribble.type.kind.Global;
import org.scribble.type.name.Role;
import org.scribble.visit.AstVisitor;

// Subsumes scatter/gather, src/dest range length one
public class ParamGMultiChoicesTransfer extends GMessageTransfer
{
	public final ParamIndexVar var;
	public final ParamIndexExpr destRangeStart;
	public final ParamIndexExpr destRangeEnd;    // Inclusive
	
	public ParamGMultiChoicesTransfer(CommonTree source, RoleNode src, MessageNode msg, RoleNode dest, 
			ParamIndexVar var, ParamIndexExpr destRangeStart, ParamIndexExpr destRangeEnd)
	{
		super(source, src, msg, Arrays.asList(dest));
		this.var = var;
		this.destRangeStart = destRangeStart;
		this.destRangeEnd = destRangeEnd;
	}
	
	@Override
	public LNode project(AstFactory af, Role self)
	{
		//return super.project(af, self);  // FIXME
		throw new RuntimeException("[param] TODO: " + this);  // Not just project, but most passes after parsing
	}

	@Override
	protected ParamGMultiChoicesTransfer copy()
	{
		return new ParamGMultiChoicesTransfer(this.source, this.src, this.msg, getDestinations().get(0),
				this.var, this.destRangeStart, this.destRangeEnd);
	}
	
	@Override
	public ParamGMultiChoicesTransfer clone(AstFactory af)
	{
		RoleNode src = this.src.clone(af);
		MessageNode msg = this.msg.clone(af);
		RoleNode dest = getDestinations().get(0).clone(af);
		return ((ParamAstFactory) af).ParamGMultiChoicesTransfer(this.source, src, msg, dest,
				this.var, this.destRangeStart, this.destRangeEnd);
	}
	
	@Override
	public ParamGMessageTransfer reconstruct(RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		throw new RuntimeException("[param] Shouldn't get in here: " + this);
	}

	public ParamGMultiChoicesTransfer reconstruct(RoleNode src, MessageNode msg, RoleNode dest, 
			ParamIndexVar var, ParamIndexExpr destRangeStart, ParamIndexExpr destRangeEnd)
	{
		ScribDel del = del();
		ParamGMultiChoicesTransfer gmt = new ParamGMultiChoicesTransfer(this.source, src, msg, dest, 
				var, destRangeStart, destRangeEnd);
		gmt = (ParamGMultiChoicesTransfer) gmt.del(del);
		return gmt;
	}

	@Override
	public MessageTransfer<Global> visitChildren(AstVisitor nv) throws ScribbleException
	{
		RoleNode src = (RoleNode) visitChild(this.src, nv);
		MessageNode msg = (MessageNode) visitChild(this.msg, nv);
		RoleNode dest = (RoleNode) visitChild(getDestinations().get(0), nv);
		return reconstruct(src, msg, dest, this.var, this.destRangeStart, this.destRangeEnd);
	}

	@Override
	public String toString()
	{
		return this.msg + " " + Constants.FROM_KW + " "
				+ this.src + "[" + this.var + "]"
				+ " " + Constants.TO_KW + " "
				+ getDestinations().get(0) + "[" + this.destRangeStart + ".." + this.destRangeEnd + "]"
				+ ";";
	}
}
