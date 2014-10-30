package org.scribble2.parser.ast.global;

import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.global.GlobalParallel;
import org.scribble2.model.global.GlobalProtocolBlock;
import org.scribble2.parser.AntlrModuleParser;
import org.scribble2.parser.util.Util;

public class AntlrGlobalParallel
{
	public static GlobalParallel parseGlobalParallel(AntlrModuleParser parser, CommonTree ct)
	{
		List<GlobalProtocolBlock> blocks = new LinkedList<>();
		for (CommonTree block : getBlockChildren(ct))
		{
			blocks.add((GlobalProtocolBlock) parser.parse(block));
		}
		return new GlobalParallel(blocks);
	}

	public static final List<CommonTree> getBlockChildren(CommonTree ct)
	{
		return Util.toCommonTreeList(ct.getChildren());
	}
}
