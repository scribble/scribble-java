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
package org.scribble.del;

import org.scribble.ast.ScribNode;
import org.scribble.visit.EnvVisitor;
import org.scribble.visit.env.Env;

// Mutable for pass-specific Envs (by visitors)
public abstract class ScribDelBase implements ScribDel
{
	private Env<?> env;
	
	public ScribDelBase()
	{

	}
	
	@Override
	public Env<?> env()
	{
		return this.env;
	}
	
	// "setEnv" rather than "env" as a non-defensive setter (cf. ModelNodeBase#del)
	@Override
	//protected void setEnv(Env env)
	public void setEnv(Env<?> env)
	{
		this.env = env;
	}
	
	public static <T extends Env<?>> void pushVisitorEnv(ScribDel del, EnvVisitor<T> ev)
	{
		T env = castEnv(ev, ev.peekEnv().enterContext());  // By default: copy
		ev.pushEnv(env);
	}
	
	public static <T1 extends Env<?>, T2 extends ScribNode>
			T2 popAndSetVisitorEnv(ScribDel del, EnvVisitor<T1> ev, T2 visited)
	{
		// No merge here: merging of child contexts into parent context is handled "manually" for each pass and (compound) interaction node as needed (e.g. WF-choice and reachability)
		T1 env = ev.popEnv();
		((ScribDelBase) del).setEnv(env);
		return visited;
	}
	
	private static <T extends Env<?>> T castEnv(EnvVisitor<T> ev, Env<?> env) 
	{
		@SuppressWarnings("unchecked")
		T tmp = (T) env;
		return tmp;
	}
}
