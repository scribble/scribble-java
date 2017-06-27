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
package org.scribble.visit.context.env;

import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LNode;
import org.scribble.visit.env.Env;

// Could make projection type a class parameter
public class ProjectionEnv extends Env<ProjectionEnv>
{
	private ScribNode projection;  // Make local  // FIXME: need to generalise for do projection (target protocol as well as the do)
	
	public ProjectionEnv()
	{

	}

	@Override
	public ProjectionEnv copy()
	{
		ProjectionEnv copy = new ProjectionEnv();
		copy.projection = this.projection;
		return copy;
	}

	@Override
	public ProjectionEnv enterContext()
	{
		return copy();
	}
	
	public ScribNode getProjection()
	{
		return this.projection;
	}

	public ProjectionEnv setProjection(LNode projection)
	{
		ProjectionEnv copy = new ProjectionEnv();
		copy.projection = projection;
		return copy;
	}

	@Override
	public String toString()
	{
		return "projection=" + this.projection;
	}
}
