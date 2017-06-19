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
import org.scribble.model.endpoint.actions.EConnect;
import org.scribble.model.endpoint.actions.EDisconnect;
import org.scribble.model.endpoint.actions.ESend;

public abstract class STOutputStateBuilder extends STStateChanBuilder
{
	protected final STSendActionBuilder sb;
	
	public STOutputStateBuilder(STSendActionBuilder sb)
	{
		this.sb = sb;
	}
	
	@Override
	public String build(STStateChanAPIBuilder api, EState s)
	{
		String out = getPreamble(api, s);
				  /*"package " + getPackage(gpn)
				+ "\n"
				+ "type " + getSTStateName(gpn, role, s) + " struct{}";*/
		
		for (EAction a : s.getActions())
		{
			out += "\n\n";
			if (a instanceof ESend)  // FIXME: factor out action kind
			{
				out += this.sb.build(api, s, a);
			}
			else if (a instanceof EConnect)
			{
				throw new RuntimeException("TODO: " + a);
			}
			else if (a instanceof EDisconnect)
			{
				throw new RuntimeException("TODO: " + a);
			}
			else
			{
				throw new RuntimeException("Shouldn't get in here: " + a);
			}
		}
		
		return out;
	}
}
