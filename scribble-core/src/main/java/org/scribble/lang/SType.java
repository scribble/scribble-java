package org.scribble.lang;

import java.util.Set;

import org.scribble.ast.ProtocolKindNode;
import org.scribble.type.kind.ProtocolKind;
import org.scribble.type.name.Role;

// CHECKME: move to type.sess?
// Generic works to "specialise" G/L subclasses (and works with immutable pattern) -- cf. not supported by contravariant method parameter subtyping for getters/setters
public interface SType<K extends ProtocolKind>
{
	boolean hasSource();  // i.e., was parsed
	ProtocolKindNode<K> getSource();  // Pre: hasSource
	
	// Unsupported for Protocol/Do
	Set<Role> getRoles();

	SType<K> substitute(Substitutions subs);
	
	// Top-level call should be on a GProtocol with an empty stack -- XXX
	// stack is treated mutably
	// Do stack.push to push the next sig onto the stack
	//
	// Pre: stack.peek gives call-site sig, i.e., call-site roles/args
	// CHECKME: OK to (re-)use GTypeTranslator for inlining?
	// Stack not "internalised", must be "manually" managed -- cf., SubprotocolVisitor
	//SessType<K> getInlined(GTypeTranslator t, Deque<SubprotoSig> stack);  // FIXME: generalise for locals
	SType<K> getInlined(STypeInliner i);
	
	// Unsupported for Do
	// FIXME: repeat recvar names, including non-shadowing (e.g., choice cases)
	SType<K> unfoldAllOnce(STypeUnfolder<K> u);
	
	// Call this using: them.canEquals(this) 
	boolean canEquals(Object o);
}
