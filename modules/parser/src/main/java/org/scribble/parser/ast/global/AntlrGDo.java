package org.scribble.parser.ast.global;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.NonRoleArgList;
import org.scribble.ast.RoleArgList;
import org.scribble.ast.global.GDo;
import org.scribble.ast.name.qualified.GProtocolNameNode;
import org.scribble.parser.ScribParser;
import org.scribble.parser.ast.name.AntlrQualifiedName;

public class AntlrGDo
{
	public static final int MEMBERNAME_CHILD_INDEX = 0;
	public static final int ARGUMENTLIST_CHILD_INDEX = 1;
	public static final int ROLEINSTANTIATIONLIST_CHILD_INDEX = 2;

	public static GDo parseGDo(ScribParser parser, CommonTree ct)
	{
		RoleArgList ril = (RoleArgList) parser.parse(getRoleArgListChild(ct));
		NonRoleArgList al = (NonRoleArgList) parser.parse(getNonRoleArgListChild(ct));
		GProtocolNameNode pnn = AntlrQualifiedName.toGProtocolNameNode(getProtocolNameChild(ct));
		return AstFactoryImpl.FACTORY.GDo(ril, al, pnn);
	}
	
	public static CommonTree getProtocolNameChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(MEMBERNAME_CHILD_INDEX);
	}

	public static CommonTree getNonRoleArgListChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(ARGUMENTLIST_CHILD_INDEX);
	}

	public static CommonTree getRoleArgListChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(ROLEINSTANTIATIONLIST_CHILD_INDEX);
	}
}
