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
package org.scribble.core.type.session.global;

import org.scribble.core.lang.global.GNode;
import org.scribble.core.type.kind.Global;
import org.scribble.core.type.session.SType;

public interface GType extends SType<Global, GSeq>, GNode
{
	
}	
	
	
	
	
	
	
	
	
	




	
	
	
	
	/*// Pre: use on inlined or later (unsupported for Do, also Protocol)
	// enabled treated immutably
	// Returns enabled post visiting
	// Cf. SType visitors, here we only throw ScribbleException or return nothing, so can use return this way
	Set<Role> checkRoleEnabling(Set<Role> enabled) throws ScribException; 
	
	// Pre: use on inlined or later (unsupported for Do, also Protocol)
	// Pre: checkRoleEnabling
	// enablers: enabled -> enabler -- treated immutably
	// Returns enablers post visiting
	Map<Role, Role> checkExtChoiceConsistency(Map<Role, Role> enablers)
			throws ScribException;
	
	// Pre: conns is reflexive
	// Also does correlation warnings
	//Map<Role, Role> checkConnections(Map<Role, Role> conns) throws ScribbleException;
			// Check on model instead: could possibly do syntactically on once-unfolding, but still need separate checks for dup-conn, unconnected and bad-dconn (may vs. must)
	*/

	/*@Override
	GType substitute(Substitutions subs);*/  // Otherwise causes return type inconsistency with base abstract classes

	/*// GType returns cause "conflicts" in implementing subclasses -- SType would need itself as another generic param, but not worth it
	@Override
	GType getInlined(STypeInliner i);//, Deque<SubprotoSig> stack);

	@Override
	SType<Global> unfoldAllOnce(STypeUnfolder<Global> u);  // Not GType return, o/w need to override again in GDo

	LType 
	projectInlined(Role self);  // Use on inlined (i.e., Do inlined, roles pruned)

	LType project(ProjEnv v);  // Use on parsed (intermed)
	*/

