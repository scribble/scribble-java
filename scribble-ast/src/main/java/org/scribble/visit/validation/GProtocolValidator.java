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
package org.scribble.visit.validation;

import org.scribble.ast.ScribNode;
import org.scribble.core.job.Job;
import org.scribble.lang.Lang;
import org.scribble.util.ScribException;
import org.scribble.visit.context.ModuleContextVisitor;

public class GProtocolValidator extends ModuleContextVisitor
{
	public final Job job2;

	public GProtocolValidator(Lang job)
	{
		super(job);
		this.job2 = null;
	}

	public GProtocolValidator(Job job2)
	{
		super(null);
		this.job2 = job2;
	}

	@Override
	public void enter(ScribNode parent, ScribNode child) throws ScribException
	{
		super.enter(parent, child);
		child.del().enterValidation(parent, child, this);
	}
	
	@Override
	public ScribNode leave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribException
	{
		visited = visited.del().leaveValidation(parent, child, this, visited);
		return super.leave(parent, child, visited);
	}
}
