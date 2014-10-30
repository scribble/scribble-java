package org.scribble2.foo.ast.name.simple;

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
