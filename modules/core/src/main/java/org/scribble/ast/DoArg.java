package org.scribble.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.AstVisitor;

// Cf. NameDeclNode/HeaderParameterDecl, i.e. wrappers for param names/arg values
// Simpler than NameDeclNode, doesn't constrain node-type correspondence for names
public abstract class DoArg<T extends DoArgNode> extends ScribNodeBase
{
	public final T val;

	protected DoArg(CommonTree source, T arg)
	{
		super(source);
		this.val = arg;
	}

	public abstract DoArg<T> reconstruct(T arg);
	
	@Override
	public DoArg<T> visitChildren(AstVisitor nv) throws ScribbleException
	{
		ScribNode visited = visitChild(this.val, nv);  // Disambiguation will replace AmbiguousNameNodes*/
		if (!(visited instanceof DoArgNode))
		{
			throw new RuntimeException("Shouldn't get in here: " + visited);
		}
		@SuppressWarnings("unchecked")
		T arg = (T) visited;
		return reconstruct(arg);
	}
	
	public T getVal()
	{
		return this.val;
	}

	public abstract DoArg<T> project(Role self);
	
	@Override
	public String toString()
	{
		return this.val.toString();
	}
}
