package org.scribble2.parser.ast.global;

import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.global.GInterrupt;
import org.scribble2.model.global.GInterruptible;
import org.scribble2.model.global.GProtocolBlock;
import org.scribble2.model.name.simple.ScopeNode;
import org.scribble2.parser.ScribbleParser;
import org.scribble2.parser.ast.name.AntlrSimpleName;
import org.scribble2.parser.util.Util;

public class AntlrGlobalInterruptible
{
	//public static final String NO_SCOPE = "NO_SCOPE";

	public static final int SCOPE_CHILD_INDEX = 0;
	public static final int BLOCK_CHILD_INDEX = 1;
	public static final int INTERRUPT_CHILDREN_START_INDEX = 2;

	public static GInterruptible parseGlobalInterruptible(ScribbleParser parser, CommonTree ct)
	{
		GProtocolBlock block = (GProtocolBlock) parser.parse(getBlockChild(ct));
		//List<GlobalInterrupt> interrs = new LinkedList<>();
		List<GInterrupt> interrs = new LinkedList<>();
		for (CommonTree interr : getInterruptChildren(ct))
		{
			interrs.add((GInterrupt) parser.parse(interr));
		}
		if (isScopeImplicit(ct))
		{
			return new GInterruptible(block, interrs);
		}
		ScopeNode scope = AntlrSimpleName.toScopeNode(getScopeChild(ct));
		return new GInterruptible(scope, block, interrs);
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
		List<? extends Object> children = ct.getChildren();
		return Util.toCommonTreeList(children.subList(INTERRUPT_CHILDREN_START_INDEX, children.size()));
	}
}
