package org.scribble2.model.name.simple;

import org.scribble2.model.MessageNode;

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
	
	/*@Override
	public MessageSignatureName toName()
	{
		return new MessageSignatureName(this.identifier);
	}*/
}
