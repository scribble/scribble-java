package org.scribble2.parser.ast.global;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.global.GlobalInteractionSequence;
import org.scribble2.model.global.GlobalProtocolBlock;
import org.scribble2.parser.AntlrModuleParser;

public class AntlrGlobalProtocolBlock
{
	public static final int INTERACTIONSEQUENCE_CHILD_INDEX = 0;

	public static GlobalProtocolBlock parseGlobalProtocolBlock(AntlrModuleParser parser, CommonTree ct)
	{
		GlobalInteractionSequence gis = (GlobalInteractionSequence) parser.parse(getInteractionSequenceChild(ct));
		//return new GlobalProtocolBlock(gis);
		return ModelFactoryImpl.FACTORY.GlobalProtocolBlock(gis);
	}

	public static final CommonTree getInteractionSequenceChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(INTERACTIONSEQUENCE_CHILD_INDEX);
	}
}
