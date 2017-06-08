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
package org.scribble.del.local;

import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LCompoundInteractionNode;
import org.scribble.main.ScribbleException;
import org.scribble.visit.wf.ReachabilityChecker;
import org.scribble.visit.wf.env.ReachabilityEnv;

public interface LCompoundInteractionNodeDel extends LInteractionNodeDel
{
	@Override
	default LCompoundInteractionNode leaveReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker, ScribNode visited) throws ScribbleException
	{
		// Following CompoundInteractionNodeDel#leaveInlinedProtocolUnfolding/leaveWFChoiceCheck
		ReachabilityEnv visited_env = checker.popEnv();
		setEnv(visited_env);
		ReachabilityEnv parent_env = checker.popEnv();
		parent_env = parent_env.mergeContext(visited_env);
		checker.pushEnv(parent_env);
		return (LCompoundInteractionNode) visited;
	}
}
