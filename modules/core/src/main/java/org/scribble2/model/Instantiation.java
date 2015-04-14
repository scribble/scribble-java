package org.scribble2.model;

import org.scribble2.model.visit.ModelVisitor;
import org.scribble2.sesstype.name.Role;
import org.scribble2.util.ScribbleException;


// Cf. NameDeclNode/HeaderParameterDecl
// Simpler than NameDeclNode, doesn't constrain node-type correspondence for names
public abstract class Instantiation<T extends InstantiationNode> extends ModelNodeBase
{
	public final T arg;

	protected Instantiation(T arg)
	{
		this.arg = arg;
	}

	protected abstract Instantiation<T> reconstruct(T arg);
	
	@Override
	public Instantiation<T> visitChildren(ModelVisitor nv) throws ScribbleException
	{
		T arg = visitChildWithClassCheck(this, this.arg, nv);
		return reconstruct(arg);
	}

	public abstract Instantiation<T> project(Role self);
}
