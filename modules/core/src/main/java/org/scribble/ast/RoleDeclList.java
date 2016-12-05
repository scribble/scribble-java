package org.scribble.ast;

import java.util.List;
import java.util.stream.Collectors;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.RoleKind;
import org.scribble.sesstype.name.Role;
import org.scribble.util.ScribUtil;

public class RoleDeclList extends HeaderParamDeclList<RoleKind>
{
	public RoleDeclList(CommonTree source, List<RoleDecl> decls)
	{
		super(source, decls);
	}

	@Override
	protected RoleDeclList copy()
	{
		return new RoleDeclList(this.source, getDecls());
	}
	
	@Override
	public RoleDeclList clone()
	{
		List<RoleDecl> decls = ScribUtil.cloneList(getDecls());
		return AstFactoryImpl.FACTORY.RoleDeclList(this.source, decls);
	}

	@Override
	public HeaderParamDeclList<RoleKind> reconstruct(List<? extends HeaderParamDecl<RoleKind>> decls)
	{
		ScribDel del = del();
		RoleDeclList rdl = AstFactoryImpl.FACTORY.RoleDeclList(this.source, castRoleDecls(decls));
		rdl = (RoleDeclList) rdl.del(del);
		return rdl;
	}
	
	@Override
	public List<RoleDecl> getDecls()
	{
		return castRoleDecls(super.getDecls());
	}

	public List<Role> getRoles()
	{
		return getDecls().stream().map((decl) -> decl.getDeclName()).collect(Collectors.toList());
	}

	// Move to del?
	@Override
	public RoleDeclList project(Role self)
	{
		return AstFactoryImpl.FACTORY.RoleDeclList(this.source, getDecls());
	}

	@Override
	public String toString()
	{
		return "(" + super.toString() + ")";
	}
	
	private static List<RoleDecl> castRoleDecls(List<? extends HeaderParamDecl<RoleKind>> decls)
	{
		return decls.stream().map((d) -> (RoleDecl) d).collect(Collectors.toList());
	}
}
