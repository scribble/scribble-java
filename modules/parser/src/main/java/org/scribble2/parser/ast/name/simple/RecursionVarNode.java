package org.scribble2.parser.ast.name.simple;

import org.antlr.runtime.Token;

public class RecursionVarNode extends SimpleNameNode
{
	public RecursionVarNode(Token t, String name)
	{
		super(t, name);
	}

	/*@Override
	public RecursionVar toName()
	{
		return new RecursionVar(this.identifier);
	}*/
}
