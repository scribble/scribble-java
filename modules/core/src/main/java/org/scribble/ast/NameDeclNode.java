package org.scribble.ast;

import org.scribble.ast.name.NameNode;
import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.name.Name;

public abstract class NameDeclNode<K extends Kind> extends ScribNodeBase
{ 
	public final NameNode<K> name;
	
	protected NameDeclNode(NameNode<K> name)
	{
		this.name = name;
	}

	public Name<K> getDeclName()
	{
		return this.name.toName();
	}
}
