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
package org.scribble.type.session.local;

import java.util.Set;

import org.scribble.job.ScribbleException;
import org.scribble.lang.local.ReachabilityEnv;
import org.scribble.model.endpoint.EGraphBuilderUtil2;
import org.scribble.type.kind.Local;
import org.scribble.type.name.RecVar;
import org.scribble.type.session.SType;

public interface LType extends SType<Local, LSeq>
{
	//Role getSelf();  // CHECKME
	
	// Return recvar of the "single continue", if so; return null, if not
	@Deprecated
	RecVar isSingleCont();

	boolean isSingleConts(Set<RecVar> rvs);
	
	// Uses b to builds graph "progressively" (working graph is mutable)
	// Use EGraphBuilderUtil2::finalise for final result
	void buildGraph(EGraphBuilderUtil2 b);
	
	ReachabilityEnv checkReachability(ReachabilityEnv env)
			throws ScribbleException;

	/*@Override
	LType substitute(Substitutions subs);*/  // Otherwise causes return type inconsistency with base abstract classes

	/*// LType returns cause "conflicts" in implementing subclasses -- SType would need itself as another generic param, but not worth it
	@Override
	SType<Local, LSeq> getInlined(STypeInliner v);

	@Override
	SType<Local, LSeq> unfoldAllOnce(STypeUnfolder<Local> u);
	*/
}
