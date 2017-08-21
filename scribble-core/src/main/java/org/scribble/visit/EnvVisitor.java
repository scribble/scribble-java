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

import java.util.LinkedList;

import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ScribNode;
import org.scribble.main.Job;
import org.scribble.main.ScribbleException;
import org.scribble.type.kind.ProtocolKind;
import org.scribble.visit.context.ModuleContextVisitor;
import org.scribble.visit.env.Env;

// By default, EnvVisitor only manipulates internal Env stack -- so AST/dels not affected
// Attaching Envs to Dels has to be done manually by each pass
// FIXME: make a ProtocolDeclContextVisitor (caches ProtocolDeclContext, e.g. roles, modifiers) between this and ModuleContextVisitor
public abstract class EnvVisitor<T extends Env<?>> extends ModuleContextVisitor
{
	private LinkedList<T> envs = new LinkedList<T>();  // Deque
	
	public EnvVisitor(Job job)
	{
		super(job);
	}
	
	@Override
	protected final void enter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.enter(parent, child);
		if (child instanceof ProtocolDecl)  // Only the root ProtocolDecl is visited: subprotocols visit the body directly
		{
			ProtocolDecl<? extends ProtocolKind> pd = (ProtocolDecl<?>) child;
			pushEnv(makeRootProtocolDeclEnv(pd));
		}
		envEnter(parent, child);
	}
	
	@Override
	protected final ScribNode leave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		ScribNode n = envLeave(parent, child, visited); 
		if (n instanceof ProtocolDecl)  // Only the root ProtocolDecl is visited by SubprotocolVisitor (subprotocols visit the body directly)
		{
			this.envs.pop();
		}
		return super.leave(parent, child, n);
	}
	
	protected abstract T makeRootProtocolDeclEnv(ProtocolDecl<? extends ProtocolKind> pd);

	protected void envEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		//..could push copy of parent Env onto visitor stack for use by visitor pass (del env-leave routine should pop and push back the final result)
		//..but only if want an env for every node (unless restrict to specific nodes types -- e.g. interaction nodes) -- as opposed to only e.g. compound nodes, as done via del
		//..so either do base env management here or via del -- here, need to do instanceof; in delegates, need to duplicate base push/pop for each pass
		//..no: should be only compound interaction nodes, as typing environments
		//return this;
	}
	
	protected ScribNode envLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		return visited;
	}
	
	// Hack? e.g. for ModuleDecl
	public boolean hasEnv()
	{
		return !this.envs.isEmpty();
	}

	public T peekEnv()
	{
		return this.envs.peek();
	}

	public T peekParentEnv()
	{
		//return this.envs.get(this.envs.size() - 2);
		return this.envs.get(1);
	}
	
	public void pushEnv(T env)
	{
		this.envs.push(env);
	}
	
	public T popEnv()
	{
		return this.envs.pop();
	}
}
