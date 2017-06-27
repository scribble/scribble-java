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
package org.scribble.visit.env;

import java.util.List;

// Consider subclasses like Exception Subclasses, wrt. their purpose in a package -- although using explicit env subpackages (no special API doc treatment, cf. Exceptions)

// Immutable
// Generic parameter inhibits further subclassing of concrete Env classes
public abstract class Env<E extends Env<?>>
{
	protected Env()
	{

	}
	
	protected abstract E copy();  // Shallow copy

	// Default push for entering a compound interaction context (e.g. used in CompoundInteractionDel via pushVisitorEnv)
	public abstract E enterContext();  // copy by default
			// FIXME: it's always just copy, so not useful -- e.g., WFChoiceEnv just does enterContext (copy) and then manually enableChoiceSubject

	// Mostly for merging a compound interaction node context into the parent block context
	// Usually used in the base compound interaction node del when leaving and restoring the parent env in the visitor env stack (e.g. CompoundInteractionNodeDel for WF-choice)
  // By default: merge just discards the argument(s) -- not all EnvVisitors need to merge (e.g. projection)
	// FIXME: maybe not "expressive" enough -- e.g. has to be "overloaded" for "horizontal composition" and "vertical parent-child" merging of contexts (e.g. path collection)
	protected Env<E> mergeContext(E env)
	{
		//return mergeContexts(Arrays.asList(env));
		return this;
	}

	// Can't use vargs with parameterized types
	// Mostly for merging child blocks contexts into the parent compound interaction node context
	// Usually used in the parent compound interaction node del to update the parent context after visting each child block before leaving (e.g. GChoiceDel for WF-choice)
	// FIXME: deprecate (should use "compose" first on children, then "merge" to parent) -- or make a utility composeAndMerge method
	protected Env<E> mergeContexts(List<E> envs)
	{
		return this;
	}
	
	// FIXME: refactor older Envs to use "merge" for parent-child merging, and "compose" for horizontal merging
	protected Env<E> composeContext(E env)
	{
		return this;
	}
}
