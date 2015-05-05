package org.scribble2.model;

import java.util.LinkedList;
import java.util.List;

import org.scribble2.model.visit.ModelVisitor;
import org.scribble2.sesstype.kind.Kind;
import org.scribble2.sesstype.name.Role;
import org.scribble2.util.ScribbleException;

/*public abstract class HeaderParameterDeclList<
		T1 extends HeaderParameterDecl<? extends SimpleNameNode<T2>,	T2>,
		T2 extends SimpleName  // FIXME: WRONG parameters decls can be of mixed kinds
> extends ModelNodeBase*/
public abstract class HeaderParameterDeclList<K extends Kind> extends ModelNodeBase
{
	//public final List<T1> decls;
	protected final List<? extends HeaderParameterDecl<K>> decls;
	
	//protected HeaderParameterDeclList(List<T1> decls)
	protected HeaderParameterDeclList(List<? extends HeaderParameterDecl<K>> decls)
	{
		this.decls = new LinkedList<>(decls);
	}
	
	public abstract List<? extends HeaderParameterDecl<K>> getDecls();
	
	public int length()
	{
		return this.decls.size();
	}

	public boolean isEmpty()
	{
		return this.decls.isEmpty();
	}
	
	//public abstract HeaderParameterDeclList<T1, T2> project(Role self);
	public abstract HeaderParameterDeclList<K> project(Role self);
	
	//protected abstract HeaderParameterDeclList<T1, T2> reconstruct(List<T1> decls);
	protected abstract HeaderParameterDeclList<K> reconstruct(List<? extends HeaderParameterDecl<K>> decls);
	
	@Override
	//public HeaderParameterDeclList<T1, T2> visitChildren(ModelVisitor nv) throws ScribbleException
	public HeaderParameterDeclList<K> visitChildren(ModelVisitor nv) throws ScribbleException
	{
		//List<T1> nds = visitChildListWithClassCheck(this, this.decls, nv);
		List<? extends HeaderParameterDecl<K>> nds = visitChildListWithClassCheck(this, this.decls, nv);
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
		//for (T1 nd : this.decls.subList(1, this.decls.size()))
		for (HeaderParameterDecl<K> nd : this.decls.subList(1, this.decls.size()))
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
