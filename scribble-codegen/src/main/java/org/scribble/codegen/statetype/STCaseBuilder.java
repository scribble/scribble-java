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
package org.scribble.codegen.statetype;

import org.scribble.core.model.endpoint.EState;
import org.scribble.core.model.endpoint.actions.EAction;
import org.scribble.core.model.endpoint.actions.ERecv;

public abstract class STCaseBuilder extends STStateChanBuilder  // Is a distinct type but not a state -- OK for now
{
	protected final STCaseActionBuilder cb;
	
	public STCaseBuilder(STCaseActionBuilder cb)
	{
		this.cb = cb;
	}
	
	@Override
	public String build(STStateChanAPIBuilder api, EState s)
	{
		String out = getPreamble(api, s);
		
		for (EAction a : s.getDetActions())
		{
			out += "\n\n";
			if (a instanceof ERecv)  // FIXME: factor out action kind
			{
				out += this.cb.build(api, s, a);
			}
			else
			{
				throw new RuntimeException("Shouldn't get in here: " + a);
			}
		}

		return out;
	}

	public abstract String getCaseStateChanName(STStateChanAPIBuilder api, EState s);
}
