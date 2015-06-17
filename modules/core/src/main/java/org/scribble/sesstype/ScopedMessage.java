package org.scribble.sesstype;

import org.scribble.sesstype.name.Scope;


// All sesstype message sigs should be scoped?
// A sig kind name: MessageSignature value (or parameter)
@Deprecated
public interface ScopedMessage extends Message
{
	Scope getScope();
}
