package org.scribble2.model.name.simple;

import org.scribble2.model.InstantiationNode;
import org.scribble2.model.del.ModelDel;
import org.scribble2.model.visit.Substitutor;
import org.scribble2.sesstype.kind.RoleKind;
import org.scribble2.sesstype.name.Role;


//public class RoleNode extends SimpleNameNode<Role> implements InstantiationNode //RoleDecl, RoleInstantiation
//public class RoleNode extends SimpleNameNode<Role, RoleKind> implements InstantiationNode //RoleDecl, RoleInstantiation
public class RoleNode extends SimpleNameNode<RoleKind> implements InstantiationNode //RoleDecl, RoleInstantiation
{
	public RoleNode(String identifier)
	{
		super(identifier);
	}

	//@Override
	private RoleNode reconstruct(String identifier)
	{
		ModelDel del = del();  // Default delegate assigned in ModelFactoryImpl for all simple names
		RoleNode rn = new RoleNode(identifier);
		rn = (RoleNode) rn.del(del);
		return rn;
	}
	
	@Override
	public RoleNode substituteNames(Substitutor subs)
	{
		//return subs.getRoleSubstitution(toName());
		return reconstruct(subs.getRoleSubstitution(toName()).toString());
	}

	@Override
	protected RoleNode copy()  // Specified to be internal shallow copy (e.g. used by del)
	{
		return new RoleNode(getIdentifier());
		//return reconstruct(this.identifier);
	}
	
	@Override
	public Role toName()
	{
		return new Role(getIdentifier());
	}
}
