package org.scribble2.parser.ast.global;

import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.global.GParallel;
import org.scribble2.model.global.GProtocolBlock;
import org.scribble2.parser.ScribbleParser;
import org.scribble2.parser.util.Util;

public class AntlrGlobalParallel
{
	public static GParallel parseGlobalParallel(ScribbleParser parser, CommonTree ct)
	{
		List<GProtocolBlock> blocks = new LinkedList<>();
		for (CommonTree block : getBlockChildren(ct))
		{
			blocks.add((GProtocolBlock) parser.parse(block));
		}
		return new GParallel(blocks);
		//return ModelFactoryImpl.FACTORY.G;
	}

	public static final List<CommonTree> getBlockChildren(CommonTree ct)
	{
		return Util.toCommonTreeList(ct.getChildren());
	}
}
