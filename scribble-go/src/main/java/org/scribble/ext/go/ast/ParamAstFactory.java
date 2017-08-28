package org.scribble.ext.go.ast;

import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.MessageNode;
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.ext.go.ast.global.ParamGChoice;
import org.scribble.ext.go.ast.global.ParamGCrossMessageTransfer;
import org.scribble.ext.go.ast.global.ParamGDotMessageTransfer;
import org.scribble.ext.go.type.index.ParamIndexExpr;
import org.scribble.ext.go.type.index.ParamIndexVar;


public interface ParamAstFactory extends AstFactory
{
	//ParamRoleDecl ParamRoleDecl(CommonTree source, RoleNode namenode, List<ParamRoleParamNode> params);
	ParamRoleDecl ParamRoleDecl(CommonTree source, RoleNode namenode, List<ParamIndexVar> params);

	ParamGCrossMessageTransfer ParamGCrossMessageTransfer(CommonTree source, RoleNode src, MessageNode msg, RoleNode dest, 
			//ParamRoleParamNode srcRangeStart, ParamRoleParamNode srcRangeEnd, ParamRoleParamNode destRangeStart, ParamRoleParamNode destRangeEnd);
			ParamIndexExpr srcRangeStart, ParamIndexExpr srcRangeEnd, ParamIndexExpr destRangeStart, ParamIndexExpr destRangeEnd);
	ParamGDotMessageTransfer ParamGDotMessageTransfer(CommonTree source, RoleNode src, MessageNode msg, RoleNode dest, 
			//ParamRoleParamNode srcRangeStart, ParamRoleParamNode srcRangeEnd, ParamRoleParamNode destRangeStart, ParamRoleParamNode destRangeEnd);
			ParamIndexExpr srcRangeStart, ParamIndexExpr srcRangeEnd, ParamIndexExpr destRangeStart, ParamIndexExpr destRangeEnd);
	ParamGChoice ParamGChoice(CommonTree source, RoleNode subj, ParamIndexExpr expr, List<GProtocolBlock> blocks);
}
