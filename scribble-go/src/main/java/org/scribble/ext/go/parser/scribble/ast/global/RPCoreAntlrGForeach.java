package org.scribble.ext.go.parser.scribble.ast.global;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
	//public static final int SUBJECT_CHILD_INDEX = 0;
	public static final int BLOCK_CHILD_INDEX = 0;
	public static final int FOREACHINTERVAL_CHILDREN_START_INDEX = 1;
	
	public static RPGForeach parseRPGForeach(AntlrToScribParser parser, CommonTree root, RPAstFactory af) throws ScribParserException
	{
		//RoleNode subj = AntlrSimpleName.toRoleNode(getSubjectChild(root), af);
		GProtocolBlock block = (GProtocolBlock) parser.parse(getBlockChild(root), af);

		List<RoleNode> subjs = new LinkedList<>();
		List<RPForeachVar> vars = new LinkedList<>();
		List<RPIndexExpr> starts = new LinkedList<>();
		List<RPIndexExpr> ends = new LinkedList<>();
		for (CommonTree fi : getForeachIntevalChildren(root))
		{
			subjs.add(AntlrSimpleName.toRoleNode(getForeachIntervalSubjectChild(fi), af));
			vars.add((RPForeachVar) RPAntlrIndexExpr.parseRPForeachVar(getForeachIntervalIndexVarChild(fi), af));
			starts.add(RPAntlrIndexExpr.parseParamIndexExpr(getForeachIntervalStartIndexExprChild(fi), af));
			ends.add(RPAntlrIndexExpr.parseParamIndexExpr(getForeachIntervalEndIndexExprChild(fi), af));
		}
		return af.RPGForeach(root, subjs, vars, starts, ends, block);
	}

	/*public static CommonTree getSubjectChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(SUBJECT_CHILD_INDEX);
	}*/

	public static CommonTree getBlockChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(BLOCK_CHILD_INDEX);
	}

	public static List<CommonTree> getForeachIntevalChildren(CommonTree ct)
	{
		List<? extends Object> children = ct.getChildren();
		return children.subList(FOREACHINTERVAL_CHILDREN_START_INDEX, children.size()).stream()
				.map(c -> (CommonTree) c).collect(Collectors.toList());
	}

	
	// PARAM_FOREACHINTERVAL -- factor out?
	public static final int SUBJECT_CHILD_INDEX = 0;
	public static final int INDEX_VAR_CHILD_INDEX = 1;
	public static final int START_INDEX_EXPR_CHILD_INDEX = 2;
	public static final int END_INDEX_EXPR_CHILD_INDEX = 3;

	public static CommonTree getForeachIntervalSubjectChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(SUBJECT_CHILD_INDEX);
	}

	public static CommonTree getForeachIntervalIndexVarChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(INDEX_VAR_CHILD_INDEX);
	}

	public static CommonTree getForeachIntervalStartIndexExprChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(START_INDEX_EXPR_CHILD_INDEX);
	}

	public static CommonTree getForeachIntervalEndIndexExprChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(END_INDEX_EXPR_CHILD_INDEX);
	}
}
