package org.scribble.sesstype;

import org.scribble.sesstype.kind.MessageIdKind;
import org.scribble.sesstype.kind.SigKind;
import org.scribble.sesstype.name.MessageId;



// A sig kind name: MessageSignature value (or parameter)
public interface Message extends Arg<SigKind>
{
	MessageId<? extends MessageIdKind> getId();
}
