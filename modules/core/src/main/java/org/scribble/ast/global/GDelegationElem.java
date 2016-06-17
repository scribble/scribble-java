package org.scribble.ast.global;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.PayloadElem;
import org.scribble.ast.ScribNodeBase;
import org.scribble.ast.local.LDelegationElem;
import org.scribble.ast.name.qualified.GProtocolNameNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ScribDel;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.name.GDelegationType;
import org.scribble.sesstype.name.PayloadType;
import org.scribble.visit.AstVisitor;
import org.scribble.visit.Projector;

// A "binary name pair" payload elem (current AST hierarchy induces this pattern), cf. UnaryPayloadElem (also differs in no parsing ambig against parameters)
// The this.name will be global kind, but overall this node is local kind
//public class DelegationElem extends PayloadElem<Local>
public class GDelegationElem extends ScribNodeBase implements PayloadElem<Local>
{
  // Currently no potential for ambiguity, cf. UnaryPayloadElem (DataTypeNameNode or ParameterNode)
	public final GProtocolNameNode proto;  // Becomes full name after disambiguation
	public final RoleNode role;
	
	public GDelegationElem(GProtocolNameNode proto, RoleNode role)
	{
		//super(proto);
		this.proto = proto;
		this.role = role;
	}
	
	@Override
	public LDelegationElem project()
	{
		return AstFactoryImpl.FACTORY.LDelegationElem(Projector.makeProjectedFullNameNode(this.proto.toName(), this.role.toName()));
	}

	@Override
	public boolean isGlobalDelegationElem()
	{
		return true;
	}

	@Override
	protected GDelegationElem copy()
	{
		return new GDelegationElem(this.proto, this.role);
	}
	
	@Override
	public GDelegationElem clone()
	{
		GProtocolNameNode name = (GProtocolNameNode) this.proto.clone();
		RoleNode role = (RoleNode) this.role.clone();
		return AstFactoryImpl.FACTORY.GDelegationElem(name, role);
	}

	public GDelegationElem reconstruct(GProtocolNameNode proto, RoleNode role)
	{
		ScribDel del = del();
		GDelegationElem elem = new GDelegationElem(proto, role);
		elem = (GDelegationElem) elem.del(del);
		return elem;
	}

	@Override
	public GDelegationElem visitChildren(AstVisitor nv) throws ScribbleException
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
		return new GDelegationType(this.proto.toName(), this.role.toName());
	}
}
