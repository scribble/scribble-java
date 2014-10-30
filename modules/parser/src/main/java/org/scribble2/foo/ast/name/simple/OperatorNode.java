package org.scribble2.foo.ast.name.simple;

import org.antlr.runtime.Token;

public class OperatorNode extends SimpleNameNode
{
	public static final String EMPTY_OPERATOR_IDENTIFIER = "";
	
	public OperatorNode(Token t, String name)
	{
		super(t, name);
	}
	
	/*@Override
	public Operator toName()
	{
		if (this.identifier.equals(EMPTY_OPERATOR_IDENTIFIER))
		{
			return Operator.EMPTY_OPERATOR;
		}
		return new Operator(this.identifier);
	}*/
}
