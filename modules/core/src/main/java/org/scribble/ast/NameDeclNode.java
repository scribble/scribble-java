package org.scribble.ast;

import org.scribble.ast.name.NameNode;
import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.name.Name;

// Should also include type decls?
//public abstract class NameDeclNode<T1 extends NameNode<T2>, T2 extends IName> extends ModelNodeBase //implements Named<Name> -- no: NameDeclNode is not a Named node itself
//public abstract class NameDeclNode<T2 extends Name<K>, K extends Kind> extends ModelNodeBase //implements Named<Name> -- no: NameDeclNode is not a Named node itself
//public abstract class NameDeclNode<T1 extends NameNode<? extends Name>> extends ModelNodeBase
public abstract class NameDeclNode<K extends Kind> extends ScribNodeBase //implements Named<Name> -- no: NameDeclNode is not a Named node itself
{ 
	public final NameNode<K> name;
	
	protected NameDeclNode(NameNode<K> name)
	{
		this.name = name;
	}

	// Can't seem to bind the Name without introducing that second type param
	//public abstract T2 toName();
	//abstract Name toName();
	public Name<K> getDeclName()
	{
		return this.name.toName();
	}
}
