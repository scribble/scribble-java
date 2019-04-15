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
import org.scribble.ast.name.simple.OpNode;
import org.scribble.core.type.session.SigLit;
import org.scribble.util.ScribException;
import org.scribble.visit.AstVisitor;

public class SigLitNode extends ScribNodeBase implements MsgNode
{
	public static final int OP_CHILD_INDEX = 0;
	public static final int PAYLOAD_CHILD_INDEX = 1;

	// ScribTreeAdaptor#create constructor
	public SigLitNode(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public SigLitNode(SigLitNode node)
	{
		super(node);
	}
	
	public OpNode getOpChild()
	{
		return (OpNode) getChild(OP_CHILD_INDEX);
	}
	
	public PayElemList getPayloadListChild()
	{
		return (PayElemList) getChild(PAYLOAD_CHILD_INDEX);
	}
	
	@Override
	public SigLitNode dupNode()
	{
		return new SigLitNode(this);
	}

	public SigLitNode reconstruct(OpNode op, PayElemList payload)
	{
		SigLitNode sig = dupNode();
		sig.addChild(op);
		sig.addChild(payload);
		sig.setDel(del());  // No copy
		return sig;
	}
	
	@Override
	public SigLitNode visitChildren(AstVisitor nv) throws ScribException
	{
		OpNode op = (OpNode) visitChild(getOpChild(), nv);
		PayElemList pay = (PayElemList) 
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
	public SigLit toArg()
	{
		return new SigLit(getOpChild().toName(),
				getPayloadListChild().toPayload());
	}

	@Override
	public SigLit toMessage()
	{
		return toArg();
	}

	@Override
	public String toString()
	{
		return getOpChild().toString() + getPayloadListChild().toString();
	}
}
