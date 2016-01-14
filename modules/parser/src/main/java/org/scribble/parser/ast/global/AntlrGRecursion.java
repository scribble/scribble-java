package org.scribble.parser.ast.global;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.ast.global.GRecursion;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.parser.ScribParser;
import org.scribble.parser.ast.name.AntlrSimpleName;

public class AntlrGRecursion
{
	public static final int RECURSIONVAR_CHILD_INDEX = 0;
	public static final int BLOCK_CHILD_INDEX = 1;

	public static GRecursion parseGRecursion(ScribParser parser, CommonTree ct)
	{
		RecVarNode recvar = AntlrSimpleName.toRecVarNode(getRecVarChild(ct));
		GProtocolBlock block = (GProtocolBlock) parser.parse(getBlockChild(ct));
		return AstFactoryImpl.FACTORY.GRecursion(recvar, block);
	}

	public static final CommonTree getRecVarChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(RECURSIONVAR_CHILD_INDEX);
	}

	public static final CommonTree getBlockChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(BLOCK_CHILD_INDEX);
	}
}
