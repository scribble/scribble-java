package org.scribble2.parser.ast.global;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.global.GlobalContinue;
import org.scribble2.model.name.SimpleKindedNameNode;
import org.scribble2.parser.ScribbleParser;
import org.scribble2.parser.ast.name.AntlrSimpleName;
import org.scribble2.sesstype.kind.RecursionVarKind;

public class AntlrGlobalContinue
{
	public static final int LABEL_CHILD_INDEX = 0;

	public static GlobalContinue parseGlobalContinue(ScribbleParser parser, CommonTree ct)
	{
		//RecursionVarNode recvar = AntlrSimpleName.toRecursionVarNode(getRecursionVarChild(ct));
		SimpleKindedNameNode<RecursionVarKind> recvar = AntlrSimpleName.toRecursionVarNode(getRecursionVarChild(ct));
		//return new GlobalContinue(recvar);
		return ModelFactoryImpl.FACTORY.GlobalContinue(recvar);
	}

	public static final CommonTree getRecursionVarChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(LABEL_CHILD_INDEX);
	}
}
