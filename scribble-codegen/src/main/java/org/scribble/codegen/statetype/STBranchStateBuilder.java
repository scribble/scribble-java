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

public abstract class STBranchStateBuilder extends STStateChanBuilder
{
	protected final STBranchActionBuilder bb;
	
	public STBranchStateBuilder(STBranchActionBuilder bb)
	{
		this.bb = bb;
	}
	
	@Override
	public String build(STStateChanAPIBuilder api, EState s)
	{
		String out = getPreamble(api, s);
		
		out += "\n\n";
		out += this.bb.build(api, s, s.getActions().get(1));  // Getting 1 checks non-unary

		return out;
	}
}
