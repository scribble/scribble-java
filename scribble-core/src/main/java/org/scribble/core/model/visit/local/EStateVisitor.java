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
package org.scribble.core.model.visit.local;

import org.scribble.core.model.endpoint.EState;
import org.scribble.core.model.endpoint.actions.EAction;
import org.scribble.core.model.visit.StateVisitor;
import org.scribble.core.type.kind.Local;
import org.scribble.core.type.name.RecVar;
import org.scribble.util.ScribException;

public abstract class EStateVisitor
		extends StateVisitor<RecVar, EAction, EState, Local>
{
	
	// Allows to visit branch succs with fresh visitors, but limited usefulness? because pattern doesn't support merge...
	// (...but could try too? e.g., by using EGraph to join visitors? -- issue is recursion, may need to precompute/integrate traversal paths and join points, e.f., b/dfs)
	// CHECKME: take "self" generic param, use as return?  or just override
	public EStateVisitor enter(EState s, EAction a, EState succ)
	{
		return this;
	}
	
	public void visitAccept(EState s) throws ScribException
	{
		setSeen(s);
	}

	public void visitOutput(EState s) throws ScribException
	{
		setSeen(s);
	}

	public void visitPolyInput(EState s) throws ScribException
	{
		setSeen(s);
	}

	public void visitServerWrap(EState s) throws ScribException
	{
		setSeen(s);
	}

	public void visitTerminal(EState s) throws ScribException
	{
		setSeen(s);
	}

	public void visitUnaryInput(EState s) throws ScribException
	{
		setSeen(s);
	}
}
	