package org.scribble2.parser.ast.name;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.name.AmbiguousKindedNameNode;

public class AntlrAmbiguousName
{
	//public static AmbiguousNameNode toAmbiguousNameNode(CommonTree ct)
	//public static SimpleKindedNameNode<AmbiguousKind> toAmbiguousNameNode(CommonTree ct)
	public static AmbiguousKindedNameNode toAmbiguousNameNode(CommonTree ct)
	{
		//return new AmbiguousNameNode(getName(ct));
		//return (AmbiguousNameNode) ModelFactoryImpl.FACTORY.SimpleNameNode(ModelFactory.SIMPLE_NAME.AMBIG, getName(ct));
		//return ModelFactoryImpl.FACTORY.SimpleKindedNameNode(AmbiguousKind.KIND, getName(ct));
		return ModelFactoryImpl.FACTORY.AmbiguousKindedNameNode(getName(ct));
	}
	
	private static String getName(CommonTree ct)
	{
		return AntlrSimpleName.getName((CommonTree) ct.getChild(0));
	}
}
