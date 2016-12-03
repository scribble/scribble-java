package org.scribble.parser.ast.name;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.name.simple.AmbigNameNode;

public class AntlrAmbigName
{
	public static AmbigNameNode toAmbigNameNode(CommonTree ct)
	{
		return AstFactoryImpl.FACTORY.AmbiguousNameNode(getNameChild(ct), getName(ct));
	}

	private static CommonTree getNameChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(0);
	}
	
	private static String getName(CommonTree ct)
	{
		return AntlrSimpleName.getName(getNameChild(ct));
	}
}
