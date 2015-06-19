package org.scribble.ast.name.simple;

import org.scribble.ast.DoArgNode;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.RoleKind;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.Substitutor;


//public class RoleNode extends SimpleNameNode<Role> implements InstantiationNode //RoleDecl, RoleInstantiation
//public class RoleNode extends SimpleNameNode<Role, RoleKind> implements InstantiationNode //RoleDecl, RoleInstantiation
public class RoleNode extends SimpleNameNode<RoleKind> implements DoArgNode //RoleDecl, RoleInstantiation
{
	public RoleNode(String identifier)
	{
		super(identifier);
	}

	//@Override
	private RoleNode reconstruct(String identifier)
	{
		ScribDel del = del();  // Default delegate assigned in ModelFactoryImpl for all simple names
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
