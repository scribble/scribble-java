package org.scribble.sesstype.name;

import org.scribble.sesstype.kind.MessageIdKind;


public interface MessageId<M extends MessageIdKind> extends Name<M>
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
