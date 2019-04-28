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

import org.antlr.runtime.Token;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.core.type.kind.ProtoKind;
import org.scribble.core.type.name.Role;
import org.scribble.util.ScribException;
import org.scribble.visit.AstVisitor;

// FIXME: rename (Simple)Interaction, after existing SimpleInteraction renamed
public abstract class DirectedInteraction<K extends ProtoKind>
		extends BasicInteraction<K>
{
	public static final int MSG_CHILD_INDEX = 0;
	public static final int SRC_CHILD_INDEX = 1;
	public static final int DST_CHILDREN_START_INDEX = 2;

	// ScribTreeAdaptor#create constructor
	public DirectedInteraction(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public DirectedInteraction(DirectedInteraction<K> node)
	{
		super(node);
	}
	
	public MsgNode getMessageNodeChild()
	{
		return (MsgNode) getChild(MSG_CHILD_INDEX);
	}
	
	public RoleNode getSourceChild()
	{
		return (RoleNode) getChild(SRC_CHILD_INDEX);
	}

	public List<RoleNode> getDestinationChildren()
	{
		List<? extends ScribNode> cs = getChildren();
		return cs.subList(DST_CHILDREN_START_INDEX, cs.size()).stream()
				.map(x -> (RoleNode) x).collect(Collectors.toList());
	}
	
	public final List<Role> getDestinationRoles()
	{
		return getDestinationChildren().stream().map(rn -> rn.toName())
				.collect(Collectors.toList());
	}

	// "add", not "set"
	public void addScribChildren(MsgNode msg, RoleNode src, List<RoleNode> dsts)
	{
		// Cf. above getters and Scribble.g children order
		addChild(msg);
		addChild(src);
		addChildren(dsts);
	}
	
	public abstract DirectedInteraction<K> dupNode();

	public DirectedInteraction<K> reconstruct(MsgNode msg, RoleNode src,
			List<RoleNode> dsts)
	{
		DirectedInteraction<K> dup = dupNode();
		// Same order as getter indices
		dup.addScribChildren(msg, src, dsts);
		dup.setDel(del());  // No copy
		return dup;
	}

	@Override
	public DirectedInteraction<K> visitChildren(AstVisitor nv)
			throws ScribException
	{
		MsgNode msg = (MsgNode) visitChild(getMessageNodeChild(), nv);
		RoleNode src = (RoleNode) visitChild(getSourceChild(), nv);
		List<RoleNode> dests = visitChildListWithClassEqualityCheck(this,
				getDestinationChildren(), nv);
		return reconstruct(msg, src, dests);
	}
}

