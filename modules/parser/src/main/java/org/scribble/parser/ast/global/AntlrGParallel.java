package org.scribble.parser.ast.global;

import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.global.GParallel;
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.parser.ScribParser;
import org.scribble.parser.util.ScribParserUtil;
import org.scribble.util.ScribParserException;

public class AntlrGParallel
{
	public static GParallel parseGParallel(ScribParser parser, CommonTree ct) throws ScribParserException
	{
		/*List<GProtocolBlock> blocks = 
				getBlockChildren(ct).stream().map((b) -> (GProtocolBlock) parser.parse(b)).collect(Collectors.toList());*/
		List<GProtocolBlock> blocks = new LinkedList<>();
		for (CommonTree b : getBlockChildren(ct))
		{
			blocks.add((GProtocolBlock) parser.parse(b));
		}
		//return new GParallel(blocks);
		return null;
	}

	public static final List<CommonTree> getBlockChildren(CommonTree ct)
	{
		return ScribParserUtil.toCommonTreeList(ct.getChildren());
	}
}
