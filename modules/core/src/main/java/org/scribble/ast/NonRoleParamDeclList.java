package org.scribble.ast;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.NonRoleParamKind;
import org.scribble.sesstype.name.Name;
import org.scribble.sesstype.name.Role;
import org.scribble.util.ScribUtil;

// Typing a bit awkward that this list has to use NonRoleParamKind as the "concrete" kind, while the NonRoleParamDecl elements use the actual concrete kind
// But OK because the NonRoleParamDecl nodes are immutable (the generic kind value is never rewritten after instantiation, only read)
public class NonRoleParamDeclList extends HeaderParamDeclList<NonRoleParamKind>
{
	public NonRoleParamDeclList(List<NonRoleParamDecl<NonRoleParamKind>> decls)
	{
		super(decls);
	}

	@Override
	protected NonRoleParamDeclList copy()
	{
		return new NonRoleParamDeclList(getDecls());
	}
	
	@Override
	public NonRoleParamDeclList clone()
	{
		List<NonRoleParamDecl<NonRoleParamKind>> decls = ScribUtil.cloneList(getDecls());
		return AstFactoryImpl.FACTORY.NonRoleParamDeclList(decls);
	}

	@Override
	public NonRoleParamDeclList reconstruct(List<? extends HeaderParamDecl<NonRoleParamKind>> decls)
	{
		ScribDel del = del();
		NonRoleParamDeclList rdl = AstFactoryImpl.FACTORY.NonRoleParamDeclList(castParamDecls(decls));
		rdl = (NonRoleParamDeclList) rdl.del(del);
		return rdl;
	}

	@Override
	public List<NonRoleParamDecl<NonRoleParamKind>> getDecls()
	{
		return castParamDecls(super.getDecls());
	}

	public List<Name<NonRoleParamKind>> getParameters()
	{
		return getDecls().stream().map((decl) -> decl.getDeclName()).collect(Collectors.toList());
	}
		
	// FIXME: move to delegate?
	@Override
	public NonRoleParamDeclList project(Role self)
	{
		return AstFactoryImpl.FACTORY.NonRoleParamDeclList(getDecls());
	}

	@Override
	public String toString()
	{
		return (isEmpty())
				? ""
				: "<" + super.toString() + ">";
	}
	
	private static List<NonRoleParamDecl<NonRoleParamKind>>
			castParamDecls(List<? extends HeaderParamDecl<NonRoleParamKind>> decls)
	{
		return decls.stream().map((d) -> (NonRoleParamDecl<NonRoleParamKind>) d).collect(Collectors.toList());
	}
}
