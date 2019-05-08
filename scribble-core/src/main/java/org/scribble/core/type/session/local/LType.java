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
package org.scribble.core.type.session.local;

import org.scribble.core.lang.local.LNode;
import org.scribble.core.type.kind.Local;
import org.scribble.core.type.session.SType;

public interface LType extends SType<Local, LSeq>, LNode
{
	//Role getSelf();  // CHECKME: useful?  maybe only specifically for interactions
	
	//void buildGraph(EGraphBuilderUtil2 b);
}





















	/*@Override
	LType substitute(Substitutions subs);*/  // Otherwise causes return type inconsistency with base abstract classes

	/*// LType returns cause "conflicts" in implementing subclasses -- SType would need itself as another generic param, but not worth it
	@Override
	SType<Local, LSeq> getInlined(STypeInliner v);

	@Override
	SType<Local, LSeq> unfoldAllOnce(STypeUnfolder<Local> u);
	
	// Return true iff this LType is "equivalent" to a single "continue X", where X is in rvs
	boolean isSingleConts(Set<RecVar> rvs);
	
	// Return: (new) env post visiting 
	// cf. GType "visitor" methods: same pattern, just env as a bespoke data type wrapper
	ReachabilityEnv checkReachability(ReachabilityEnv env)
			throws ScribException;
	*/
