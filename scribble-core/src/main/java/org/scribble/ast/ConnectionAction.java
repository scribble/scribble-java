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

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.main.ScribbleException;
import org.scribble.type.kind.ProtocolKind;
import org.scribble.visit.AstVisitor;

// FIXME: factor with MessageTransfer
public abstract class ConnectionAction<K extends ProtocolKind> extends SimpleInteractionNode<K>
{
	public final RoleNode src;
	public final MessageNode msg;
	public final RoleNode dest;

	protected ConnectionAction(CommonTree source, RoleNode src, MessageNode msg, RoleNode dest)
	//protected ConnectionAction(RoleNode src, RoleNode dest)
	{
		super(source);
		this.src = src;
		this.msg = msg;
		this.dest = dest;
	}

	public abstract ConnectionAction<K> reconstruct(RoleNode src, MessageNode msg, RoleNode dest);
	//public abstract ConnectionAction<K> reconstruct(RoleNode src, RoleNode dest);

	@Override
	public ConnectionAction<K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		RoleNode src = (RoleNode) visitChild(this.src, nv);
		MessageNode msg = (MessageNode) visitChild(this.msg, nv);
		RoleNode dest = (RoleNode) visitChild(this.dest, nv);
		return reconstruct(src, msg, dest);
		//return reconstruct(src, dest);
	}
	
	@Override
	public abstract String toString();
	
	protected boolean isUnitMessage()
	{
		if (!this.msg.isMessageSigNode())
		{
			return false;
		}
		MessageSigNode msn = (MessageSigNode) this.msg;
		return msn.op.isEmpty() && msn.payloads.isEmpty();
	}
}
