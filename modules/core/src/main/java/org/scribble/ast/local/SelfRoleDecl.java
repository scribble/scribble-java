package org.scribble.ast.local;

import org.scribble.ast.RoleDecl;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.ast.name.simple.SimpleNameNode;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.RoleKind;
import org.scribble.sesstype.name.Role;

public class SelfRoleDecl extends RoleDecl
{
	public SelfRoleDecl(RoleNode rn)
	{
		super(rn);
	}

	@Override
	protected SelfRoleDecl copy()
	{
		return new SelfRoleDecl((RoleNode) this.name);
	}

	@Override
	public RoleDecl reconstruct(SimpleNameNode<RoleKind> name)
	{
		ScribDel del = del();
		SelfRoleDecl rd = new SelfRoleDecl((RoleNode) name);
		rd = (SelfRoleDecl) rd.del(del);
		return rd;
	}
	
	@Override
	public SelfRoleDecl project(Role self)
	{
		throw new RuntimeException("Shouldn't get in here: " + this);
	}
	
	@Override
	public boolean isSelfRoleDecl()
	{
		return true;
	}
}
