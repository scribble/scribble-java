package org.scribble2.model.name.qualified;



// N.B. not a SimpleNameNode
//public class SimpleProtocolNameNode extends SimpleCompoundNameNode<ProtocolName> //SimpleMemberNameNode
public class SimpleProtocolNameNode extends ProtocolNameNode
{
	public final String identifier;
	
	public SimpleProtocolNameNode(String identifier)
	{
		super(identifier);
		this.identifier = identifier;
	}

	/*@Override
	protected SimpleProtocolNameNode reconstruct(String identifier)
	{
		ModelDel del = del();  // Default delegate assigned in ModelFactoryImpl for all simple names
		SimpleProtocolNameNode spnn = new SimpleProtocolNameNode(identifier);
		spnn = (SimpleProtocolNameNode) spnn.del(del);
		return spnn;
	}*/

	@Override
	protected SimpleProtocolNameNode copy()
	{
		return new SimpleProtocolNameNode(this.identifier);
		//return new SimpleProtocolNameNode(getLastElement());
	}

	/*@Override
	public SimpleName toName()
	{
		return new SimpleName(KindEnum.PROTOCOL, this.identifier);
	}*/

	/*@Override
	public ProtocolName toCompoundName()
	{
		return new ProtocolName(this.identifier);
	}*/

	/*@Override
	public PrimitiveNameNode toPrimitiveNameNode()
	{
		return (PrimitiveNameNode) this;
	}*/
}
