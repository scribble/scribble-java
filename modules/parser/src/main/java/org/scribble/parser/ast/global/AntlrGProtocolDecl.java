package org.scribble.parser.ast.global;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.ast.global.GProtocolDef;
import org.scribble.ast.global.GProtocolHeader;
import org.scribble.parser.ScribbleParser;

public class AntlrGProtocolDecl
{
	/*public static final int NAME_CHILD_INDEX = 0;
	public static final int PARAMETERDECLLIST_CHILD_INDEX = 1;
	public static final int ROLEDECLLIST_CHILD_INDEX = 2;*/
	public static final int HEADER_CHILD_INDEX = 0;
	public static final int BODY_CHILD_INDEX = 1;

	public static GProtocolDecl parseGPrototocolDecl(ScribbleParser parser, CommonTree ct)
	{
		/*SimpleProtocolNameNode name = AntlrSimpleName.toSimpleProtocolNameNode(getNameChild(ct));
		RoleDeclList rdl = (RoleDeclList) parser.parse(getRoleDeclListChild(ct));
		ParameterDeclList pdl = (ParameterDeclList) parser.parse(getParameterDeclListChild(ct));*/
		GProtocolHeader header = (GProtocolHeader) parser.parse(getHeaderChild(ct));
		GProtocolDef def = (GProtocolDef) parser.parse(getBodyChild(ct));
		//return new GlobalProtocolDecl(header, def);
		return AstFactoryImpl.FACTORY.GProtocolDecl(header, def);
	}

	/*public static CommonTree getNameChild(CommonTree ct)
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
	}*/

	public static CommonTree getHeaderChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(HEADER_CHILD_INDEX);
	}

	public static CommonTree getBodyChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(BODY_CHILD_INDEX);
	}
	
	/*public static CommonTree getModuleParent(CommonTree ct)
	{
		return (CommonTree) ct.getParent();
	}*/
}
