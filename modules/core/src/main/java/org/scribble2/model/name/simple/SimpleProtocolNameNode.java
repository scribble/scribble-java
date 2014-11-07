package org.scribble2.model.name.simple;

import org.scribble2.sesstype.name.ProtocolName;



public class SimpleProtocolNameNode extends SimpleNameNode //SimpleMemberNameNode
{
	public SimpleProtocolNameNode(String identifier)
	{
		super(identifier);
	}

	@Override
	protected SimpleProtocolNameNode copy()
	{
		return new SimpleProtocolNameNode(this.identifier);
	}

	@Override
	public ProtocolName toName()
	{
		return new ProtocolName(this.identifier);
	}

	/*@Override
	public PrimitiveNameNode toPrimitiveNameNode()
	{
		return (PrimitiveNameNode) this;
	}*/
}
