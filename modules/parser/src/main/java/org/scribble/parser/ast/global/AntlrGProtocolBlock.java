package org.scribble.parser.ast.global;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.global.GInteractionSeq;
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.parser.ScribbleParser;

public class AntlrGProtocolBlock
{
	public static final int INTERACTIONSEQUENCE_CHILD_INDEX = 0;

	public static GProtocolBlock parseGProtocolBlock(ScribbleParser parser, CommonTree ct)
	{
		GInteractionSeq gis = (GInteractionSeq) parser.parse(getInteractionSequenceChild(ct));
		//return new GlobalProtocolBlock(gis);
		return AstFactoryImpl.FACTORY.GProtocolBlock(gis);
	}

	public static final CommonTree getInteractionSequenceChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(INTERACTIONSEQUENCE_CHILD_INDEX);
	}
}
