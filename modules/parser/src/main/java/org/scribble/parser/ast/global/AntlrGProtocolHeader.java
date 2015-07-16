package org.scribble.parser.ast.global;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.NonRoleParamDeclList;
import org.scribble.ast.RoleDeclList;
import org.scribble.ast.global.GProtocolHeader;
import org.scribble.ast.name.qualified.GProtocolNameNode;
import org.scribble.parser.ScribParser;
import org.scribble.parser.ast.name.AntlrSimpleName;

public class AntlrGProtocolHeader
{
	public static final int NAME_CHILD_INDEX = 0;
	public static final int PARAMETERDECLLIST_CHILD_INDEX = 1;
	public static final int ROLEDECLLIST_CHILD_INDEX = 2;

	public static GProtocolHeader parseGProtocolHeader(ScribParser parser, CommonTree ct)
	{
		GProtocolNameNode name = AntlrSimpleName.toGProtocolNameNode(getNameChild(ct));
		RoleDeclList rdl = (RoleDeclList) parser.parse(getRoleDeclListChild(ct));
		NonRoleParamDeclList pdl = (NonRoleParamDeclList) parser.parse(getParamDeclListChild(ct));
		return AstFactoryImpl.FACTORY.GProtocolHeader(name, rdl, pdl);
	}

	public static CommonTree getNameChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(NAME_CHILD_INDEX);
	}
	
	public static CommonTree getRoleDeclListChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(ROLEDECLLIST_CHILD_INDEX);
	}

	public static CommonTree getParamDeclListChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(PARAMETERDECLLIST_CHILD_INDEX);
	}
}
