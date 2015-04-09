package org.scribble2.model.name.simple;

import org.scribble2.model.del.ModelDelegate;
import org.scribble2.sesstype.name.ProtocolName;



public class SimpleProtocolNameNode extends SimpleNameNode //SimpleMemberNameNode
{
	public SimpleProtocolNameNode(String identifier)
	{
		super(identifier);
	}

	@Override
	protected SimpleProtocolNameNode reconstruct(String identifier)
	{
		ModelDelegate del = del();  // Default delegate assigned in ModelFactoryImpl for all simple names
		SimpleProtocolNameNode spnn = new SimpleProtocolNameNode(identifier);
		spnn = (SimpleProtocolNameNode) spnn.del(del);
		return spnn;
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