package org.scribble2.model.local;

import org.scribble2.model.RoleDecl;
import org.scribble2.model.del.ModelDel;
import org.scribble2.model.name.NameNode;
import org.scribble2.model.name.simple.RoleNode;
import org.scribble2.sesstype.kind.RoleKind;
//import scribble2.sesstype.name.NameDeclaration;
import org.scribble2.sesstype.name.Role;

public class SelfRoleDecl extends RoleDecl
{
	//public SelfRoleDecl(RoleNode rn)
	//public SelfRoleDecl(NameNode<Role, RoleKind> rn)
	public SelfRoleDecl(RoleNode rn)
	{
		super(rn);
	}

	/*@Override
	public SelfRoleDecl visitChildren(ModelVisitor nv) throws ScribbleException
	{
		NameDecl<? extends PrimitiveNameNode> nd = super.visitChildren(nv);
		return new SelfRoleDecl(nd.ct, (RoleNode) nd.name);
	}*/
	
	@Override
	public SelfRoleDecl project(Role self)
	{
		throw new RuntimeException("Shouldn't get in here: " + this);
	}

	@Override
	//protected RoleDecl reconstruct(SimpleNameNode snn)
	//protected RoleDecl reconstruct(RoleNode snn)
	//protected RoleDecl reconstruct(NameNode<Role, RoleKind> snn)
	protected RoleDecl reconstruct(NameNode<RoleKind> snn)
	{
		ModelDel del = del();
		SelfRoleDecl rd = new SelfRoleDecl((RoleNode) snn);
		rd = (SelfRoleDecl) rd.del(del);
		return rd;
	}

	@Override
	protected SelfRoleDecl copy()
	{
		return new SelfRoleDecl((RoleNode) this.name);
	}
	
	@Override
	public boolean isSelfRoleDecl()
	{
		return true;
	}

	/*@Override
	public String toString()
	{
		return AntlrConstants.SELF_KW + " " + this.name;
	}*/
}
