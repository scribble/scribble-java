package org.scribble2.parser.ast.global;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.global.GlobalContinue;
import org.scribble2.model.name.simple.RecursionVarNode;
import org.scribble2.parser.AntlrModuleParser;
import org.scribble2.parser.ast.AntlrSimpleName;

public class AntlrGlobalContinue
{
	public static final int LABEL_CHILD_INDEX = 0;

	public static GlobalContinue parseGlobalContinue(AntlrModuleParser parser, CommonTree ct)
	{
		RecursionVarNode recvar = AntlrSimpleName.toRecursionVarNode(getRecursionVarChild(ct));
		return new GlobalContinue(recvar);
	}

	public static final CommonTree getRecursionVarChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(LABEL_CHILD_INDEX);
	}
}
