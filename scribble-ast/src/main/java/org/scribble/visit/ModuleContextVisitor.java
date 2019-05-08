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
package org.scribble.visit;

import org.scribble.ast.Module;
import org.scribble.ast.ScribNode;
import org.scribble.core.lang.context.ModuleContext;
import org.scribble.core.type.name.ModuleName;
import org.scribble.job.Job;
import org.scribble.util.ScribException;

// A visitor that caches the ModuleContext from the *entered* Module, for later access
// N.B. may be a different Module than the "root" Module of the job
// Looking up the "entered" Module context is otherwise inconvenient
public abstract class ModuleContextVisitor extends AstVisitor
{
	private ModuleContext mcon;  // The "current" module context, gathered on entry -- different than the front-end "main" module 

	public ModuleContextVisitor(Job job)
	{
		super(job);
	}

	@Override
	protected void enter(ScribNode child)
			throws ScribException
	{
		super.enter(child);
		if (child instanceof Module)  // Factor out?  (e.g., Module?)
		{
			ModuleName fullname = ((Module) child).getFullModuleName();
			setModuleContext(this.job.getContext().getModuleContext(fullname));
		}
	}

	public ModuleContext getModuleContext()
	{
		return this.mcon;
	}
	
	// Factor out -- e.g. some Visitors want to root on ProtocolDecl, not Module
	protected void setModuleContext(ModuleContext mcontext)
	{
		this.mcon = mcontext;
	}
}
