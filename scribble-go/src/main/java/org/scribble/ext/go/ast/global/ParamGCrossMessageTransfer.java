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
import org.scribble.ext.go.ast.name.simple.ParamRoleParamNode;
import org.scribble.main.ScribbleException;
import org.scribble.type.kind.Global;
import org.scribble.type.name.Role;
import org.scribble.visit.AstVisitor;

// Subsumes scatter/gather, src/dest range length one
public class ParamGCrossMessageTransfer extends GMessageTransfer //implements ParamActionAssertNode
{
	// ParamRoleNode? -- cf. ParamRoleDecl
	// All >= 0; end >= start
	public final ParamRoleParamNode srcRangeStart;
	public final ParamRoleParamNode srcRangeEnd;     // Inclusive
	public final ParamRoleParamNode destRangeStart;
	public final ParamRoleParamNode destRangeEnd;    // Inclusive

	public ParamGCrossMessageTransfer(CommonTree source, RoleNode src, MessageNode msg, RoleNode dest, 
			ParamRoleParamNode srcRangeStart, ParamRoleParamNode srcRangeEnd, ParamRoleParamNode destRangeStart, ParamRoleParamNode destRangeEnd)
	{
		super(source, src, msg, Arrays.asList(dest));
		this.srcRangeStart = srcRangeStart;
		this.srcRangeEnd = srcRangeEnd;
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
	protected ParamGCrossMessageTransfer copy()
	{
		return new ParamGCrossMessageTransfer(this.source, this.src, this.msg, getDestinations().get(0),
				this.srcRangeStart, this.srcRangeEnd, this.destRangeStart, this.destRangeEnd);
	}
	
	@Override
	public ParamGCrossMessageTransfer clone(AstFactory af)
	{
		RoleNode src = this.src.clone(af);
		MessageNode msg = this.msg.clone(af);
		RoleNode dest = getDestinations().get(0).clone(af);
		return ((ParamAstFactory) af).ParamGCrossMessageTransfer(this.source, src, msg, dest,
				this.srcRangeStart, this.srcRangeEnd, this.destRangeStart, this.destRangeEnd);
	}

	@Override
	public ParamGCrossMessageTransfer reconstruct(RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		throw new RuntimeException("[param] Shouldn't get in here: " + this);
	}

	public ParamGCrossMessageTransfer reconstruct(RoleNode src, MessageNode msg, RoleNode dest, 
			ParamRoleParamNode srcRangeStart, ParamRoleParamNode srcRangeEnd, ParamRoleParamNode destRangeStart, ParamRoleParamNode destRangeEnd)
	{
		ScribDel del = del();
		ParamGCrossMessageTransfer gmt = new ParamGCrossMessageTransfer(this.source, src, msg, dest, 
				srcRangeStart, srcRangeEnd, destRangeStart, destRangeEnd);
		gmt = (ParamGCrossMessageTransfer) gmt.del(del);
		return gmt;
	}

	@Override
	public MessageTransfer<Global> visitChildren(AstVisitor nv) throws ScribbleException
	{
		RoleNode src = (RoleNode) visitChild(this.src, nv);
		MessageNode msg = (MessageNode) visitChild(this.msg, nv);
		RoleNode dest = (RoleNode) visitChild(getDestinations().get(0), nv);
		return reconstruct(src, msg, dest, this.srcRangeStart, this.srcRangeEnd, this.destRangeStart, this.destRangeEnd);
	}

	@Override
	public String toString()
	{
		return this.msg + Constants.FROM_KW + " "
				+ this.src + "[" + this.srcRangeStart + ".." + this.srcRangeEnd + "]"
				+ " " + Constants.TO_KW + " "
				+ getDestinations().get(0) + "[" + this.destRangeStart + ".." + this.destRangeEnd + "]"
				+ ";";
	}
}
