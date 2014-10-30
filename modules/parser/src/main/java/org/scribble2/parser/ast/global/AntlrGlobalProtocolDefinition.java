package org.scribble2.parser.ast.global;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.global.GlobalProtocolBlock;
import org.scribble2.model.global.GlobalProtocolDefinition;
import org.scribble2.parser.AntlrModuleParser;

public class AntlrGlobalProtocolDefinition
{
	public static final int BLOCK_CHILD_INDEX = 0;

	public static GlobalProtocolDefinition parseGlobalProtocolDefinition(AntlrModuleParser parser, CommonTree ct)
	{
		GlobalProtocolBlock gpb = (GlobalProtocolBlock) parser.parse(getBlockChild(ct));
		return new GlobalProtocolDefinition(gpb);
	}

	public static CommonTree getBlockChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(BLOCK_CHILD_INDEX);
	}
}
