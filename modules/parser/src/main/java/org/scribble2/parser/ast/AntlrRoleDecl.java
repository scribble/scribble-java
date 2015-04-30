package org.scribble2.parser.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.RoleDecl;
import org.scribble2.model.name.simple.RoleNode;
import org.scribble2.parser.ScribbleParser;
import org.scribble2.parser.ast.name.AntlrSimpleName;

public class AntlrRoleDecl
{
	public static final int NAME_CHILD_INDEX = 0;

	//public static RoleNode parseRoleDecl(AntlrModuleParser parser, CommonTree ct)
	public static RoleDecl parseRoleDecl(ScribbleParser parser, CommonTree ct)
	{
		RoleNode name = AntlrSimpleName.toRoleNode(getNameChild(ct));
		//return new RoleDecl(ct, name);
		//return name;
		return ModelFactoryImpl.FACTORY.RoleDecl(name);
	}

	public static CommonTree getNameChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(NAME_CHILD_INDEX);
	}
}
