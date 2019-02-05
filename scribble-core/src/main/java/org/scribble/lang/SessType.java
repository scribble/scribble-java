package org.scribble.lang;

import org.scribble.type.kind.ProtocolKind;

// CHECKME: move to type.sess?
// Generic works to "specialise" G/L subclasses (and works with immutable pattern) -- cf. not supported by contravariant method parameter subtyping for getters/setters
public interface SessType<K extends ProtocolKind>
{
	// Call this using: them.canEquals(this) 
	boolean canEquals(Object o);
}
