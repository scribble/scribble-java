package org.scribble2.sesstype;

import org.scribble2.sesstype.name.Scope;


// All sesstype message sigs should be scoped?
// A sig kind name: MessageSignature value (or parameter)
public interface ScopedMessage extends Message
{
	Scope getScope();
}
