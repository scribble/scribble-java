package org.scribble2.model;

import org.scribble2.model.name.NameNode;
import org.scribble2.sesstype.name.Name;

public abstract class NameDeclNode<T1 extends NameNode<T2>, T2 extends Name> extends ModelNodeBase //implements Named<Name> -- no: NameDeclNode is not a Named node itself
//public abstract class NameDeclNode<T1 extends NameNode<? extends Name>> extends ModelNodeBase
{ 
	// Can't seem to bind the Name without introducing that second type param
	abstract T2 toName();
	//abstract Name toName();
}
