package org.scribble.lang;

import java.util.Deque;

import org.scribble.ast.ProtocolKindNode;
import org.scribble.lang.global.GTypeTranslator;
import org.scribble.type.SubprotoSig;
import org.scribble.type.kind.ProtocolKind;
import org.scribble.type.name.Role;

// CHECKME: move to type.sess?
// Generic works to "specialise" G/L subclasses (and works with immutable pattern) -- cf. not supported by contravariant method parameter subtyping for getters/setters
public interface SessType<K extends ProtocolKind>
{
	boolean hasSource();  // i.e., was parsed
	ProtocolKindNode<K> getSource();  // Pre: hasSource

	SessType<K> substitute(Substitutions<Role> role);
	
	// Top-level call should be on a GProtocol with an empty stack -- XXX
	// stack is treated mutably
	// Do stack.push to push the next sig onto the stack
	//
	// Pre: stack.peek gives call-site sig, i.e., call-site roles/args
	// CHECKME: OK to (re-)use GTypeTranslator for inlining?
	SessType<K> getInlined(GTypeTranslator t, Deque<SubprotoSig> stack);  // FIXME: generalise for locals
	
	// Call this using: them.canEquals(this) 
	boolean canEquals(Object o);
}
