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

// TODO CHECKME: factor with MessageTransfer?
public abstract class ConnectAction<K extends ProtoKind>
		extends DirectedInteraction<K>
{
	// ScribTreeAdaptor#create constructor
	public ConnectAction(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public ConnectAction(ConnectAction<K> node)
	{
		super(node);
	}
	
	// TODO: refactor
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
	
	// CHECKME: currently only used for toString, because current syntax allows "connect" with no explicit message ?
	protected boolean isUnitMessage()
	{
		MsgNode n = getMessageNodeChild();
		if (!n.isSigLitNode())
		{
			return false;
		}
		SigLitNode msn = (SigLitNode) n;
		return msn.getOpChild().isEmpty() && msn.getPayloadListChild().isEmpty();
	}
}
