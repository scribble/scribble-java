package org.scribble2.model;

import org.scribble2.model.name.simple.OperatorNode;
import org.scribble2.sesstype.MessageSig;

public class MessageSigNode extends ModelNodeBase implements MessageNode
{
	public final OperatorNode op;
	public final PayloadNode payload;

	public MessageSigNode(OperatorNode op, PayloadNode payload)
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
	public boolean isParameterNode()
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
	}
	
	@Override
	public MessageSignatureNode visitChildren(NodeVisitor nv) throws ScribbleException
	{
		OperatorNode op = (OperatorNode) visitChild(this.op, nv);
		Payload payload = (Payload) visitChild(this.payload, nv);
		return new MessageSignatureNode(this.ct, op, payload);
	}
	
	/*@Override 
	public Operator getOperator()
	{
		return this.op;
	}*/

	// FIXME: make a direct scoped version (taking scope as argument)
	@Override
	//public MessageSignature toArgument(Scope scope)
	public MessageSig toArgument()
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
		return toArgument();
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
