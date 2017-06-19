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
	public abstract String getActionName(STStateChanAPIBuilder api, EAction a);
	public abstract String buildArgs(EAction a);
	public abstract String buildBody(STStateChanAPIBuilder api, EState curr, EAction a, EState succ);

	public String getReturnType(STStateChanAPIBuilder api, EState curr, EState succ)
	{
		return api.getStateChanName(succ);
	}

	public String buildReturn(STStateChanAPIBuilder api, EState curr, EState succ)
	{
		return api.buildActionReturn(this, curr, succ);
	}
	
	public String build(STStateChanAPIBuilder api, EState curr, EAction a)
	{
		return api.buildAction(this, curr, a);  // Because action builder hierarchy not suitable (extended by action kinds, not by target language) 
	}
	
	public String getStateChanType(STStateChanAPIBuilder api, EState curr, EAction a)
	{
		return api.getStateChanName(curr);
	}
}
