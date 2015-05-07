package org.scribble2.parser.ast.global;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.global.GInteractionNode;
import org.scribble2.model.global.GInteractionSeq;
import org.scribble2.parser.ScribbleParser;
import org.scribble2.parser.util.Util;

public class AntlrGlobalInteractionSequence
{
	public static GInteractionSeq parseGlobalInteractionSequence(ScribbleParser parser, CommonTree ct)
	{
		List<GInteractionNode> gis = new LinkedList<>();
		for (CommonTree gi : getInteractionChildren(ct))
		{
			gis.add((GInteractionNode) parser.parse(gi));
		}
		//return new GlobalInteractionSequence(gis);
		return ModelFactoryImpl.FACTORY.GlobalInteractionSequence(gis);
	}

	public static List<CommonTree> getInteractionChildren(CommonTree ct)
	{
		if (ct.getChildCount() == 0)
		{
			return Collections.emptyList();
		}
		return Util.toCommonTreeList(ct.getChildren());
	}
}
