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
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.core.type.kind.ProtoKind;
import org.scribble.util.Constants;
import org.scribble.util.ScribException;
import org.scribble.visit.AstVisitor;

// (G)Disconnect is symmetric (send/receive asymmetric/async; request/accept asymmetric/sync; (g)disconnect symmetric/async)
// However, LDisconnect is "symmetric" but self-oriented -- "left" used for self
public abstract class DisconnectAction<K extends ProtoKind>
		extends BasicInteraction<K>
{
	public static final int LEFT_CHILD_INDEX = 0;
	public static final int RIGHT_CHILD_INDEX = 1;

	// ScribTreeAdaptor#create constructor
	public DisconnectAction(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public DisconnectAction(DisconnectAction<K> node)
	{
		super(node);
	}
	
	public RoleNode getLeftChild()
	{
		return (RoleNode) getChild(LEFT_CHILD_INDEX);
	}

	public RoleNode getRightChild()
	{
		return (RoleNode) getChild(RIGHT_CHILD_INDEX);
	}

	// "add", not "set"
	public void addScribChildren(RoleNode left, RoleNode right)
	{
		// Cf. above getters and Scribble.g children order
		addChild(left);
		addChild(right);
	}
	
	public abstract DisconnectAction<K> dupNode();

	public DisconnectAction<K> reconstruct(RoleNode left, RoleNode right)
	{
		DisconnectAction<K> dup = dupNode();
		dup.addScribChildren(left, right);
		dup.setDel(del());  // No copy
		return dup;
	}

	@Override
	public DisconnectAction<K> visitChildren(AstVisitor nv)
			throws ScribException
	{
		RoleNode src = (RoleNode) visitChild(getLeftChild(), nv);
		RoleNode dest = (RoleNode) visitChild(getRightChild(), nv);
		return reconstruct(src, dest);
	}

	@Override
	public String toString()
	{
		return Constants.DISCONNECT_KW + " " + getLeftChild()
				+ " " + Constants.AND_KW + " " + getRightChild() + ";";
	}
}
