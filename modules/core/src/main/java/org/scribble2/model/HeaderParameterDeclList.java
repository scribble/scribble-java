package org.scribble2.model;

import java.util.LinkedList;
import java.util.List;

import org.scribble2.model.visit.ModelVisitor;
import org.scribble2.util.ScribbleException;

public abstract class HeaderParameterDeclList<T extends HeaderParameterDecl> extends ModelNodeBase 
{
	public final List<T> decls;
	
	public HeaderParameterDeclList(List<T> decls)
	{
		this.decls = new LinkedList<>(decls);
	}
	
	public int length()
	{
		return this.decls.size();
	}

	public boolean isEmpty()
	{
		return this.decls.isEmpty();
	}
	
	protected abstract HeaderParameterDeclList<T> reconstruct(List<T> decls);
	
	@Override
	public HeaderParameterDeclList<T> visitChildren(ModelVisitor nv) throws ScribbleException
	{
		List<T> nds = visitChildListWithClassCheck(this, this.decls, nv);
		//return new HeaderParameterDeclList<>(this.ct, nds);
		return reconstruct(nds);
	}

	/*@Override 
	public NameDeclList checkWellFormedness(WellFormednessChecker wfc) throws ScribbleException
	{
		Set<Name> ns = new HashSet<>();
		Set<Name> dns = new HashSet<>();
		for (ParameterDecl pd : this.decls)
		{
			Name n = pd.namenode.toName();
			Name dn = pd.getDeclarationName();
			if (ns.contains(n))  // FIXME: should also be distinct from payload type names (for arguments)
			{
				throw new ScribbleException("Duplicate parameter declaration: " + n);
			}
			if (dns.contains(dn))
			{
				throw new ScribbleException("Duplicate parameter delcaration: " + dn);
			}
			ns.add(n);
			dns.add(dn);
		}
		return (NameDeclList) super.checkWellFormedness(wfc);
	}
	
	@Override 
	public NameDeclList leave(EnvVisitor nv) throws ScribbleException
	{
		NameDeclList pdl = (NameDeclList) super.leave(nv);
		Env env = nv.getEnv();
		for (ParameterDecl pd : this.decls)
		{
			env.params.addParameter(pd.getDeclarationName(), pd.kind);
		}
		return pdl;
	}*/

	@Override
	public String toString()
	{
		if (isEmpty())
		{
			return "";
		}
		String s = decls.get(0).toString();
		for (T nd : this.decls.subList(1, this.decls.size()))
		{
			s += ", " + nd;
		}
		return s;
	}
					
	/*public static final Function<List<? extends NameDecl>, List<ParameterDecl>> toParameterDeclList =
			(List<? extends NameDecl> nds)
					-> nds.stream().map(ParameterDeclList.toParameterDecl).collect(Collectors.toList());*/
					
	/*public static final Function<List<? extends NameDecl>, List<RoleDecl>> toRoleDeclList = 
			(List<? extends NameDecl> nds) -> { return Util.listCast(nds, toRoleDecl); };
			
	public static final Function<List<? extends NameDecl>, List<ParameterDecl>> toParameterDeclList = 
			(List<? extends NameDecl> nds) -> { return Util.listCast(nds, toParameterDecl); };*/
}
