package org.scribble2.sesstype.name;

public class Operator extends SimpleName //SerializableSimpleName
{
	private static final long serialVersionUID = 1L;
	
	public static final Operator EMPTY_OPERATOR = new Operator();
	
	//public static final Operator TAU = new Operator("__tau");
	//public static final Operator ENTER = new Operator("__enter");
	//public static final Operator FORK = new Operator("__fork");
	//public static final Operator JOIN = new Operator("__join");

	protected Operator()
	{
		super(KindEnum.OPERATOR);
	}

	// FIXME: scope should be a subcomponent -- or in MessageSignature
	public Operator(String text)
	{
		super(KindEnum.OPERATOR, text);
	}
}
