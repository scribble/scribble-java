package org.scribble.ast;

import org.scribble.ast.del.ModelDel;
import org.scribble.ast.name.simple.OpNode;
import org.scribble.ast.visit.ModelVisitor;
import org.scribble.sesstype.MessageSig;
import org.scribble.util.ScribbleException;

public class MessageSigNode extends ModelNodeBase implements MessageNode
{
	public final OpNode op;
	public final PayloadElemList payload;

	public MessageSigNode(OpNode op, PayloadElemList payload)
	{
		this.op = op;
		this.payload = payload;
	}

	@Override
	public boolean isMessageSigNode()
	{
		return true;
	}

	@Override
	public boolean isMessageSigNameNode()
	{
		return false;
	}

	@Override
	public boolean isDataTypeNameNode()
	{
		return false;
	}

	@Override
	public boolean isParamNode()
	{
		return false;
	}

	/*// Basically a copy without the AST
	@Override
	public MessageSignatureNode leaveProjection(Projector proj) //throws ScribbleException
	{
		OperatorNode op = new OperatorNode(null, this.op.toName().toString());
		Payload payload = (Payload) ((ProjectionEnv) this.payload.getEnv()).getProjection();	
		MessageSignatureNode projection = new MessageSignatureNode(null, op, payload);
		this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleContext(), projection));
		return this;
	}*/
	
	protected MessageSigNode reconstruct(OpNode op, PayloadElemList payload)
	{
		ModelDel del = del();	
		MessageSigNode msn = new MessageSigNode(op, payload);
		msn = (MessageSigNode) msn.del(del);
		return msn;
	}
	
	@Override
	public MessageSigNode visitChildren(ModelVisitor nv) throws ScribbleException
	{
		OpNode op = (OpNode) visitChild(this.op, nv);
		PayloadElemList payload = (PayloadElemList) visitChild(this.payload, nv);
		return reconstruct(op, payload);
	}
	
	/*@Override 
	public Operator getOperator()
	{
		return this.op;
	}*/

	// FIXME: make a direct scoped version (taking scope as argument)
	@Override
	//public MessageSignature toArgument(Scope scope)
	public MessageSig toArg()
	{
		/*List<PayloadType<? extends Kind>> types = this.payload.payloadelems.stream().map((pe) -> pe.name.toPayloadTypeOrParameter()).collect(Collectors.toList());
		return new MessageSignature(this.op.toName(), payload);*/
		//return new MessageSignature(scope, this.op.toName(), this.payload.toPayload());
		return new MessageSig(this.op.toName(), this.payload.toPayload());
	}

	@Override
	//public MessageSignature toMessage(Scope scope)
	public MessageSig toMessage()
	{
		return toArg();
	}

	/*@Override
	public boolean isAmbiguousNode()
	{
		return false;
	}*/

	@Override
	public String toString()
	{
		return this.op.toString() + this.payload.toString();
	}

	@Override
	protected MessageSigNode copy()
	{
		return new MessageSigNode(this.op, this.payload);
	}
}
