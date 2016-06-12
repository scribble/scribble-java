package org.scribble.ast;

import org.scribble.ast.name.qualified.GProtocolNameNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ScribDel;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.name.DelegationType;
import org.scribble.sesstype.name.PayloadType;
import org.scribble.visit.AstVisitor;

// A "binary name pair" payload elem (current AST hierarchy induces this pattern), cf. UnaryPayloadElem (also differs in no parsing ambig against parameters)
// The this.name will be global kind, but overall this node is local kind
//public class DelegationElem extends PayloadElem<Local>
public class DelegationElem extends ScribNodeBase implements PayloadElem
{
  // Currently no potential for ambiguity, cf. UnaryPayloadElem (DataTypeNameNode or ParameterNode)
	public final GProtocolNameNode proto;
	public final RoleNode role;
	
	public DelegationElem(GProtocolNameNode proto, RoleNode role)
	{
		//super(proto);
		this.proto = proto;
		this.role = role;
	}

	@Override
	public boolean isDelegationElem()
	{
		return true;
	}

	@Override
	protected DelegationElem copy()
	{
		return new DelegationElem(this.proto, this.role);
	}
	
	@Override
	public DelegationElem clone()
	{
		GProtocolNameNode name = (GProtocolNameNode) proto.clone();
		RoleNode role = (RoleNode) this.role.clone();
		return AstFactoryImpl.FACTORY.DelegationElem(name, role);
	}

	public DelegationElem reconstruct(GProtocolNameNode proto, RoleNode role)
	{
		ScribDel del = del();
		DelegationElem elem = new DelegationElem(proto, role);
		elem = (DelegationElem) elem.del(del);
		return elem;
	}

	@Override
	public DelegationElem visitChildren(AstVisitor nv) throws ScribbleException
	{
		GProtocolNameNode name = (GProtocolNameNode) visitChild(this.proto, nv);
		RoleNode role = (RoleNode) visitChild(this.role, nv);
		return reconstruct(name, role);
	}
	
	@Override
	public String toString()
	{
		return this.proto + "@" + this.role;
	}

	@Override
	public PayloadType<Local> toPayloadType()
	{
		return new DelegationType(this.proto.toName(), this.role.toName());
	}
}
