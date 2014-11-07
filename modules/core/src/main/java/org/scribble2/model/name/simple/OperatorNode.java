package org.scribble2.model.name.simple;

import org.scribble2.sesstype.name.Operator;



public class OperatorNode extends SimpleNameNode
{
	public static final String EMPTY_OPERATOR_IDENTIFIER = "";
	
	public OperatorNode(String identifier)
	{
		super(identifier);
	}

	@Override
	protected OperatorNode copy()
	{
		return new OperatorNode(this.identifier);
	}
	
	@Override
	public Operator toName()
	{
		if (this.identifier.equals(EMPTY_OPERATOR_IDENTIFIER))
		{
			return Operator.EMPTY_OPERATOR;
		}
		return new Operator(this.identifier);
	}
}
