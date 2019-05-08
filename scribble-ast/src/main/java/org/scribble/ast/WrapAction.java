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
package org.scribble.ast;

import java.util.List;

import org.antlr.runtime.Token;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.core.type.kind.ProtoKind;

public abstract class WrapAction<K extends ProtoKind>
		extends DirectedInteraction<K>
{
	// ScribTreeAdaptor#create constructor
	public WrapAction(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public WrapAction(WrapAction<K> node)
	{
		super(node);
	}
	
	public RoleNode getClientChild()
	{
		return (RoleNode) getSourceChild();
	}

	public RoleNode getServerChild()
	{
		return getDestinationChild();
	}

	// Cf. ConnectAction
	public RoleNode getDestinationChild()
	{
		List<RoleNode> dests = getDestinationChildren();
		if (dests.size() != 1)
		{
			throw new RuntimeException("Shouldn't get in here: " + this);  
					// CHECKME: don't use common src/dst pattern between global/local?
		}
		return dests.get(0);
	}

	@Override
	public void addScribChildren(MsgNode msg, RoleNode src, List<RoleNode> dsts)
	{
		throw new RuntimeException("Unsupported for LWrapAction: " + msg);
	}

	// "add", not "set"
	// "Overrides" DirectedInteraction::addScribChildren
	public void addScribChildren(RoleNode client, RoleNode server)
	{
		// Cf. above getters and Scribble.g children order
		addChild(client);
		addChild(server);
	}
}
