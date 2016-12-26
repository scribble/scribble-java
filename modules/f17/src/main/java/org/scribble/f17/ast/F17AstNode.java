package org.scribble.f17.ast;

@Deprecated
public abstract class F17AstNode
{
	/*public Set<RecVar> freeVariables();
	public Set<Role> roles();*/
	
	protected abstract boolean canEquals(Object o);
}
