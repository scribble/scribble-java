package org.scribble.ast;

import org.scribble.ast.visit.AstVisitor;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.name.Role;


// Cf. NameDeclNode/HeaderParameterDecl, i.e. wrappers for param names/arg values
// Simpler than NameDeclNode, doesn't constrain node-type correspondence for names
public abstract class DoArg<T extends DoArgNode> extends ScribNodeBase
{
	public final T val;

	protected DoArg(T arg)
	{
		this.val = arg;
	}

	protected abstract DoArg<T> reconstruct(T arg);
	
	@Override
	public DoArg<T> visitChildren(AstVisitor nv) throws ScribbleException
	{
		@SuppressWarnings("unchecked")
		T arg = (T) visitChild(this.val, nv);  // Disambiguation will replace AmbiguousNameNodes
		return reconstruct(arg);
	}

	public abstract DoArg<T> project(Role self);
	
	@Override
	public String toString()
	{
		return this.val.toString();
	}
}
