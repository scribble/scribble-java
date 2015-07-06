package org.scribble.ast;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.RoleKind;
import org.scribble.sesstype.name.Role;
import org.scribble.util.ScribUtil;

public class RoleDeclList extends HeaderParamDeclList<RoleKind>
{
	public RoleDeclList(List<RoleDecl> decls)
	{
		super(decls);
	}

	@Override
	protected RoleDeclList copy()
	{
		return new RoleDeclList(getRoleDecls());
	}
	
	@Override
	public RoleDeclList clone()
	{
		List<RoleDecl> decls = ScribUtil.cloneList(getRoleDecls());
		return AstFactoryImpl.FACTORY.RoleDeclList(decls);
	}

	@Override
	public HeaderParamDeclList<RoleKind> reconstruct(List<? extends HeaderParamDecl<RoleKind>> decls)
	{
		ScribDel del = del();
		RoleDeclList rdl = new RoleDeclList(getRoleDecls());
		rdl = (RoleDeclList) rdl.del(del);
		return rdl;
	}

	// FIXME: move to delegate?
	@Override
	public RoleDeclList project(Role self)
	{
		return AstFactoryImpl.FACTORY.RoleDeclList(getRoleDecls());
	}
	
	public List<RoleDecl> getRoleDecls()
	{
		return this.decls.stream().map((rd) -> (RoleDecl) rd).collect(Collectors.toList());
	}

	public List<Role> getRoles()
	{
		return this.decls.stream().map((rd) -> ((RoleDecl) rd).getDeclName()).collect(Collectors.toList());
	}

	@Override
	public String toString()
	{
		return "(" + super.toString() + ")";
	}
}
