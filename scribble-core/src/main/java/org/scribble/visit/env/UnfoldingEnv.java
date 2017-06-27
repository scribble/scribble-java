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

import java.util.Arrays;
import java.util.List;

public class UnfoldingEnv extends Env<UnfoldingEnv>
{
	private boolean shouldUnfold;  // i.e. unguarded choice context
	
	public UnfoldingEnv()
	{
		this.shouldUnfold = false;
	}

	protected UnfoldingEnv(boolean shouldUnfold)
	{
		this.shouldUnfold = shouldUnfold;
	}

	@Override
	protected UnfoldingEnv copy()
	{
		return new UnfoldingEnv(this.shouldUnfold);
	}

	@Override
	public UnfoldingEnv enterContext()
	{
		return copy();
	}

	@Override
	public UnfoldingEnv mergeContext(UnfoldingEnv env)
	{
		return mergeContexts(Arrays.asList(env));
	}

	@Override
	public UnfoldingEnv mergeContexts(List<UnfoldingEnv> envs)
	{
		UnfoldingEnv copy = copy();
		boolean merge = (envs.stream().filter((e) -> e.shouldUnfold).count() > 0);
		copy.shouldUnfold = merge;
		return copy;
	}

	public boolean shouldUnfold()
	{
		return this.shouldUnfold;
	}

	public UnfoldingEnv pushChoiceParent()
	{
		UnfoldingEnv copy = copy();
		copy.shouldUnfold = true;
		return copy;
	}
	
	public UnfoldingEnv disableUnfold()
	{	
		UnfoldingEnv copy = copy();
		copy.shouldUnfold = false;
		return copy;
	}
	
	@Override
	public String toString()
	{
		return super.toString() + ": " + this.shouldUnfold;
	}
}
