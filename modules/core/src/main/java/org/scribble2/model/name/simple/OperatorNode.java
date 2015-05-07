package org.scribble2.model.name.simple;

import org.scribble2.sesstype.kind.OperatorKind;
import org.scribble2.sesstype.name.Operator;



//public class OperatorNode extends SimpleNameNode
public class OperatorNode extends SimpleNameNode<Operator, OperatorKind>
{
	public static final String EMPTY_OPERATOR_IDENTIFIER = "";
	
	public OperatorNode(String identifier)
	{
		super(identifier);
	}

	/*@Override
	protected OperatorNode reconstruct(String identifier)
	{
		ModelDel del = del();  // Default delegate assigned in ModelFactoryImpl for all simple names
		OperatorNode on = new OperatorNode(identifier);
		on = (OperatorNode) on.del(del);
		return on;
	}*/

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
