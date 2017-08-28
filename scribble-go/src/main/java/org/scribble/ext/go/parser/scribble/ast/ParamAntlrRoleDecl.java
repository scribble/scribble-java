package org.scribble.ext.go.parser.scribble.ast;

import java.util.List;
import java.util.stream.Collectors;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.RoleDecl;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.ext.go.ast.ParamAstFactory;
import org.scribble.ext.go.type.index.ParamIndexFactory;
import org.scribble.ext.go.type.index.ParamIndexVar;
import org.scribble.parser.scribble.AntlrToScribParser;
import org.scribble.parser.scribble.ast.name.AntlrSimpleName;

public class ParamAntlrRoleDecl
{
	// Same as original
	public static final int NAME_CHILD_INDEX = 0;
	
	public static final int PARAM_CHILDREN_START_INDEX = 1;

	public static RoleDecl parseParamRoleDecl(AntlrToScribParser parser, CommonTree root, ParamAstFactory af)
	{
		RoleNode name = AntlrSimpleName.toRoleNode(getNameChild(root), af);
		/*List<ParamRoleParamNode> params = getParamChildren(root)
				.stream().map(p -> ParamAntlrSimpleName.toParamRoleParamNode(p, af)).collect(Collectors.toList());*/
		List<ParamIndexVar> params = getParamChildren(root)
				.stream().map(p -> ParamIndexFactory.ParamIntVar(p.getText())).collect(Collectors.toList());
		return af.ParamRoleDecl(root, name, params);
	}

	public static CommonTree getNameChild(CommonTree root)
	{
		return (CommonTree) root.getChild(NAME_CHILD_INDEX);
	}

	public static List<CommonTree> getParamChildren(CommonTree root)
	{
		List<?> children = root.getChildren();
		return children.subList(PARAM_CHILDREN_START_INDEX, children.size()).stream().map(c -> (CommonTree) c).collect(Collectors.toList());
	}

	/*public static int getStartIndex(CommonTree root)
	{
		return Integer.parseInt(root.getChild(START_CHILD_INDEX).getText());
	}

	public static int getEndIndex(CommonTree root)
	{
		return Integer.parseInt(root.getChild(END_CHILD_INDEX).getText());
	}*/
}
