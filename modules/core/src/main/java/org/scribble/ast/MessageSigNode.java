package org.scribble.ast;

import org.scribble.ast.name.simple.OpNode;
import org.scribble.del.ScribDel;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.MessageSig;
import org.scribble.visit.AstVisitor;

public class MessageSigNode extends ScribNodeBase implements MessageNode
{
	public final OpNode op;
	public final PayloadElemList payload;

	public MessageSigNode(OpNode op, PayloadElemList payload)
	{
		this.op = op;
		this.payload = payload;
	}

	@Override
	protected MessageSigNode copy()
	{
		return new MessageSigNode(this.op, this.payload);
	}
	
	protected MessageSigNode reconstruct(OpNode op, PayloadElemList payload)
	{
		ScribDel del = del();	
		MessageSigNode msn = new MessageSigNode(op, payload);
		msn = (MessageSigNode) msn.del(del);
		return msn;
	}
	
	@Override
	public MessageSigNode visitChildren(AstVisitor nv) throws ScribbleException
	{
		OpNode op = (OpNode) visitChild(this.op, nv);
		PayloadElemList payload = (PayloadElemList) visitChild(this.payload, nv);
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
		return new MessageSig(this.op.toName(), this.payload.toPayload());
	}

	@Override
	public MessageSig toMessage()
	{
		return toArg();
	}

	@Override
	public String toString()
	{
		return this.op.toString() + this.payload.toString();
	}
}
