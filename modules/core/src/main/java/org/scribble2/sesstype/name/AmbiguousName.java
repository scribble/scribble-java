package org.scribble2.sesstype.name;

import org.scribble2.sesstype.kind.AmbiguousKind;

public class AmbiguousName extends Name<AmbiguousKind> //SerializableSimpleName
{
	private static final long serialVersionUID = 1L;
	
	public static final AmbiguousName EMPTY_OPERATOR = new AmbiguousName();
	
	//public static final Operator TAU = new Operator("__tau");
	//public static final Operator ENTER = new Operator("__enter");
	//public static final Operator FORK = new Operator("__fork");
	//public static final Operator JOIN = new Operator("__join");

	protected AmbiguousName()
	{
		//super(KindEnum.OPERATOR);
		super(AmbiguousKind.KIND);
	}

	// FIXME: scope should be a subcomponent -- or in MessageSignature
	public AmbiguousName(String text)
	{
		//super(KindEnum.OPERATOR, text);
		super(AmbiguousKind.KIND, text);
	}
}
