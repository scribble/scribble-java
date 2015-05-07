package org.scribble2.parser.ast.global;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.global.GProtocolBlock;
import org.scribble2.model.global.GProtocolDef;
import org.scribble2.parser.ScribbleParser;

public class AntlrGlobalProtocolDefinition
{
	public static final int BLOCK_CHILD_INDEX = 0;

	public static GProtocolDef parseGlobalProtocolDefinition(ScribbleParser parser, CommonTree ct)
	{
		GProtocolBlock gpb = (GProtocolBlock) parser.parse(getBlockChild(ct));
		//return new GlobalProtocolDefinition(gpb);
		return ModelFactoryImpl.FACTORY.GlobalProtocolDefinition(gpb);
	}

	public static CommonTree getBlockChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(BLOCK_CHILD_INDEX);
	}
}
