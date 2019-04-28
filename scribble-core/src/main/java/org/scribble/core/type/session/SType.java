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

import java.util.function.Function;
import java.util.stream.Stream;

import org.scribble.core.lang.SNode;
import org.scribble.core.type.kind.ProtoKind;
import org.scribble.core.visit.STypeAgg;
import org.scribble.core.visit.STypeAggNoThrow;
import org.scribble.util.ScribException;


// B used (needed?) to factor up several methods from G/L compounds to bases, e.g., getInlined and unfoldAllOnce
// Generic param works to "specialise" G/L subclasses (and works with immutable pattern) -- cf. not supported by contravariant method parameter subtyping for getters/setters
// N.B. Taking G/LSeq as the B param "limits" the "cast-freeness" of this hierarchy at this point -- i.e., potential G/LSeq subclasses would be beyond that point
// However, the framework is mainly intended to be extended "horizontally" w.r.t. node types (additional types), and "vertically" (subclasses) for behaviour only via Visitors
// In case of "vertical" extension of node types (e.g., additional node fields), still consider that extension of Seq is less often necessary 
public interface SType<K extends ProtoKind, B extends Seq<K, B>>
		extends SNode<K>
{
	// N.B. visitWith should be considered a "top-level" entry point only, i.e., do not assume visitWith is called (or not) again during the recursive traversal
	// (visitWith may be called enroute to each visitNode, except for Seq, which is "entered directly" via visitSeq due to its generic typing)
	// (However, visitWith is used by Seq to visit its elems -- main point of visitWith is to be agnostic to node type)
	<T> T visitWith(STypeAgg<K, B, T> v) throws ScribException;
	<T> T visitWithNoThrow(STypeAggNoThrow<K, B, T> v);

	// Pass in an STypeGatherer::visit, e.g., n.(new RoleGatherer<Global, GSeq>()::visit)
	<T> Stream<T> gather(Function<SType<K, B>, Stream<T>> f);

	// subclass equals should call this by: them.canEquals(this) 
	boolean canEquals(Object o);
	
	
	
	
	
	

	
	
	
	
	
	
	
	

	/*Set<Role> getRoles();
	Set<MessageId<?>> getMessageIds();
	Set<RecVar> getRecVars();  // Gets Continue RecVars (not Recursion)
	
	// CHECKME: OK to (re-)use GTypeTranslator for inlining?
	SType<K, B> getInlined(STypeInliner i);
	
	// Unsupported for Do
	// CHECKME: repeat recvar names OK? including non-shadowing (e.g., choice cases)
	SType<K, B> unfoldAllOnce(STypeUnfolder<K> u);
	
	// Resulting Lists should not contain duplicates (i.e., Choice/Seq use distinct)
	// Result does not necessarily contain root proto (protodecl is not an SType), but may do so via dependencies
	List<ProtocolName<K>> getProtoDependencies();
	List<MemberName<?>> getNonProtoDependencies();  // N.B. delegation payloads currently here, not getProtoDependencies (CHECKME: refactor?)

	SType<K, B> substitute(Substitutions subs);
	
	SType<K, B> pruneRecs();  // Assumes no shadowing (e.g., use after inlining recvar disamb)
	*/
}
