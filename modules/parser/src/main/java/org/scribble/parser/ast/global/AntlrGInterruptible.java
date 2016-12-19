package org.scribble.parser.ast.global;

import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.global.GInterruptible;
import org.scribble.parser.ScribParser;
import org.scribble.parser.ast.name.AntlrSimpleName;
import org.scribble.parser.util.ScribParserUtil;
import org.scribble.util.ScribParserException;

public class AntlrGInterruptible
{
	public static final int SCOPE_CHILD_INDEX = 0;
	public static final int BLOCK_CHILD_INDEX = 1;
	public static final int INTERRUPT_CHILDREN_START_INDEX = 2;

	public static GInterruptible parseGInterruptible(ScribParser parser, CommonTree ct) throws ScribParserException
	{
		/*GProtocolBlock block = (GProtocolBlock) parser.parse(getBlockChild(ct));
		/*List<GInterrupt> interrs = 
			getInterruptChildren(ct).stream().map((interr) -> (GInterrupt) parser.parse(interr)).collect(Collectors.toList());
		* /
		List<GInterrupt> interrs = new LinkedList<>();
		for (CommonTree interr : getInterruptChildren(ct))
		{
			interrs.add((GInterrupt) parser.parse(interr));
		}
		if (isScopeImplicit(ct))
		{
			//return new GInterruptible(block, interrs);
			return null;
		}
		ScopeNode scope = AntlrSimpleName.toScopeNode(getScopeChild(ct));
		//return new GInterruptible(scope, block, interrs);*/
		return null;
	}
	
	public static boolean isScopeImplicit(CommonTree ct)
	{
		return AntlrSimpleName.toScopeNode(ct) == null;
	}

	public static CommonTree getScopeChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(SCOPE_CHILD_INDEX);
	}

	public static CommonTree getBlockChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(BLOCK_CHILD_INDEX);
	}

	public static List<CommonTree> getInterruptChildren(CommonTree ct)
	{
		List<?> children = ct.getChildren();
		return ScribParserUtil.toCommonTreeList(children.subList(INTERRUPT_CHILDREN_START_INDEX, children.size()));
	}
}
