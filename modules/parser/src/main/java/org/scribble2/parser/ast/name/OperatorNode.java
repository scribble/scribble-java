package scribble2.ast.name;

import org.antlr.runtime.tree.CommonTree;

import scribble2.sesstype.name.Operator;

public class OperatorNode extends PrimitiveNameNode
{
	public static final String EMPTY_OPERATOR_IDENTIFIER = "";
	
	public OperatorNode(CommonTree ct, String name)
	{
		super(ct, name);
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
