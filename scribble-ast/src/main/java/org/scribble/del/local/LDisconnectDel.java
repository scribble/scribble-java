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

import org.scribble.del.ConnectionActionDel;

public class LDisconnectDel extends ConnectionActionDel
		implements LSimpleSessionNodeDel
{
	/*@Override
	public LDisconnect leaveEGraphBuilding(ScribNode parent, ScribNode child, EGraphBuilder builder, ScribNode visited) throws ScribbleException
	{
		LDisconnect ld = (LDisconnect) visited;
		Role peer = ld.peer.toName();
		builder.util.addEdge(builder.util.getEntry(), builder.job.ef.newEDisconnect(peer), builder.util.getExit());
		return (LDisconnect) super.leaveEGraphBuilding(parent, child, builder, ld);
	}

	@Override
	public void enterProjectedChoiceSubjectFixing(ScribNode parent, ScribNode child, ProjectedChoiceSubjectFixer fixer)
	{
		fixer.setChoiceSubject(((LDisconnect) child).src.toName());
	}*/
}
