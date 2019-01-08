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

import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;

public abstract class STActionBuilder
{
	public String build(STStateChanApiBuilder scb, EState curr, EAction a)
	{
		return scb.buildAction(this, curr, a);  // Because action builder hierarchy not suitable (extended by action kinds, not by target language) 
	}

	public String buildReturn(STStateChanApiBuilder scb, EState curr, EState succ)
	{
		return scb.buildActionReturn(this, curr, succ);
	}

	public abstract String getActionName(STStateChanApiBuilder api, EAction a);
	public abstract String buildArgs(STStateChanApiBuilder api, EAction a);
	public abstract String buildBody(STStateChanApiBuilder api, EState curr, EAction a, EState succ);

	public String getReturnType(STStateChanApiBuilder scb, EState curr, EState succ)
	{
		return scb.getStateChanName(succ);
	}
	
	public String getStateChanType(STStateChanApiBuilder scb, EState curr, EAction a)
	{
		return scb.getStateChanName(curr);
	}
}
