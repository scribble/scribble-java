package org.scribble.parser.ast.global;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.global.GContinue;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.parser.ScribbleParser;
import org.scribble.parser.ast.name.AntlrSimpleName;

public class AntlrGContinue
{
	public static final int LABEL_CHILD_INDEX = 0;

	public static GContinue parseGContinue(ScribbleParser parser, CommonTree ct)
	{
		RecVarNode recvar = AntlrSimpleName.toRecVarNode(getRecVarChild(ct));
		//return new GlobalContinue(recvar);
		return AstFactoryImpl.FACTORY.GContinue(recvar);
	}

	public static final CommonTree getRecVarChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(LABEL_CHILD_INDEX);
	}
}
