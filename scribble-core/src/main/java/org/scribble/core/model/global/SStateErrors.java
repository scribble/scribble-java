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
package org.scribble.core.model.global;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.scribble.core.model.endpoint.EState;
import org.scribble.core.model.endpoint.actions.ERecv;
import org.scribble.core.model.endpoint.actions.ESend;
import org.scribble.core.model.global.actions.SAction;
import org.scribble.core.type.name.Role;

public class SStateErrors
{
	public final SState state;
	
	// FIXME: factor out explicit error classes -- for error message formatting
	// FIXME: could also check for roles stuck on unconnected sends here (probably better, than current syntax check)
	public final Map<Role, ERecv> stuck;         // Reception errors
	public final Set<Set<Role>> waitFor;         // Deadlock cycles
	public final Map<Role, Set<ESend>> orphans;  // Orphan messages
	public final Map<Role, EState> unfinished;   // Unfinished roles

	public SStateErrors(SState state)
	{
		this.state = state;
		this.stuck = state.config.getStuckMessages();
		this.waitFor = state.config.getWaitForCycles();
		this.orphans = state.config.getOrphanMessages();
		this.unfinished = state.config.getUnfinishedRoles();
	}
	
	public boolean isEmpty()
	{
		return this.stuck.isEmpty() && this.waitFor.isEmpty()
				&& this.orphans.isEmpty() && this.unfinished.isEmpty();
	}
	
	@Override
	public String toString()
	{
		return "stuck=" + this.stuck + ", watiFor=" + this.waitFor + ", orphans="
				+ this.orphans + ", unfinished=" + this.unfinished;
	}

	public String toErrorMessage(SGraph graph)
	{
		String msg = "";  // Return empty when no error?
		if (!isEmpty())
		{
			// CHECKME: getTrace can get stuck when local choice subjects are disabled ? (has since been rewritten)
			List<SAction> trace = graph.getTraceFromInit(this.state);  // CHECKME: getTrace broken on non-det self loops?
			msg += "\nSafety violation(s) at session state " + this.state.id
					+ ":\n    Trace=" + trace
					+ appendErrors();  // Does leading "\n"
		}
		return msg;
	}

	// TODO: snip if too long? -- maybe not, let user pipe to file
	protected String appendErrors()
	{
		String res = "";
		if (!this.stuck.isEmpty())
		{
			res += "\n    Stuck messages: " + this.stuck;  // Deadlock from reception error
		}
		if (!this.waitFor.isEmpty())
		{
			res += "\n    Wait-for cycles: " + this.waitFor;  // Deadlock from input-blocked cycles -- not from terminated dependencies, cf. unfinished roles
		}
		if (!this.orphans.isEmpty())
		{
			res += "\n    Orphan messages: " + this.orphans;  // TODO: add sender of orphan to error message 
		}
		if (!this.unfinished.isEmpty())
		{
			res += "\n    Unfinished roles: " + this.unfinished;
		}
		return res;
	}
}
