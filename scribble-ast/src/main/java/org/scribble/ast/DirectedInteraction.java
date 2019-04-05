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

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.core.job.ScribbleException;
import org.scribble.core.type.kind.ProtocolKind;
import org.scribble.core.type.name.Role;
import org.scribble.del.ScribDel;
import org.scribble.visit.AstVisitor;

// FIXME: rename (Simple)Interaction, after existing SimpleInteraction renamed
public abstract class DirectedInteraction<K extends ProtocolKind>
		extends BasicInteraction<K>
{
	// ScribTreeAdaptor#create constructor
	public DirectedInteraction(Token t)
	{
		super(t);
		this.src = null;
		this.msg = null;
		this.dests = null;
	}

	// Tree#dupNode constructor
	public DirectedInteraction(DirectedInteraction<K> node)
	{
		super(node);
		this.src = null;
		this.msg = null;
		this.dests = null;
	}
	
	public MessageNode getMessageNodeChild()
	{
		return (MessageNode) getChild(0);
	}
	
	public RoleNode getSourceChild()
	{
		return (RoleNode) getChild(1);
	}

	// CHECKME:  OK for (future) connect?
	public List<RoleNode> getDestinationChildren()
	{
		//return Collections.unmodifiableList(this.dests);
		List<ScribNode> cs = getChildren();
		return cs.subList(2, cs.size()).stream().map(x -> (RoleNode) x)
				.collect(Collectors.toList());
	}
	
	public final List<Role> getDestinationRoles()
	{
		return getDestinationChildren().stream().map(rn -> rn.toName())
				.collect(Collectors.toList());
	}
	
	public abstract DirectedInteraction<K> dupNode();

	public DirectedInteraction<K> reconstruct(MessageNode msg, RoleNode src,
			List<RoleNode> dests)
	{
		DirectedInteraction<K> mt = dupNode();
		// Same order as getter indices
		mt.addChild(msg);
		mt.addChild(src);
		mt.addChildren(dests);
		ScribDel del = del();
		mt.setDel(del);  // No copy
		return mt;
	}

	@Override
	public DirectedInteraction<K> visitChildren(AstVisitor nv)
			throws ScribbleException
	{
		MessageNode msg = (MessageNode) visitChild(getMessageNodeChild(), nv);
		RoleNode src = (RoleNode) visitChild(getSourceChild(), nv);
		List<RoleNode> dests = visitChildListWithClassEqualityCheck(this,
				getDestinationChildren(), nv);
		return reconstruct(msg, src, dests);
	}
	
	@Override
	public abstract String toString();
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private final RoleNode src;
	private final MessageNode msg;  // CHECKME: ambig may get resolved to an unexpected kind, e.g. DataTypeNode (cf. DoArg, PayloadElem wrappers)
	private final List<RoleNode> dests;

	protected DirectedInteraction(CommonTree source, RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		super(source);
		this.src = src;
		this.msg = msg;
		this.dests = new LinkedList<>(dests);  // CHECKME: Collections.unmodifiable?
	}

}

