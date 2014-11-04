package org.scribble2.parser.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.name.simple.RoleNode;
import org.scribble2.parser.AntlrModuleParser;

public class AntlrRoleDecl
{
	public static final int NAME_CHILD_INDEX = 0;

	public static RoleNode parseRoleDecl(AntlrModuleParser parser, CommonTree ct)
	{
		RoleNode name = AntlrSimpleName.toRoleNode(getNameChild(ct));
		//return new RoleDecl(ct, name);
		return name;
	}

	public static CommonTree getNameChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(NAME_CHILD_INDEX);
	}
}
