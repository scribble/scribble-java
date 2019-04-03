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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.type.kind.ProtocolKind;

// TODO CHECKME: factor with MessageTransfer?
public abstract class ConnectAction<K extends ProtocolKind>
		extends DirectedInteraction<K>
{
	// ScribTreeAdaptor#create constructor
	public ConnectAction(Token t)
	{
		super(t);
		this.src = null;
		this.msg = null;
		this.dest = null;
	}

	// Tree#dupNode constructor
	public ConnectAction(ConnectAction<K> node)
	{
		super(node);
		this.src = null;
		this.msg = null;
		this.dest = null;
	}
	
	// TODO: refactor
	public RoleNode getDestinationChild()
	{
		List<RoleNode> dests = getDestinationChildren();
		if (dests.size() != 1)
		{
			throw new RuntimeException("Shouldn't get in here: " + this);
		}
		return dests.get(0);
	}
	
	// Currently only used for toString, because current syntax allows "connect" with no explicit message
	protected boolean isUnitMessage()
	{
		MessageNode n = getMessageNodeChild();
		if (!n.isMessageSigNode())
		{
			return false;
		}
		MessageSigNode msn = (MessageSigNode) n;
		return msn.getOpChild().isEmpty() && msn.getPayloadListChild().isEmpty();
	}
	
	
	
	
	
	
	
	
	
	
	
	private final RoleNode src;
	private final MessageNode msg;
	private final RoleNode dest;

	protected ConnectAction(CommonTree source, RoleNode src, MessageNode msg, RoleNode dest)
	//protected ConnectionAction(RoleNode src, RoleNode dest)
	{
		super(source, src, msg, Stream.of(dest).collect(Collectors.toList()));
		this.src = src;
		this.msg = msg;
		this.dest = dest;
	}
}
