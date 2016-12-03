package org.scribble.parser.ast.global;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.global.GInteractionSeq;
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.parser.ScribParser;
import org.scribble.util.ScribParserException;

public class AntlrGProtocolBlock
{
	public static final int INTERACTIONSEQUENCE_CHILD_INDEX = 0;

	public static GProtocolBlock parseGProtocolBlock(ScribParser parser, CommonTree ct) throws ScribParserException
	{
		GInteractionSeq gis = (GInteractionSeq) parser.parse(getInteractionSequenceChild(ct));
		return AstFactoryImpl.FACTORY.GProtocolBlock(ct, gis);
	}

	public static final CommonTree getInteractionSequenceChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(INTERACTIONSEQUENCE_CHILD_INDEX);
	}
}
