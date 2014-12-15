package org.scribble2.sesstype;

import org.scribble2.sesstype.name.Scope;



// A sig kind name: MessageSignature value (or parameter)
public interface Message extends Argument
{
	//Scope getScope();  // Enforce this here? would need toMessage methods to take scope argument, e.g. for subprotocol signatures (don't want scopes there)
	
	ScopedMessage toScopedMessage(Scope scope);
}
