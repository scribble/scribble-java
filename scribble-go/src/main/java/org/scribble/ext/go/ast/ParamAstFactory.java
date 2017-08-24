package org.scribble.ext.go.ast;

import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.MessageNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.ext.go.ast.global.ParamGCrossMessageTransfer;
import org.scribble.ext.go.ast.name.simple.ParamRoleParamNode;


public interface ParamAstFactory extends AstFactory
{
	ParamRoleDecl ParamRoleDecl(CommonTree source, RoleNode namenode, List<ParamRoleParamNode> params);

	ParamGCrossMessageTransfer ParamGCrossMessageTransfer(CommonTree source, RoleNode src, MessageNode msg, RoleNode dest, 
			int srcRangeStart, int srcRangeEnd, int destRangeStart, int destRangeEnd);
}
