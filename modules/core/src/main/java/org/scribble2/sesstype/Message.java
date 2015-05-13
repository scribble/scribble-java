package org.scribble2.sesstype;

import org.scribble2.sesstype.kind.SigKind;
import org.scribble2.sesstype.name.MessageId;



// A sig kind name: MessageSignature value (or parameter)
public interface Message extends Argument<SigKind>
{
	//Scope getScope();  // Enforce this here? would need toMessage methods to take scope argument, e.g. for subprotocol signatures (don't want scopes there)
	
	//ScopedMessage toScopedMessage(Scope scope);
	
	MessageId getId();
}
