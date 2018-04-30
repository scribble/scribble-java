package org.scribble.ext.go.parser.scribble.ast.global;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.ext.go.ast.RPAstFactory;
import org.scribble.ext.go.ast.global.RPGForeach;
import org.scribble.ext.go.parser.scribble.ast.index.RPAntlrIndexExpr;
import org.scribble.ext.go.type.index.RPForeachVar;
import org.scribble.ext.go.type.index.RPIndexExpr;
import org.scribble.parser.scribble.AntlrToScribParser;
import org.scribble.parser.scribble.ast.name.AntlrSimpleName;
import org.scribble.util.ScribParserException;

public class RPCoreAntlrGForeach
{
	public static final int SUBJECT_CHILD_INDEX = 0;

	public static final int BLOCK_CHILD_INDEX = 4;

	public static final int INDEX_VAR_CHILD_INDEX = 1;
	public static final int START_INDEX_EXPR_CHILD_INDEX = 2;
	public static final int END_INDEX_EXPR_CHILD_INDEX = 3;
	
	public static RPGForeach parseRPGForeach(AntlrToScribParser parser, CommonTree root, RPAstFactory af) throws ScribParserException
	{
		RoleNode subj = AntlrSimpleName.toRoleNode(getSubjectChild(root), af);
		RPForeachVar var = (RPForeachVar) RPAntlrIndexExpr.parseRPForeachVar(getIndexVarChild(root), af);
		RPIndexExpr start = RPAntlrIndexExpr.parseParamIndexExpr(getStartIndexExprChild(root), af);
		RPIndexExpr end = RPAntlrIndexExpr.parseParamIndexExpr(getEndIndexExprChild(root), af);
		GProtocolBlock block = (GProtocolBlock) parser.parse(getBlockChild(root), af);
		return af.RPGForeach(root, subj, var, start, end, block);
	}

	public static CommonTree getSubjectChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(SUBJECT_CHILD_INDEX);
	}

	public static CommonTree getIndexVarChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(INDEX_VAR_CHILD_INDEX);
	}

	public static CommonTree getStartIndexExprChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(START_INDEX_EXPR_CHILD_INDEX);
	}

	public static CommonTree getEndIndexExprChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(END_INDEX_EXPR_CHILD_INDEX);
	}

	public static CommonTree getBlockChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(BLOCK_CHILD_INDEX);
	}
}
