package org.scribble.parser.ast.name;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.name.simple.AmbigNameNode;

public class AntlrAmbigName
{
	public static AmbigNameNode toAmbigNameNode(CommonTree ct)
	{
		return AstFactoryImpl.FACTORY.AmbiguousNameNode(getName(ct));
	}
	
	private static String getName(CommonTree ct)
	{
		return AntlrSimpleName.getName((CommonTree) ct.getChild(0));
	}
}
