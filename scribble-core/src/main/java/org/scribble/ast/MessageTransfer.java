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
import org.scribble.del.ScribDel;
import org.scribble.main.ScribbleException;
import org.scribble.type.kind.ProtocolKind;
import org.scribble.type.name.Role;
import org.scribble.visit.AstVisitor;

public abstract class MessageTransfer<K extends ProtocolKind> extends SimpleInteractionNode<K>
{
	// ScribTreeAdaptor#create constructor
	public MessageTransfer(Token t)
	{
		super(t);
		this.src = null;
		this.msg = null;
		this.dests = null;
	}

	// Tree#dupNode constructor
	public MessageTransfer(MessageTransfer<K> node)
	{
		super(node);
		this.src = null;
		this.msg = null;
		this.dests = null;
	}
	
	public abstract MessageTransfer<K> dupNode();
	
	public MessageNode getMessageNodeChild()
	{
		return (MessageNode) getChild(0);
	}
	
	public RoleNode getSourceChild()
	{
		return (RoleNode) getChild(1);
	}

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

	public MessageTransfer<K> reconstruct(RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		MessageTransfer<K> pd = dupNode();
		ScribDel del = del();
		pd.setDel(del);  // No copy
		return pd;
	}

	@Override
	public MessageTransfer<K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		MessageNode msg = (MessageNode) visitChild(getMessageNodeChild(), nv);
		RoleNode src = (RoleNode) visitChild(getSourceChild(), nv);
		List<RoleNode> dests = visitChildListWithClassEqualityCheck(this,
				getDestinationChildren(), nv);
		return reconstruct(src, msg, dests);
	}
	
	@Override
	public abstract String toString();
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private final RoleNode src;
	private final MessageNode msg;  // CHECKME: ambig may get resolved to an unexpected kind, e.g. DataTypeNode (cf. DoArg, PayloadElem wrappers)
	private final List<RoleNode> dests;

	protected MessageTransfer(CommonTree source, RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		super(source);
		this.src = src;
		this.msg = msg;
		this.dests = new LinkedList<>(dests);  // CHECKME: Collections.unmodifiable?
	}

}

