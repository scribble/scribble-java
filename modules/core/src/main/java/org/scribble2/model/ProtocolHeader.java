package org.scribble2.model;

import org.scribble2.model.name.qualified.SimpleProtocolNameNode;
import org.scribble2.model.visit.ModelVisitor;
import org.scribble2.sesstype.kind.ProtocolKind;
import org.scribble2.sesstype.name.ProtocolName;
import org.scribble2.util.ScribbleException;


// TODO: parameterize on global/local name node and role decl list (i.e. self roles)
public abstract class ProtocolHeader
		//extends ModelNodeBase
		extends NameDeclNode<ProtocolKind>
{
	//public final SimpleProtocolNameNode name;
	public final RoleDeclList roledecls;
	public final ParamDeclList paramdecls;

	// Maybe deprecate SimpleProtocolNameNode
	protected ProtocolHeader(SimpleProtocolNameNode name, RoleDeclList roledecls, ParamDeclList paramdecls)
	{
		//this.name = name;
		super(name);
		this.roledecls = roledecls;
		this.paramdecls = paramdecls;
	}
	
	protected abstract ProtocolHeader reconstruct(SimpleProtocolNameNode name, RoleDeclList rdl, ParamDeclList pdl);
	
	@Override
	public ProtocolHeader visitChildren(ModelVisitor nv) throws ScribbleException
	{
		RoleDeclList rdl = (RoleDeclList) visitChild(this.roledecls, nv);
		ParamDeclList pdl = (ParamDeclList) visitChild(this.paramdecls, nv);
		return reconstruct((SimpleProtocolNameNode) this.name, rdl, pdl);
	}

	public boolean isParameterDeclListEmpty()
	{
		return this.paramdecls.isEmpty();
	}
	
	@Override
	public ProtocolName getDeclName()
	{
		return (ProtocolName) super.getDeclName();
	}

	@Override
	public String toString()
	{
		String s = Constants.PROTOCOL_KW + " " + this.name;
		if (!isParameterDeclListEmpty())
		{
			s += this.paramdecls;
		}
		return s + this.roledecls;
	}
}
