package org.scribble2.parser.ast.global;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.global.GContinue;
import org.scribble2.model.name.simple.RecVarNode;
import org.scribble2.parser.ScribbleParser;
import org.scribble2.parser.ast.name.AntlrSimpleName;

public class AntlrGContinue
{
	public static final int LABEL_CHILD_INDEX = 0;

	public static GContinue parseGContinue(ScribbleParser parser, CommonTree ct)
	{
		RecVarNode recvar = AntlrSimpleName.toRecVarNode(getRecVarChild(ct));
		//return new GlobalContinue(recvar);
		return ModelFactoryImpl.FACTORY.GContinue(recvar);
	}

	public static final CommonTree getRecVarChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(LABEL_CHILD_INDEX);
	}
}
