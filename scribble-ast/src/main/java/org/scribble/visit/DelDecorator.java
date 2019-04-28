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

import org.scribble.ast.ScribNode;
import org.scribble.ast.ScribNodeBase;
import org.scribble.del.DelFactory;
import org.scribble.job.Job;
import org.scribble.util.ScribException;

// A visitor that caches the ModuleContext from the *entered* Module, for later access
// N.B. may be a different Module than the "root" Module of the job
// Looking up the "entered" Module context is otherwise inconvenient
public class DelDecorator extends AstVisitor
{
	protected final DelFactory df;

	protected DelDecorator(Job job)
	{
		super(job);
		this.df = job.config.df;
	}
	
	// AstVisitor enter/leave typically delegates to dels -- DelDecorator is a "proto-visitor", no del to delegate to (yet)
	@Override
	protected ScribNode leave(ScribNode child, ScribNode visited)
			throws ScribException
	{
		decorate(visited);  // Mutating setter
		return visited;
	}
	
	protected void decorate(ScribNode n)
	{
		if (n.del() != null)  // Currently, only the simple name nodes "constructed directly" by parser, e.g., t=ID -> ID<...Node>[$t] 
		{
			return;
		}
		((ScribNodeBase) n).decorateDel(this.df);

		/*try
		{
			String cname = n.getClass().getName();
			String mname = cname.substring(cname.lastIndexOf('.')+1, cname.length());
			Class<?> param = Class.forName(cname);
			Method m = DelFactory.class.getMethod(mname, param);
			m.invoke(this.df, n);
		}
		catch (NoSuchMethodException | SecurityException | ClassNotFoundException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e)
		{
			throw new RuntimeException(e);
		}*/
	}

	/*protected void decorateChildren(ScribNode n)
	{
		n.getChildren().stream().forEach(x -> decorate(x));
	}*/
}




