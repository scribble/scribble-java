package org.scribble.ext.go.ast.global;

import java.util.Arrays;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.Constants;
import org.scribble.ast.MessageNode;
import org.scribble.ast.MessageTransfer;
import org.scribble.ast.global.GMessageTransfer;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.ext.go.type.index.ParamIndexExpr;
import org.scribble.main.ScribbleException;
import org.scribble.type.kind.Global;
import org.scribble.visit.AstVisitor;

public abstract class ParamGMessageTransfer extends GMessageTransfer  //implements ParamActionAssertNode
{
	// ParamRoleNode? -- cf. ParamRoleDecl
	// All >= 0; end >= start
	/*public final ParamRoleParamNode srcRangeStart;
	public final ParamRoleParamNode srcRangeEnd;     // Inclusive
	public final ParamRoleParamNode destRangeStart;
	public final ParamRoleParamNode destRangeEnd;    // Inclusive*/
	public final ParamIndexExpr srcRangeStart;
	public final ParamIndexExpr srcRangeEnd;     // Inclusive
	public final ParamIndexExpr destRangeStart;
	public final ParamIndexExpr destRangeEnd;    // Inclusive

	public ParamGMessageTransfer(CommonTree source, RoleNode src, MessageNode msg, RoleNode dest, 
			//ParamRoleParamNode srcRangeStart, ParamRoleParamNode srcRangeEnd, ParamRoleParamNode destRangeStart, ParamRoleParamNode destRangeEnd)
			ParamIndexExpr srcRangeStart, ParamIndexExpr srcRangeEnd, ParamIndexExpr destRangeStart, ParamIndexExpr destRangeEnd)
	{
		super(source, src, msg, Arrays.asList(dest));
		this.srcRangeStart = srcRangeStart;
		this.srcRangeEnd = srcRangeEnd;
		this.destRangeStart = destRangeStart;
		this.destRangeEnd = destRangeEnd;
	}
	
	@Override
	public ParamGMessageTransfer reconstruct(RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		throw new RuntimeException("[param] Shouldn't get in here: " + this);
	}

	public abstract ParamGMessageTransfer reconstruct(RoleNode src, MessageNode msg, RoleNode dest, 
			//ParamRoleParamNode srcRangeStart, ParamRoleParamNode srcRangeEnd, ParamRoleParamNode destRangeStart, ParamRoleParamNode destRangeEnd);
			ParamIndexExpr srcRangeStart, ParamIndexExpr srcRangeEnd, ParamIndexExpr destRangeStart, ParamIndexExpr destRangeEnd);

	@Override
	public MessageTransfer<Global> visitChildren(AstVisitor nv) throws ScribbleException
	{
		RoleNode src = (RoleNode) visitChild(this.src, nv);
		MessageNode msg = (MessageNode) visitChild(this.msg, nv);
		RoleNode dest = (RoleNode) visitChild(getDestinations().get(0), nv);
		return reconstruct(src, msg, dest, this.srcRangeStart, this.srcRangeEnd, this.destRangeStart, this.destRangeEnd);
	}

	protected String rangesToString()
	{
		return 
					this.src + "[" + this.srcRangeStart + ".." + this.srcRangeEnd + "]"
				+ " " + Constants.TO_KW + " "
				+ getDestinations().get(0) + "[" + this.destRangeStart + ".." + this.destRangeEnd + "]"
				+ ";";
	}
}
