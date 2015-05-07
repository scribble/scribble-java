package org.scribble2.parser.ast.global;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.ParamDeclList;
import org.scribble2.model.RoleDeclList;
import org.scribble2.model.global.GProtocolHeader;
import org.scribble2.model.name.qualified.SimpleProtocolNameNode;
import org.scribble2.parser.ScribbleParser;
import org.scribble2.parser.ast.name.AntlrSimpleName;

public class AntlrGlobalProtocolHeader
{
	public static final int NAME_CHILD_INDEX = 0;
	public static final int PARAMETERDECLLIST_CHILD_INDEX = 1;
	public static final int ROLEDECLLIST_CHILD_INDEX = 2;

	public static GProtocolHeader parseGlobalProtocolHeader(ScribbleParser parser, CommonTree ct)
	{
		SimpleProtocolNameNode name = AntlrSimpleName.toSimpleProtocolNameNode(getNameChild(ct));
		RoleDeclList rdl = (RoleDeclList) parser.parse(getRoleDeclListChild(ct));
		ParamDeclList pdl = (ParamDeclList) parser.parse(getParameterDeclListChild(ct));
		//return new GlobalProtocolHeader(name, rdl, pdl);
		return ModelFactoryImpl.FACTORY.GlobalProtocolHeader(name, rdl, pdl);
	}

	public static CommonTree getNameChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(NAME_CHILD_INDEX);
	}
	
	public static CommonTree getRoleDeclListChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(ROLEDECLLIST_CHILD_INDEX);
	}

	public static CommonTree getParameterDeclListChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(PARAMETERDECLLIST_CHILD_INDEX);
	}
}
