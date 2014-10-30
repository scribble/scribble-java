package org.scribble2.model.name.simple;


public class OperatorNode extends SimpleNameNode
{
	public static final String EMPTY_OPERATOR_IDENTIFIER = "";
	
	public OperatorNode(String name)
	{
		super(name);
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
