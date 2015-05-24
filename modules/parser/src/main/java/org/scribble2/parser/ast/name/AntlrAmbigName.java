package org.scribble2.parser.ast.name;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.ModelFactory;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.name.simple.AmbigNameNode;

public class AntlrAmbigName
{
	public static AmbigNameNode toAmbigNameNode(CommonTree ct)
	{
		//return new AmbiguousNameNode(getName(ct));
		//return (AmbiguousNameNode) ModelFactoryImpl.FACTORY.SimpleNameNode(ModelFactory.SIMPLE_NAME.AMBIG, getName(ct));
		return ModelFactoryImpl.FACTORY.AmbiguousNameNode(getName(ct));
	}
	
	private static String getName(CommonTree ct)
	{
		return AntlrSimpleName.getName((CommonTree) ct.getChild(0));
	}
}
