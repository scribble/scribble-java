package org.scribble.ast;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.name.Name;
import org.scribble.sesstype.name.Role;

public class NonRoleParamDeclList extends HeaderParamDeclList<Kind>
{
	public NonRoleParamDeclList(List<NonRoleParamDecl<Kind>> decls)
	{
		super(decls);
	}

	@Override
	protected NonRoleParamDeclList copy()
	{
		return new NonRoleParamDeclList(getParamDecls());
	}

	@Override
	protected NonRoleParamDeclList reconstruct(List<? extends HeaderParamDecl<Kind>> decls)
	{
		ScribDel del = del();
		NonRoleParamDeclList rdl = new NonRoleParamDeclList(getParamDecls());
		rdl = (NonRoleParamDeclList) rdl.del(del);
		return rdl;
	}

	public List<NonRoleParamDecl<Kind>> getParamDecls()
	{
		return this.decls.stream().map((d) -> (NonRoleParamDecl<Kind>) d).collect(Collectors.toList());
	}
		
	// FIXME: move to delegate?
	@Override
	public NonRoleParamDeclList project(Role self)
	{
		return AstFactoryImpl.FACTORY.NonRoleParamDeclList(getParamDecls());
	}

	public List<Name<Kind>> getParameters()
	{
		return this.decls.stream().map((decl) -> decl.getDeclName()).collect(Collectors.toList());
	}

	@Override
	public String toString()
	{
		return (isEmpty())
				? ""
				: "<" + super.toString() + ">";
	}
}
