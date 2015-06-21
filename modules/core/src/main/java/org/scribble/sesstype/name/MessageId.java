package org.scribble.sesstype.name;

public interface MessageId
{
	default boolean isOp()
	{
		return false;
	}

	default boolean isMessageSigName()
	{
		return false;
	}
}
