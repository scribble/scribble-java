package org.scribble.sesstype;

import org.scribble.sesstype.kind.SigKind;
import org.scribble.sesstype.name.MessageId;



// A sig kind name: MessageSignature value (or parameter)
public interface Message extends Arg<SigKind>
{
	//Scope getScope();  // Enforce this here? would need toMessage methods to take scope argument, e.g. for subprotocol signatures (don't want scopes there)
	
	//ScopedMessage toScopedMessage(Scope scope);
	
	MessageId getId();
}
