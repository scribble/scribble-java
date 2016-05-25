package org.scribble.parser.ast.global;

import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.ast.global.GProtocolDef;
import org.scribble.ast.global.GProtocolHeader;
import org.scribble.parser.ScribParser;

public class AntlrGProtocolDecl
{
	public static final int HEADER_CHILD_INDEX = 0;
	public static final int BODY_CHILD_INDEX = 1;
	public static final int EXPLICIT_CONNECTIONS_FLAG_INDEX = 2;

	public static GProtocolDecl parseGPrototocolDecl(ScribParser parser, CommonTree ct)
	{
		GProtocolHeader header = (GProtocolHeader) parser.parse(getHeaderChild(ct));
		GProtocolDef def = (GProtocolDef) parser.parse(getBodyChild(ct));
		List<GProtocolDecl.Modifiers> modifiers = new LinkedList<>();
		if (isExplicitConnections(ct))
		{
			modifiers.add(GProtocolDecl.Modifiers.EXPLICIT);
		}
		return AstFactoryImpl.FACTORY.GProtocolDecl(modifiers, header, def);
	}

	public static CommonTree getHeaderChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(HEADER_CHILD_INDEX);
	}

	public static CommonTree getBodyChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(BODY_CHILD_INDEX);
	}

	public static boolean isExplicitConnections(CommonTree ct)
	{
		return ct.getChildCount() > EXPLICIT_CONNECTIONS_FLAG_INDEX;
	}
}
