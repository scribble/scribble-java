package org.scribble2.model;

import org.scribble2.model.name.simple.SimpleProtocolNameNode;


// TODO: parameterize on global/local role decl list
public abstract class ProtocolHeader extends ModelNodeBase
{
	public final SimpleProtocolNameNode name;
	public final RoleDeclList roledecls;
	public final ParameterDeclList paramdecls;

	protected ProtocolHeader(SimpleProtocolNameNode name, RoleDeclList roledecls, ParameterDeclList paramdecls)
	{
		this.name = name;
		this.roledecls = roledecls;
		this.paramdecls = paramdecls;
	}
	
	/*protected abstract ProtocolDefinition<T> reconstruct(CommonTree ct, T block);
	
	@Override
	//public ProtocolDefinition<T> visitChildren(NodeVisitor nv) throws ScribbleException
	public ProtocolDefinition<T> visitChildren(NodeVisitor nv) throws ScribbleException
	{
		T block = visitChildWithClassCheck(this, this.block, nv);
		//return new ProtocolDefinition<T>(this.ct, block);
		return reconstruct(this.ct, block);
	}*/

	/*public boolean isParameterDeclListEmpty()
	{
		return this.paramdecls.isEmpty();
	}

	@Override
	public String toString()
	{
		String s = AntlrConstants.PROTOCOL_KW + " " + this.name;
		if (!isParameterDeclListEmpty())
		{
			s += this.paramdecls;
		}
		return s + this.roledecls;
	}*/
}
