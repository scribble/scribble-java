package org.scribble.ast;

import java.util.LinkedList;
import java.util.List;

import org.scribble.ast.visit.ModelVisitor;
import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.name.Role;
import org.scribble.util.ScribbleException;

public abstract class HeaderParamDeclList<
		/*T1 extends HeaderParamDecl<? extends SimpleNameNode<T2>,	T2>,
		T2 extends SimpleName*/
		//T extends Name<K>,
		K extends Kind
> extends ScribNodeBase 
{
	//public final List<T1> decls;
	//public final List<HeaderParamDecl<T, K>> decls;  // Not List<? extends HeaderParamDecl<T, K>> because ParamDeclList contains mixed kinds
	public final List<HeaderParamDecl<K>> decls;  // Not List<? extends HeaderParamDecl<T, K>> because ParamDeclList contains mixed kinds
	
	//protected HeaderParamDeclList(List<T1> decls)
	protected HeaderParamDeclList(List<? extends HeaderParamDecl<K>> decls)
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
	
	//public abstract HeaderParamDeclList<T1, T2> project(Role self);
	//public abstract HeaderParamDeclList<T, K> project(Role self);
	public abstract HeaderParamDeclList<K> project(Role self);
	
	//protected abstract HeaderParamDeclList<T1, T2> reconstruct(List<T1> decls);
	//protected abstract HeaderParamDeclList<T, K> reconstruct(List<HeaderParamDecl<T, K>> decls);
	//protected abstract HeaderParamDeclList<T, K> reconstruct(List<HeaderParamDecl<K>> decls);
	protected abstract HeaderParamDeclList<K> reconstruct(List<? extends HeaderParamDecl<K>> decls);
	
	@Override
	//public HeaderParamDeclList<T1, T2> visitChildren(ModelVisitor nv) throws ScribbleException
	//public HeaderParamDeclList<T, K> visitChildren(ModelVisitor nv) throws ScribbleException
	public HeaderParamDeclList<K> visitChildren(ModelVisitor nv) throws ScribbleException
	{
		//List<T1> nds = visitChildListWithClassCheck(this, this.decls, nv);
		//List<HeaderParamDecl<T, K>> nds = visitChildListWithClassCheck(this, this.decls, nv);
		List<HeaderParamDecl<K>> nds = visitChildListWithClassCheck(this, this.decls, nv);
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
		//for (HeaderParamDecl<T, K> nd : this.decls.subList(1, this.decls.size()))
		for (HeaderParamDecl<K> nd : this.decls.subList(1, this.decls.size()))
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
