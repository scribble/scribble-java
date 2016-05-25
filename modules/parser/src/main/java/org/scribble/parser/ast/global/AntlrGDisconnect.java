package org.scribble.parser.ast.global;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.global.GDisconnect;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.parser.ScribParser;
import org.scribble.parser.ast.name.AntlrSimpleName;

// Factor with AntlrGMessageTransfer/AntlrGConnect
public class AntlrGDisconnect
{
	public static final int SOURCE_CHILD_INDEX = 0;
	public static final int DESTINATION_CHILD_INDEX = 1;

	public static GDisconnect parseGDisconnect(ScribParser parser, CommonTree ct)
	{
		RoleNode src = AntlrSimpleName.toRoleNode(getSourceChild(ct));
		RoleNode dest = AntlrSimpleName.toRoleNode(getDestinationChild(ct));
		return AstFactoryImpl.FACTORY.GDisconnect(src, dest);
	}

	public static CommonTree getSourceChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(SOURCE_CHILD_INDEX);
	}

	public static CommonTree getDestinationChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(DESTINATION_CHILD_INDEX);
	}
}
