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

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.scribble.ast.ScribNode;
import org.scribble.main.Job;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.name.RecVar;
import org.scribble.sesstype.name.Role;

public class ProjectedChoiceSubjectFixer extends ModuleContextVisitor
{
	private Map<RecVar, Deque<Role>> recs = new HashMap<RecVar, Deque<Role>>();
	
	public ProjectedChoiceSubjectFixer(Job job)
	{
		super(job);
	}

	@Override
	protected final void enter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.enter(parent, child);
		child.del().enterProjectedChoiceSubjectFixing(parent, child, this);
		
		/*if (child instanceof LProtocolDecl)
		{
			System.out.println("aaa:\n" + ((LProtocolDecl) child));
		}*/
	}
	
	@Override
	protected ScribNode leave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveProjectedChoiceSubjectFixing(parent, child, this, visited);
		return super.leave(parent, child, visited);
	}
	
	public Role getChoiceSubject(RecVar rv)
	{
		return this.recs.get(rv).peek();
	}
	
	public void setChoiceSubject(Role subj)
	{
		for (Deque<Role> rs : this.recs.values())
		{
			if (!rs.isEmpty())  // E.g. rec X { ... } rec Y { ... } -- after X, X is still in map but is empty
			{
				if (rs.peek() == null)
				{
					rs.pop();
					rs.push(subj);
				}
			}
		}
	}
	
	public void pushRec(RecVar rv)
	{
		Deque<Role> tmp = this.recs.get(rv);
		if (tmp == null)
		{
			tmp = new LinkedList<>();
			this.recs.put(rv, tmp);
		}
		tmp.push(null);
	}
	
	public void popRec(RecVar rv)
	{
		this.recs.get(rv).pop();
	}
	
	public RecVarRole createRecVarRole(RecVar rv)
	{
		return new RecVarRole(rv.toString());
	}
	
	public boolean isRecVarRole(Role r)
	{
		return (r instanceof RecVarRole);
	}
}

class RecVarRole extends Role // HACK for unguarded continues -- but not actually needed?
{
	private static final long serialVersionUID = 1L;
	
	public RecVarRole(String text)
	{
		super(text);
	}
}
