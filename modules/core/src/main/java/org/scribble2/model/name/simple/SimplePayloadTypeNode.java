package org.scribble2.model.name.simple;

import org.scribble2.model.ArgumentInstantiation;

public class SimplePayloadTypeNode extends SimpleNameNode implements ArgumentInstantiation//, PayloadElementNameNode// SimpleMemberNameNode
{
	//public final String extType;  // Not current considered for equals/hashCode

	//public SimplePayloadTypeNode(CommonTree ct, String name, String extType)
	public SimplePayloadTypeNode(String name)
	{
		super(name);
		//this.extType = extType;
	}
	
	/*@Override
	public PayloadType toName()
	{
		return new PayloadType(this.identifier);
	}*/

	/*@Override
	public PayloadType toArgument()
	{
		return new PayloadType(this.identifier);
	}*/

	/*@Override
	public PrimitiveNameNode toPrimitiveNameNode()
	{
		return (PrimitiveNameNode) this;
	}*/

	/*@Override
	public boolean isMessageSignatureNode()
	{
		return false;
	}

	@Override
	public boolean isPayloadTypeNode()
	{
		return true;
	}

	@Override 
	public boolean isParameterNode()
	{
		return false;
	}

	@Override
	public boolean isAmbiguousNode()
	{
		return false;
	}*/
}
