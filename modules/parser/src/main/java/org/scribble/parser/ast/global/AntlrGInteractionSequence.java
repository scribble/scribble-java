package org.scribble.parser.ast.global;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.global.GInteractionNode;
import org.scribble.ast.global.GInteractionSeq;
import org.scribble.parser.ScribParser;
import org.scribble.parser.util.ScribParserUtil;

public class AntlrGInteractionSequence
{
	public static GInteractionSeq parseGInteractionSequence(ScribParser parser, CommonTree ct)
	{
		List<GInteractionNode> gis =
				getInteractionChildren(ct).stream().map((gi) -> (GInteractionNode) parser.parse(gi)).collect(Collectors.toList());
		return AstFactoryImpl.FACTORY.GInteractionSeq(gis);
	}

	public static List<CommonTree> getInteractionChildren(CommonTree ct)
	{
		return (ct.getChildCount() == 0)
				? Collections.emptyList()
				: ScribParserUtil.toCommonTreeList(ct.getChildren());
	}
}
