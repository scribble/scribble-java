package org.scribble.ext.go.parser.scribble.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.RoleDecl;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.ext.go.ast.ParamAstFactory;
import org.scribble.parser.scribble.AntlrToScribParser;
import org.scribble.parser.scribble.ast.name.AntlrSimpleName;

public class ParamAntlrRoleDecl
{
	// Same as original
	public static final int NAME_CHILD_INDEX = 0;
	
	public static final int START_CHILD_INDEX = 1;
	public static final int END_CHILD_INDEX = 2;

	public static RoleDecl parseParamRoleDecl(AntlrToScribParser parser, CommonTree root, ParamAstFactory af)
	{
		RoleNode name = AntlrSimpleName.toRoleNode(getNameChild(root), af);
		int start = getStartIndex(root);
		int end = getEndIndex(root);
		return af.ParamRoleDecl(root, name, start, end);
	}

	public static CommonTree getNameChild(CommonTree root)
	{
		return (CommonTree) root.getChild(NAME_CHILD_INDEX);
	}

	public static int getStartIndex(CommonTree root)
	{
		return Integer.parseInt(root.getChild(START_CHILD_INDEX).getText());
	}

	public static int getEndIndex(CommonTree root)
	{
		return Integer.parseInt(root.getChild(END_CHILD_INDEX).getText());
	}
}
