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
package org.scribble.visit.context;

import org.scribble.ast.Module;
import org.scribble.ast.ScribNode;
import org.scribble.ast.context.ModuleContext;
import org.scribble.del.ModuleDel;
import org.scribble.main.Job;
import org.scribble.main.ScribbleException;
import org.scribble.visit.AstVisitor;

public abstract class ModuleContextVisitor extends AstVisitor
{
	private ModuleContext mcontext;  // The "root" module context (different than the front-end "main" module)  // Factor up to ModelVisitor? (will be null before context building)

	public ModuleContextVisitor(Job job)
	{
		super(job);
	}

	@Override
	protected void enter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.enter(parent, child);
		if (child instanceof Module)  // Factor out?
		{
			setModuleContext(((ModuleDel) ((Module) child).del()).getModuleContext());
		}
	}

	public ModuleContext getModuleContext()
	{
		return this.mcontext;
	}
	
	// Factor out -- e.g. some Visitors want to root on ProtocolDecl, not Module
	protected void setModuleContext(ModuleContext mcontext)
	{
		this.mcontext = mcontext;
	}
}
