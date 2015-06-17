package org.scribble.ast;

import org.scribble.ast.visit.ModelVisitor;
import org.scribble.sesstype.name.Role;
import org.scribble.util.ScribbleException;


// Cf. NameDeclNode/HeaderParameterDecl
// Simpler than NameDeclNode, doesn't constrain node-type correspondence for names
public abstract class DoArg<T extends DoArgNode> extends ModelNodeBase
//public abstract class DoArgument<K extends Kind> extends ModelNodeBase
{
	public final T val;
	//public final DoArgumentNode<K> node;

	protected DoArg(T arg)
	{
		this.val = arg;
	}

	protected abstract DoArg<T> reconstruct(T arg);
	
	@Override
	public DoArg<T> visitChildren(ModelVisitor nv) throws ScribbleException
	{
		//T arg = visitChildWithClassCheck(this, this.arg, nv);
		@SuppressWarnings("unchecked")
		T arg = (T) this.visitChild(this.val, nv);  // Disambiguation will change AmbiguousNameNodes to 
		return reconstruct(arg);
	}

	public abstract DoArg<T> project(Role self);
	
	@Override
	public String toString()
	{
		return this.val.toString();
	}
}
