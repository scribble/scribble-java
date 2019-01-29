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

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ScribDel;
import org.scribble.main.ScribbleException;
import org.scribble.type.kind.ProtocolKind;
import org.scribble.visit.AstVisitor;

// TODO: factor with MessageTransfer
public abstract class ConnectAction<K extends ProtocolKind> extends SimpleInteractionNode<K>
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
	
	public abstract ConnectAction<K> dupNode();
	
	public MessageNode getMessageNodeChild()
	{
		return (MessageNode) getChild(0);
	}
	
	public RoleNode getSourceChild()
	{
		return (RoleNode) getChild(1);
	}

	public RoleNode getDestinationChild()
	{
		return (RoleNode) getChild(2);
	}

	public ConnectAction<K> reconstruct(RoleNode src, MessageNode msg, RoleNode dest)
	{
		ConnectAction<K> ca = dupNode();
		ca.addChild(msg);
		ca.addChild(src);
		ca.addChild(dest);
		ScribDel del = del();
		ca.setDel(del);  // No copy
		return ca;
	}

	@Override
	public ConnectAction<K> visitChildren(AstVisitor nv)
			throws ScribbleException
	{
		RoleNode src = (RoleNode) visitChild(getSourceChild(), nv);
		MessageNode msg = (MessageNode) visitChild(getMessageNodeChild(), nv);
		RoleNode dest = (RoleNode) visitChild(getDestinationChild(), nv);
		return reconstruct(src, msg, dest);
	}
	
	protected boolean isUnitMessage()
	{
		if (!this.msg.isMessageSigNode())
		{
			return false;
		}
		MessageSigNode msn = (MessageSigNode) this.msg;
		return msn.getOpChild().isEmpty() && msn.getPayloadListChild().isEmpty();
	}
	
	@Override
	public abstract String toString();
	
	
	
	
	
	
	
	
	
	
	
	public final RoleNode src;
	public final MessageNode msg;
	public final RoleNode dest;

	protected ConnectAction(CommonTree source, RoleNode src, MessageNode msg, RoleNode dest)
	//protected ConnectionAction(RoleNode src, RoleNode dest)
	{
		super(source);
		this.src = src;
		this.msg = msg;
		this.dest = dest;
	}
}
