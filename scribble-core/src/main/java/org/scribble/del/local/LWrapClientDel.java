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
import org.scribble.ast.local.LWrapClient;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ConnectionActionDel;
import org.scribble.main.ScribbleException;
import org.scribble.type.name.Role;
import org.scribble.visit.context.EGraphBuilder;
import org.scribble.visit.context.ProjectedChoiceSubjectFixer;

public class LWrapClientDel extends ConnectionActionDel implements LSimpleInteractionNodeDel
{
	@Override
	public LWrapClient leaveEGraphBuilding(ScribNode parent, ScribNode child, EGraphBuilder builder, ScribNode visited) throws ScribbleException
	{
		LWrapClient lc = (LWrapClient) visited;
		RoleNode dest = lc.dest;
		Role peer = dest.toName();
		builder.util.addEdge(builder.util.getEntry(), builder.job.ef.newEWrapClient(peer), builder.util.getExit());
		return (LWrapClient) super.leaveEGraphBuilding(parent, child, builder, lc);
	}

	@Override
	public void enterProjectedChoiceSubjectFixing(ScribNode parent, ScribNode child, ProjectedChoiceSubjectFixer fixer)
	{
		fixer.setChoiceSubject(((LWrapClient) child).src.toName());
	}
}
