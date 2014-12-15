package org.scribble2.model;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble2.model.name.simple.OperatorNode;
import org.scribble2.sesstype.MessageSignature;
import org.scribble2.sesstype.name.PayloadTypeOrParameter;

public class MessageSignatureNode extends ModelNodeBase implements MessageNode
{
	public final OperatorNode op;
	public final Payload payload;

	public MessageSignatureNode(OperatorNode op, Payload payload)
	{
		this.op = op;
		this.payload = payload;
	}

	@Override
	public boolean isMessageSignatureNode()
	{
		return true;
	}

	@Override
	public boolean isPayloadTypeNode()
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
	public MessageSignature toArgument()
	{
		List<PayloadTypeOrParameter> payload = this.payload.payloadelems.stream().map((pe) -> pe.name.toPayloadTypeOrParameter()).collect(Collectors.toList());
		return new MessageSignature(this.op.toName(), payload);
	}

	@Override
	public MessageSignature toMessage()
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
	protected MessageSignatureNode copy()
	{
		return new MessageSignatureNode(this.op, this.payload);
	}
}
