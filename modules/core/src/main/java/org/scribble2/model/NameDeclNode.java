package org.scribble2.model;

import org.scribble2.sesstype.kind.Kind;
import org.scribble2.sesstype.name.Name;

// Should also include type decls?
//public abstract class NameDeclNode<T1 extends NameNode<T2>, T2 extends IName> extends ModelNodeBase //implements Named<Name> -- no: NameDeclNode is not a Named node itself
public abstract class NameDeclNode<T2 extends Name<K>, K extends Kind> extends ModelNodeBase //implements Named<Name> -- no: NameDeclNode is not a Named node itself
//public abstract class NameDeclNode<T1 extends NameNode<? extends Name>> extends ModelNodeBase
{ 
	// Can't seem to bind the Name without introducing that second type param
	abstract T2 toName();
	//abstract Name toName();
}
