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
package org.scribble.del.global;

import org.scribble.ast.ScribNode;
import org.scribble.del.InteractionNodeDel;
import org.scribble.del.ScribDelBase;
import org.scribble.main.ScribbleException;
import org.scribble.visit.context.Projector;

public interface GInteractionNodeDel extends InteractionNodeDel
{
	//public abstract LNode project(GNode n, Role self);  // Generalised return, e.g. returning a seq
	
	@Override
	default void enterProjection(ScribNode parent, ScribNode child, Projector proj) throws ScribbleException
	{
		ScribDelBase.pushVisitorEnv(this, proj);
	}

	@Override
	default ScribNode leaveProjection(ScribNode parent, ScribNode child, Projector proj, ScribNode visited) throws ScribbleException
	{
		return ScribDelBase.popAndSetVisitorEnv(this, proj, visited);
	}
}
