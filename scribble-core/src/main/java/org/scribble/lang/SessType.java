package org.scribble.lang;

import org.scribble.ast.ScribNodeBase;
import org.scribble.type.kind.ProtocolKind;

// CHECKME: move to type.sess?
// Generic works to "specialise" G/L subclasses (and works with immutable pattern) -- cf. not supported by contravariant method parameter subtyping for getters/setters
public interface SessType<K extends ProtocolKind>
{
	boolean hasSource();  // i.e., was parsed
	ScribNodeBase getSource();  // Pre: hasSource
	
	// Call this using: them.canEquals(this) 
	boolean canEquals(Object o);
}
