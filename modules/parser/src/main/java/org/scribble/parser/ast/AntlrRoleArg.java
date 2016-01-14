package org.scribble.parser.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.RoleArg;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.parser.ScribParser;
import org.scribble.parser.ast.name.AntlrSimpleName;

public class AntlrRoleArg
{
	public static final int ARG_CHILD_INDEX = 0;

	public static RoleArg parseRoleArg(ScribParser parser, CommonTree ct)
	{
		RoleNode role = AntlrSimpleName.toRoleNode(getArgChild(ct));
		return AstFactoryImpl.FACTORY.RoleArg(role);
	}

	public static CommonTree getArgChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(ARG_CHILD_INDEX);
	}
}
