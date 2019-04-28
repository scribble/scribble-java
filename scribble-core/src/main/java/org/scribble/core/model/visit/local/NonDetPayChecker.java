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

import java.util.List;

import org.scribble.core.model.endpoint.EState;
import org.scribble.core.model.endpoint.actions.EAction;
import org.scribble.util.ScribException;

// CHECKME: should make via factory method?  cf. VisitorFactory
public class NonDetPayChecker extends EStateVisitor
{

	protected void checkNonDetPays(EState s) throws ScribException
	{
		List<EAction> as = s.getActions();
		if (as.size() <= 1)
		{
			return;
		}
		for (EAction a : as)
		{
			if (as.stream().anyMatch(x -> x.peer.equals(a.peer) && x.mid.equals(a.mid)
					&& !x.payload.equals(a.payload)))
			{
				throw new ScribException(
						"Bad non-deterministic action payloads: " + as);
			}
		}
	}

	@Override
	public void visitAccept(EState s) throws ScribException
	{
		super.visitOutput(s);  // Does setSeen
		checkNonDetPays(s);
	}

	@Override
	public void visitOutput(EState s) throws ScribException
	{
		super.visitOutput(s);  // Does setSeen
		checkNonDetPays(s);
	}

	@Override
	public void visitPolyInput(EState s) throws ScribException
	{
		super.visitOutput(s);  // Does setSeen
		checkNonDetPays(s);
	}

	@Override
	public void visitServerWrap(EState s) throws ScribException
	{
		super.visitOutput(s);  // Does setSeen
		checkNonDetPays(s);
	}
}	