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
import org.scribble.ast.name.simple.OpNode;
import org.scribble.core.type.session.MessageSig;
import org.scribble.del.ScribDel;
import org.scribble.util.ScribException;
import org.scribble.visit.AstVisitor;

public class MessageSigNode extends ScribNodeBase implements MessageNode
{
	// ScribTreeAdaptor#create constructor
	public MessageSigNode(Token t)
	{
		super(t);
		this.op = null;
		this.payloads = null;
	}

	// Tree#dupNode constructor
	public MessageSigNode(MessageSigNode node)
	{
		super(node);
		this.op = null;
		this.payloads = null;
	}
	
	public OpNode getOpChild()
	{
		return (OpNode) getChild(0);
	}
	
	public PayloadElemList getPayloadListChild()
	{
		return (PayloadElemList) getChild(1);
	}
	
	@Override
	public MessageSigNode dupNode()
	{
		return new MessageSigNode(this);
	}

	public MessageSigNode reconstruct(OpNode op, PayloadElemList payload)
	{
		MessageSigNode sig = dupNode();
		sig.addChild(op);
		sig.addChild(payload);
		ScribDel del = del();
		sig.setDel(del);  // No copy
		return sig;
	}
	
	@Override
	public MessageSigNode visitChildren(AstVisitor nv) throws ScribException
	{
		OpNode op = (OpNode) visitChild(getOpChild(), nv);
		PayloadElemList pay = (PayloadElemList) 
				visitChild(getPayloadListChild(), nv);
		return reconstruct(op, pay);
	}

	@Override
	public boolean isMessageSigNode()
	{
		return true;
	}

	// Make a direct scoped version? (taking scope as argument)
	@Override
	public MessageSig toArg()
	{
		return new MessageSig(getOpChild().toName(),
				getPayloadListChild().toPayload());
	}

	@Override
	public MessageSig toMessage()
	{
		return toArg();
	}

	@Override
	public String toString()
	{
		return getOpChild().toString() + getPayloadListChild().toString();
	}

	
	
	
	
	
	
	
	
	
	
	private final OpNode op;
	private final PayloadElemList payloads;

	public MessageSigNode(CommonTree source, OpNode op, PayloadElemList payload)
	{
		super(source);
		this.op = op;
		this.payloads = payload;
	}

	/*@Override
	protected MessageSigNode copy()
	{
		return new MessageSigNode(this.source, this.op, this.payloads);
	}	

	@Override
	public MessageSigNode clone(AstFactory af)
	{
		OpNode op = this.op.clone(af);
		PayloadElemList payload = this.payloads.clone(af);
		return af.MessageSigNode(this.source, op, payload);
	}*/
	
	
}
