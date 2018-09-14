package org.scribble.ext.go.ast;

import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.MessageNode;
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.ast.name.qualified.GProtocolNameNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.ext.go.ast.global.RPGChoice;
import org.scribble.ext.go.ast.global.RPGCrossMessageTransfer;
import org.scribble.ext.go.ast.global.RPGDelegationElem;
import org.scribble.ext.go.ast.global.RPGDotMessageTransfer;
import org.scribble.ext.go.ast.global.RPGForeach;
import org.scribble.ext.go.ast.global.RPGMultiChoices;
import org.scribble.ext.go.ast.global.RPGMultiChoicesTransfer;
import org.scribble.ext.go.ast.name.simple.RPIndexedRoleNode;
import org.scribble.ext.go.type.index.RPForeachVar;
import org.scribble.ext.go.type.index.RPIndexExpr;
import org.scribble.ext.go.type.index.RPIndexVar;


public interface RPAstFactory extends AstFactory
{
	////ParamRoleDecl ParamRoleDecl(CommonTree source, RoleNode namenode, List<ParamRoleParamNode> params);
	//RPRoleDecl ParamRoleDecl(CommonTree source, RoleNode namenode, List<RPIndexVar> params);

	RPGCrossMessageTransfer ParamGCrossMessageTransfer(CommonTree source, RoleNode src, MessageNode msg, RoleNode dest, 
			//ParamRoleParamNode srcRangeStart, ParamRoleParamNode srcRangeEnd, ParamRoleParamNode destRangeStart, ParamRoleParamNode destRangeEnd);
			RPIndexExpr srcRangeStart, RPIndexExpr srcRangeEnd, RPIndexExpr destRangeStart, RPIndexExpr destRangeEnd);
	RPGDotMessageTransfer ParamGDotMessageTransfer(CommonTree source, RoleNode src, MessageNode msg, RoleNode dest, 
			//ParamRoleParamNode srcRangeStart, ParamRoleParamNode srcRangeEnd, ParamRoleParamNode destRangeStart, ParamRoleParamNode destRangeEnd);
			RPIndexExpr srcRangeStart, RPIndexExpr srcRangeEnd, RPIndexExpr destRangeStart, RPIndexExpr destRangeEnd);
	RPGChoice ParamGChoice(CommonTree source, RoleNode subj, RPIndexExpr expr, List<GProtocolBlock> blocks);
	RPGForeach RPGForeach(CommonTree source, RoleNode subj, RPForeachVar param, RPIndexExpr start, RPIndexExpr end, GProtocolBlock block);
	RPIndexedRoleNode RPIndexedRoleNode(CommonTree source, String identifier, RPIndexExpr start, RPIndexExpr end);

	@Deprecated
	RPGMultiChoices ParamGMultiChoices(CommonTree source, RoleNode subj, RPIndexVar var,
			RPIndexExpr start, RPIndexExpr end, List<GProtocolBlock> blocks);
	@Deprecated
	RPGMultiChoicesTransfer ParamGMultiChoicesTransfer(CommonTree source, RoleNode src, MessageNode msg, RoleNode dest, 
			RPIndexVar var, RPIndexExpr destRangeStart, RPIndexExpr destRangeEnd);


	// param-core -- RPCoreAstFactory is for core subset, "full" AST (Antlr) stuff still here

	//RPCoreDelegDecl ParamCoreDelegDecl(CommonTree source, String schema, String extName, String extSource, DataTypeNode name);

	RPGDelegationElem RPGDelegationElem(CommonTree source, GProtocolNameNode root, GProtocolNameNode state, RoleNode role);
	//RPLDelegationElem RPLDelegationElem(CommonTree source, LProtocolNameNode proto);  // Delegation currently supported only by core, and just uses RPCoreGDelegationType
}
