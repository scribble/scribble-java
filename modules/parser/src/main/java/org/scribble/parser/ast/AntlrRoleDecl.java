package org.scribble.parser.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.RoleDecl;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.parser.ScribParser;
import org.scribble.parser.ast.name.AntlrSimpleName;

public class AntlrRoleDecl
{
	public static final int NAME_CHILD_INDEX = 0;

	//public static RoleNode parseRoleDecl(AntlrModuleParser parser, CommonTree ct)
	public static RoleDecl parseRoleDecl(ScribParser parser, CommonTree ct)
	{
		RoleNode name = AntlrSimpleName.toRoleNode(getNameChild(ct));
		return AstFactoryImpl.FACTORY.RoleDecl(ct, name);
	}

	public static CommonTree getNameChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(NAME_CHILD_INDEX);
	}
}
