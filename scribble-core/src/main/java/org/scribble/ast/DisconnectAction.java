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
import org.scribble.job.ScribbleException;
import org.scribble.type.kind.ProtocolKind;
import org.scribble.visit.AstVisitor;

// (G)Disconnect is symmetric (send/receive asymmetric/async; request/accept asymmetric/sync; (g)disconnect symmetric/async)
// However, LDisconnect is "symmetric" but self-oriented -- "left" used for self
public abstract class DisconnectAction<K extends ProtocolKind>
		extends SimpleInteractionNode<K>
{
	// ScribTreeAdaptor#create constructor
	public DisconnectAction(Token t)
	{
		super(t);
		this.left = null;
		this.right = null;
	}

	// Tree#dupNode constructor
	public DisconnectAction(DisconnectAction<K> node)
	{
		super(node);
		this.left = null;
		this.right = null;
	}
	
	public RoleNode getLeftChild()
	{
		return (RoleNode) getChild(0);
	}

	public RoleNode getRightChild()
	{
		return (RoleNode) getChild(1);
	}
	
	public abstract DisconnectAction<K> dupNode();

	public DisconnectAction<K> reconstruct(RoleNode left, RoleNode right)
	{
		DisconnectAction<K> da = dupNode();
		da.addChild(left);
		da.addChild(right);
		ScribDel del = del();
		da.setDel(del);  // No copy
		return da;
	}

	@Override
	public DisconnectAction<K> visitChildren(AstVisitor nv)
			throws ScribbleException
	{
		RoleNode src = (RoleNode) visitChild(getLeftChild(), nv);
		RoleNode dest = (RoleNode) visitChild(getRightChild(), nv);
		return reconstruct(src, dest);
	}
	
	@Override
	public abstract String toString();
	
	
	
	
	
	
	
	
	
	
	
	
	
	public final RoleNode left;
	public final RoleNode right;

	protected DisconnectAction(CommonTree source, RoleNode src, RoleNode dest)
	{
		super(source);
		this.left = src;
		this.right = dest;
	}
}
