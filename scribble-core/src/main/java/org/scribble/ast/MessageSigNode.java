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
import org.scribble.ast.name.simple.OpNode;
import org.scribble.del.ScribDel;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.MessageSig;
import org.scribble.visit.AstVisitor;

public class MessageSigNode extends ScribNodeBase implements MessageNode
{
	public final OpNode op;
	public final PayloadElemList payloads;

	public MessageSigNode(CommonTree source, OpNode op, PayloadElemList payload)
	{
		super(source);
		this.op = op;
		this.payloads = payload;
	}
	
	@Override
	public MessageNode project(AstFactory af)  // Currently outside of visitor/env pattern
	{
		return af.MessageSigNode(this.source, this.op, this.payloads.project(af));  // Original del not retained by projection
	}

	@Override
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
	}
	
	public MessageSigNode reconstruct(OpNode op, PayloadElemList payload)
	{
		ScribDel del = del();	
		MessageSigNode msn = new MessageSigNode(this.source, op, payload);
		msn = (MessageSigNode) msn.del(del);
		return msn;
	}
	
	@Override
	public MessageSigNode visitChildren(AstVisitor nv) throws ScribbleException
	{
		OpNode op = (OpNode) visitChild(this.op, nv);
		PayloadElemList payload = (PayloadElemList) visitChild(this.payloads, nv);
		return reconstruct(op, payload);
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
		return new MessageSig(this.op.toName(), this.payloads.toPayload());
	}

	@Override
	public MessageSig toMessage()
	{
		return toArg();
	}

	@Override
	public String toString()
	{
		return this.op.toString() + this.payloads.toString();
	}
}
