package org.scribble.parser.ast.global;

import java.util.List;
import java.util.stream.Collectors;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.global.GParallel;
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.parser.ScribbleParser;
import org.scribble.parser.util.Util;

public class AntlrGParallel
{
	public static GParallel parseGParallel(ScribbleParser parser, CommonTree ct)
	{
		List<GProtocolBlock> blocks = 
				getBlockChildren(ct).stream().map((b) -> (GProtocolBlock) parser.parse(b)).collect(Collectors.toList());
		return new GParallel(blocks);
	}

	public static final List<CommonTree> getBlockChildren(CommonTree ct)
	{
		return Util.toCommonTreeList(ct.getChildren());
	}
}
