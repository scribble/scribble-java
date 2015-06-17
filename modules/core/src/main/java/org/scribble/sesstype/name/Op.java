package org.scribble.sesstype.name;

import org.scribble.sesstype.kind.OpKind;

//public class Operator extends SimpleName //SerializableSimpleName
public class Op extends Name<OpKind> implements MessageId //SerializableSimpleName
{
	private static final long serialVersionUID = 1L;
	
	public static final Op EMPTY_OPERATOR = new Op();
	
	//public static final Operator TAU = new Operator("__tau");
	//public static final Operator ENTER = new Operator("__enter");
	//public static final Operator FORK = new Operator("__fork");
	//public static final Operator JOIN = new Operator("__join");

	protected Op()
	{
		//super(KindEnum.OPERATOR);
		super(OpKind.KIND);
	}

	// FIXME: scope should be a subcomponent -- or in MessageSignature
	public Op(String text)
	{
		//super(KindEnum.OPERATOR, text);
		super(OpKind.KIND, text);
	}

	@Override
	public boolean isOp()
	{
		return true;
	}

	@Override
	public boolean isMessageSigName()
	{
		return false;
	}

	/*@Override
	public String getId()
	{
		return this.toString();
	}*/
}
