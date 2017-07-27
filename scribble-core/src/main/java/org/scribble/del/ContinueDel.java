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

import org.scribble.ast.Continue;
import org.scribble.ast.Recursion;
import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.type.name.RecVar;
import org.scribble.visit.InlinedProtocolUnfolder;
import org.scribble.visit.util.RecVarCollector;

public abstract class ContinueDel extends SimpleInteractionNodeDel
{
	public ContinueDel()
	{

	}

	/*@Override
	public Continue<?> leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner inl, ScribNode visited) throws ScribbleException
	{
		Continue<?> c = (Continue<?>) visited;
		Continue<?> inlined = (Continue<?>) c.clone();
		inl.pushEnv(inl.popEnv().setTranslation(inlined));
		return (Continue<?>) super.leaveProtocolInlining(parent, child, inl, c);
	}*/

	@Override
	public ScribNode leaveInlinedProtocolUnfolding(ScribNode parent, ScribNode child, InlinedProtocolUnfolder unf, ScribNode visited) throws ScribbleException
	{
		Continue<?> cont = (Continue<?>) visited;
		RecVar rv = cont.recvar.toName();
		if (unf.isContinueUnguarded(rv))  // Without this, graph building becomes sensitive to the order of choice blocks (specifically, the relative position were the side effect (re-set entry to rec state) of an unguarded continue is performed)
		{
			Recursion<?> tmp = unf.getRecVar(rv);
			if (tmp == null)  // Hacky?  for recursive unfolding of cached blocks
			{
				return cont;
			}
			return unf.getRecVar(rv).clone(unf.job.af);
		}
		else if (unf.shouldUnfoldForUnguardedRec(rv))
		{
			return unf.getRecVar(rv).clone(unf.job.af);
		}
		return cont;
	}

	@Override
	public ScribNode leaveRecVarCollection(ScribNode parent, ScribNode child, RecVarCollector coll, ScribNode visited)
			throws ScribbleException
	{
		coll.removeName(((Continue<?>) visited).recvar.toName());
		return visited;
	}
}
