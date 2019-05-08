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
package org.scribble.ast.local;

import org.antlr.runtime.Token;
import org.scribble.ast.DisconnectAction;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.core.type.kind.Local;
import org.scribble.del.DelFactory;
import org.scribble.util.Constants;

public class LDisconnect extends DisconnectAction<Local>
		implements LSimpleSessionNode
{
	// ScribTreeAdaptor#create constructor
	public LDisconnect(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public LDisconnect(LDisconnect node)
	{
		super(node);
	}

	// Cf. core LDisconnect
	public RoleNode getSelfChild()
	{
		return getLeftChild();
	}

	public RoleNode getPeerChild()
	{
		return getRightChild();
	}
	
	@Override
	public void decorateDel(DelFactory df)
	{
		df.LDisconnect(this);
	}
	
	@Override
	public LDisconnect dupNode()
	{
		return new LDisconnect(this);
	}

	@Override
	public String toString()
	{
		return Constants.DISCONNECT_KW + " " + getRightChild() + ";";   // FIXME: make explicit, using right as peer (cf. core.type)
	}
}
