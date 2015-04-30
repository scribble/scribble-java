package org.scribble2.parser.ast.global;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.global.GlobalProtocolBlock;
import org.scribble2.model.global.GlobalRecursion;
import org.scribble2.model.name.simple.RecursionVarNode;
import org.scribble2.parser.ScribbleParser;
import org.scribble2.parser.ast.name.AntlrSimpleName;

public class AntlrGlobalRecursion
{
	public static final int RECURSIONVAR_CHILD_INDEX = 0;
	public static final int BLOCK_CHILD_INDEX = 1;

	public static GlobalRecursion parseGlobalRecursion(ScribbleParser parser, CommonTree ct)
	{
		RecursionVarNode recvar = AntlrSimpleName.toRecursionVarNode(getRecursionVarChild(ct));
		GlobalProtocolBlock block = (GlobalProtocolBlock) parser.parse(getBlockChild(ct));
		//return new GlobalRecursion(lab, block);
		return ModelFactoryImpl.FACTORY.GlobalRecursion(recvar, block);
	}

	public static final CommonTree getRecursionVarChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(RECURSIONVAR_CHILD_INDEX);
	}

	public static final CommonTree getBlockChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(BLOCK_CHILD_INDEX);
	}
}
