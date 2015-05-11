package org.scribble2.model.name.simple;

import org.scribble2.model.del.ModelDel;
import org.scribble2.sesstype.name.DataType;
import org.scribble2.sesstype.name.SimpleName;

@Deprecated
public class SimplePayloadTypeNode extends SimpleCompoundNameNode<DataType> //implements ArgumentInstantiation//, PayloadElementNameNode// SimpleMemberNameNode
{
	//public final String extType;  // Not current considered for equals/hashCode

	//public SimplePayloadTypeNode(CommonTree ct, String name, String extType)
	public SimplePayloadTypeNode(String identifier)
	{
		super(identifier);
		//this.extType = extType;
	}

	/*@Override
	protected SimplePayloadTypeNode reconstruct(String identifier)
	{
		ModelDel del = del();  // Default delegate assigned in ModelFactoryImpl for all simple names
		SimplePayloadTypeNode sptn = new SimplePayloadTypeNode(identifier);
		sptn = (SimplePayloadTypeNode) sptn.del(del);
		return sptn;
	}

	@Override
	protected SimplePayloadTypeNode copy()
	{
		return new SimplePayloadTypeNode(this.identifier);
	}

	@Override
	public SimpleName toName()
	{
		// TODO Auto-generated method stub
		return null;
	}*/

	@Override
	public DataType toCompoundName()
	{
		// TODO Auto-generated method stub
		return null;
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
