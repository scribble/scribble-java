package org.scribble.ext.f17.ast;


//public abstract class F17Type extends F17AstNode
public interface F17Type
{
	/*public Set<RecVar> freeVariables();
	public Set<Role> roles();*/
	
	boolean canEquals(Object o);
}
