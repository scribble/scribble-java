package org.scribble2.model.name.simple;

import org.scribble2.model.del.ModelDel;
import org.scribble2.sesstype.name.Kind;
import org.scribble2.sesstype.name.ProtocolName;
import org.scribble2.sesstype.name.SimpleName;



public class SimpleProtocolNameNode extends SimpleCompoundNameNode<ProtocolName> //SimpleMemberNameNode
{
	public SimpleProtocolNameNode(String identifier)
	{
		super(identifier);
	}

	@Override
	protected SimpleProtocolNameNode reconstruct(String identifier)
	{
		ModelDel del = del();  // Default delegate assigned in ModelFactoryImpl for all simple names
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
	public SimpleName toName()
	{
		return new SimpleName(Kind.PROTOCOL, this.identifier);
	}

	@Override
	public ProtocolName toCompoundName()
	{
		return new ProtocolName(this.identifier);
	}

	/*@Override
	public PrimitiveNameNode toPrimitiveNameNode()
	{
		return (PrimitiveNameNode) this;
	}*/
}
