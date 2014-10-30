package org.scribble2.parser.ast;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.Payload;
import org.scribble2.model.PayloadElement;
import org.scribble2.parser.AntlrModuleParser;
import org.scribble2.parser.util.Util;

public class AntlrPayload
{
	public static Payload parsePayload(AntlrModuleParser parser, CommonTree ct)
	{
		List<PayloadElement> pes = new LinkedList<>();
		for (CommonTree pe : getPayloadElements(ct))
		{
			pes.add((PayloadElement) parser.parse(pe));
		}
		return new Payload(pes);
	}

	public static final List<CommonTree> getPayloadElements(CommonTree ct)
	{
		if (ct.getChildCount() == 0)
		{
			return Collections.emptyList();
		}
		return Util.toCommonTreeList(ct.getChildren());
	}
}
