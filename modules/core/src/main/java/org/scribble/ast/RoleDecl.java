package org.scribble.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.ast.name.simple.SimpleNameNode;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.RoleKind;
import org.scribble.sesstype.name.Name;
import org.scribble.sesstype.name.Role;

public class RoleDecl extends HeaderParamDecl<RoleKind>
{
	public RoleDecl(CommonTree source, RoleNode name)
	{
		super(source, name);
	}

	@Override
	protected RoleDecl copy()
	{
		return new RoleDecl(this.source, (RoleNode) this.name);
	}
	
	@Override
	public RoleDecl clone()
	{
		RoleNode role = (RoleNode) this.name.clone();
		return AstFactoryImpl.FACTORY.RoleDecl(this.source, role);
	}

	@Override
	public RoleDecl reconstruct(SimpleNameNode<RoleKind> name)
	{
		ScribDel del = del();
		RoleDecl rd = new RoleDecl(this.source, (RoleNode) name);
		rd = (RoleDecl) rd.del(del);
		return rd;
	}

	@Override
	public RoleDecl project(Role self)
	{
		Name<RoleKind> role = this.name.toName();
		RoleNode rn = (RoleNode) AstFactoryImpl.FACTORY.SimpleNameNode(this.name.source, RoleKind.KIND, role.toString());
		if (role.equals(self))
		{
			return AstFactoryImpl.FACTORY.SelfRoleDecl(this.name.source, rn);
		}
		return AstFactoryImpl.FACTORY.RoleDecl(this.source, rn);
	}
	
	@Override
	public Role getDeclName()
	{
		return (Role) super.getDeclName();
	}
	
	public boolean isSelfRoleDecl()
	{
		return false;
	}

	@Override
	public String getKeyword()
	{
		return Constants.ROLE_KW;
	}
}
