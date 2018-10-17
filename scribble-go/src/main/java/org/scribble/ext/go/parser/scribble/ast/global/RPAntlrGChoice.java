package org.scribble.ext.go.parser.scribble.ast.global;

import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.ext.go.ast.RPAstFactory;
import org.scribble.ext.go.ast.global.RPGChoice;
import org.scribble.ext.go.parser.scribble.ast.index.RPAntlrIndexExpr;
import org.scribble.ext.go.type.index.RPIndexExpr;
import org.scribble.parser.scribble.AntlrToScribParser;
import org.scribble.parser.scribble.AntlrToScribParserUtil;
import org.scribble.parser.scribble.ast.name.AntlrSimpleName;
import org.scribble.util.ScribParserException;

public class RPAntlrGChoice
{
	public static final int SUBJECT_CHILD_INDEX = 0;

	public static final int BLOCK_CHILDREN_START_INDEX = 2;
	public static final int INDEX_EXPR_CHILD_INDEX = 1;
	
	public static RPGChoice parseParamGChoice(AntlrToScribParser parser, CommonTree root, RPAstFactory af) throws ScribParserException
	{
		RoleNode subj = AntlrSimpleName.toRoleNode(getSubjectChild(root), af);
		List<GProtocolBlock> blocks = new LinkedList<>();
		RPIndexExpr expr = RPAntlrIndexExpr.parseParamIndexExpr(getIndexExprChild(root), af);
		for (CommonTree b : getBlockChildren(root))
		{
			blocks.add((GProtocolBlock) parser.parse(b, af));
		}
		return af.ParamGChoice(root, subj, expr, blocks);
	}

	public static CommonTree getSubjectChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(SUBJECT_CHILD_INDEX);
	}

	public static CommonTree getIndexExprChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(INDEX_EXPR_CHILD_INDEX);
	}

	public static List<CommonTree> getBlockChildren(CommonTree ct)
	{
		List<?> children = ct.getChildren();
		return AntlrToScribParserUtil.toCommonTreeList(children.subList(BLOCK_CHILDREN_START_INDEX, children.size()));
	}
}
