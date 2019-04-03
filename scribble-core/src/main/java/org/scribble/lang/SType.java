/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.scribble.lang;

import java.util.List;
import java.util.Set;

import org.scribble.ast.ProtocolKindNode;
import org.scribble.type.kind.ProtocolKind;
import org.scribble.type.name.MemberName;
import org.scribble.type.name.ProtocolName;
import org.scribble.type.name.RecVar;
import org.scribble.type.name.Role;

// CHECKME: move to type.sess?
// Generic works to "specialise" G/L subclasses (and works with immutable pattern) -- cf. not supported by contravariant method parameter subtyping for getters/setters
public interface SType<K extends ProtocolKind>  // CHECKME: consider adding B extends Seq<K> here
{
	boolean hasSource();  // i.e., was parsed
	ProtocolKindNode<K> getSource();  // Pre: hasSource
	
	// Unsupported for Protocol/Do
	Set<Role> getRoles();

	SType<K> substitute(Substitutions subs);
	
	Set<RecVar> getRecVars();
	SType<K> pruneRecs();  // Assumes no shadowing (e.g., use after inlining recvar disamb)
	
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
	
	// Resulting Lists should not contain duplicates (i.e., Choice/Seq)
	List<ProtocolName<K>> getProtoDependencies();
	List<MemberName<?>> getNonProtoDependencies();  // N.B. delegation payloads currently here, not getProtoDependencies (CHECKME: refactor?)
	
	// Call this using: them.canEquals(this) 
	boolean canEquals(Object o);
}
