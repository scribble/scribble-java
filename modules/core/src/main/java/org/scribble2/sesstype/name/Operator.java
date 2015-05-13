package org.scribble2.sesstype.name;

import org.scribble2.sesstype.kind.OperatorKind;

//public class Operator extends SimpleName //SerializableSimpleName
public class Operator extends Name<OperatorKind> implements MessageId //SerializableSimpleName
{
	private static final long serialVersionUID = 1L;
	
	public static final Operator EMPTY_OPERATOR = new Operator();
	
	//public static final Operator TAU = new Operator("__tau");
	//public static final Operator ENTER = new Operator("__enter");
	//public static final Operator FORK = new Operator("__fork");
	//public static final Operator JOIN = new Operator("__join");

	protected Operator()
	{
		//super(KindEnum.OPERATOR);
		super(OperatorKind.KIND);
	}

	// FIXME: scope should be a subcomponent -- or in MessageSignature
	public Operator(String text)
	{
		//super(KindEnum.OPERATOR, text);
		super(OperatorKind.KIND, text);
	}
}
