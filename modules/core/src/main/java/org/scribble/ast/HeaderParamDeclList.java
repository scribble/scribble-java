package org.scribble.ast;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ParamKind;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.AstVisitor;


// RoleKind or (NonRole)ParamKind
public abstract class HeaderParamDeclList<K extends ParamKind> extends ScribNodeBase 
{
	private final List<? extends HeaderParamDecl<K>> decls;
	
	protected HeaderParamDeclList(List<? extends HeaderParamDecl<K>> decls)
	{
		this.decls = new LinkedList<>(decls);
	}
	
	public abstract HeaderParamDeclList<K> reconstruct(List<? extends HeaderParamDecl<K>> decls);
	
	@Override
	public HeaderParamDeclList<? extends K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		List<? extends HeaderParamDecl<K>> nds = visitChildListWithClassEqualityCheck(this, this.decls, nv);
		return reconstruct(nds);
	}
	
	public List<? extends HeaderParamDecl<K>> getDecls()
	{
		return Collections.unmodifiableList(this.decls);
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
		StringBuilder sb = new StringBuilder(this.decls.get(0).toString());
		this.decls.subList(1, this.decls.size()).stream().forEach((nd) ->
				{
					sb.append(", " + nd);
				});
		return sb.toString();
	}
}
