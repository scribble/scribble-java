package org.scribble.parser.ast.global;

import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.global.GParallel;
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.parser.ScribbleParser;
import org.scribble.parser.util.Util;

public class AntlrGParallel
{
	public static GParallel parseGParallel(ScribbleParser parser, CommonTree ct)
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
