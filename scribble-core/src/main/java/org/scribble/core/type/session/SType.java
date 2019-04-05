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
package org.scribble.core.type.session;

import java.util.List;
import java.util.Set;

import org.scribble.core.lang.SNode;
import org.scribble.core.type.kind.ProtocolKind;
import org.scribble.core.type.name.MemberName;
import org.scribble.core.type.name.MessageId;
import org.scribble.core.type.name.ProtocolName;
import org.scribble.core.type.name.RecVar;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.name.Substitutions;
import org.scribble.core.visit.STypeInliner;
import org.scribble.core.visit.STypeUnfolder;

// B needed(?) to factor up several methods from G/L compounds to bases, e.g., getInlined and unfoldAllOnce
// Generic works to "specialise" G/L subclasses (and works with immutable pattern) -- cf. not supported by contravariant method parameter subtyping for getters/setters
public interface SType<K extends ProtocolKind, B extends Seq<K, B>>
		extends SNode
{
	// Unsupported for Protocol (Protocol should not be an SType)
	Set<Role> getRoles();
	Set<MessageId<?>> getMessageIds();

	SType<K, B> substitute(Substitutions subs);
	
	Set<RecVar> getRecVars();
	SType<K, B> pruneRecs();  // Assumes no shadowing (e.g., use after inlining recvar disamb)
	
	// CHECKME: OK to (re-)use GTypeTranslator for inlining?
	SType<K, B> getInlined(STypeInliner i);
	
	// Unsupported for Do
	// CHECKME: repeat recvar names OK? including non-shadowing (e.g., choice cases)
	SType<K, B> unfoldAllOnce(STypeUnfolder<K> u);
	
	// Resulting Lists should not contain duplicates (i.e., Choice/Seq)
	// Result does not necessarily contain root proto (protodecl is not an SType), but may do so via dependencies
	List<ProtocolName<K>> getProtoDependencies();
	List<MemberName<?>> getNonProtoDependencies();  // N.B. delegation payloads currently here, not getProtoDependencies (CHECKME: refactor?)
	
	// subclass equals should call this by: them.canEquals(this) 
	boolean canEquals(Object o);
}
