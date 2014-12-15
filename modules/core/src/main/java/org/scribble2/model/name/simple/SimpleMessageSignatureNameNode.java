package org.scribble2.model.name.simple;

import org.scribble2.model.MessageNode;
import org.scribble2.sesstype.Argument;
import org.scribble2.sesstype.Message;
import org.scribble2.sesstype.name.MessageSignatureName;

public class SimpleMessageSignatureNameNode extends SimpleNameNode implements MessageNode//SimpleMemberNameNode
{
	//public final String extType;  // Not current considered for equals/hashCode

	//public SimplePayloadTypeNode(CommonTree ct, String name, String extType)
	public SimpleMessageSignatureNameNode(String identifier)
	{
		super(identifier);
		//this.extType = extType;
	}

	@Override
	protected SimpleMessageSignatureNameNode copy()
	{
		return new SimpleMessageSignatureNameNode(this.identifier);
	}

	@Override
	public boolean isMessageSignatureNode()
	{
		return false;
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

	@Override
	public MessageSignatureName toName()
	{
		return new MessageSignatureName(this.identifier);
	}

	@Override
	public Argument toArgument()
	{
		throw new RuntimeException("TODO");
	}

	@Override
	public Message toMessage()
	{
		throw new RuntimeException("TODO");
	}
}
