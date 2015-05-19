package org.scribble2.model;

import org.scribble2.model.visit.ModelVisitor;
import org.scribble2.sesstype.name.Role;
import org.scribble2.util.ScribbleException;


// Cf. NameDeclNode/HeaderParameterDecl
// Simpler than NameDeclNode, doesn't constrain node-type correspondence for names
public abstract class DoArgument<T extends DoArgumentNode> extends ModelNodeBase
{
	public final T arg;

	protected DoArgument(T arg)
	{
		this.arg = arg;
	}

	protected abstract DoArgument<T> reconstruct(T arg);
	
	@Override
	public DoArgument<T> visitChildren(ModelVisitor nv) throws ScribbleException
	{
		//T arg = visitChildWithClassCheck(this, this.arg, nv);
		@SuppressWarnings("unchecked")
		T arg = (T) this.visitChild(this.arg, nv);  // Disambiguation will change AmbiguousNameNodes to 
		return reconstruct(arg);
	}

	public abstract DoArgument<T> project(Role self);
	
	@Override
	public String toString()
	{
		return this.arg.toString();
	}
}
