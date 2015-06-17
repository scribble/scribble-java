package org.scribble.sesstype.name;

import org.scribble.sesstype.kind.AmbigKind;

public class AmbigName extends Name<AmbigKind> //SerializableSimpleName
{
	private static final long serialVersionUID = 1L;
	
	public static final AmbigName EMPTY_OPERATOR = new AmbigName();
	
	//public static final Operator TAU = new Operator("__tau");
	//public static final Operator ENTER = new Operator("__enter");
	//public static final Operator FORK = new Operator("__fork");
	//public static final Operator JOIN = new Operator("__join");

	protected AmbigName()
	{
		//super(KindEnum.OPERATOR);
		super(AmbigKind.KIND);
	}

	// FIXME: scope should be a subcomponent -- or in MessageSignature
	public AmbigName(String text)
	{
		//super(KindEnum.OPERATOR, text);
		super(AmbigKind.KIND, text);
	}
	
	// Bit hacky
	public MessageSigName toMessageSigName()
	{
		return new MessageSigName(getLastElement());
	}

	public DataType toDataType()
	{
		return new DataType(getLastElement());
	}
}
