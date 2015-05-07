package org.scribble2.parser.ast.global;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.global.GInteractionSeq;
import org.scribble2.model.global.GProtocolBlock;
import org.scribble2.parser.ScribbleParser;

public class AntlrGlobalProtocolBlock
{
	public static final int INTERACTIONSEQUENCE_CHILD_INDEX = 0;

	public static GProtocolBlock parseGlobalProtocolBlock(ScribbleParser parser, CommonTree ct)
	{
		GInteractionSeq gis = (GInteractionSeq) parser.parse(getInteractionSequenceChild(ct));
		//return new GlobalProtocolBlock(gis);
		return ModelFactoryImpl.FACTORY.GlobalProtocolBlock(gis);
	}

	public static final CommonTree getInteractionSequenceChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(INTERACTIONSEQUENCE_CHILD_INDEX);
	}
}
