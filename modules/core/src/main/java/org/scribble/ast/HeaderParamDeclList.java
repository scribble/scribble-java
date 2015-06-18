package org.scribble.ast;

import java.util.LinkedList;
import java.util.List;

import org.scribble.ast.visit.AstVisitor;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.name.Role;

public abstract class HeaderParamDeclList<K extends Kind> extends ScribNodeBase 
{
	public final List<HeaderParamDecl<K>> decls;  // Not List<? extends HeaderParamDecl<T, K>> because ParamDeclList contains mixed kinds
	
	protected HeaderParamDeclList(List<? extends HeaderParamDecl<K>> decls)
	{
		this.decls = new LinkedList<>(decls);
	}
	
	protected abstract HeaderParamDeclList<K> reconstruct(List<? extends HeaderParamDecl<K>> decls);
	
	@Override
	public HeaderParamDeclList<K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		List<HeaderParamDecl<K>> nds = visitChildListWithClassCheck(this, this.decls, nv);
		return reconstruct(nds);
	}
	
	public abstract HeaderParamDeclList<K> project(Role self);  // FIXME: move to delegate
	
	public int length()
	{
		return this.decls.size();
	}

	public boolean isEmpty()
	{
		return this.decls.isEmpty();
	}

	@Override
	public String toString()
	{
		if (isEmpty())
		{
			return "";
		}
		String s = decls.get(0).toString();
		for (HeaderParamDecl<K> nd : this.decls.subList(1, this.decls.size()))
		{
			s += ", " + nd;
		}
		return s;
	}
}
