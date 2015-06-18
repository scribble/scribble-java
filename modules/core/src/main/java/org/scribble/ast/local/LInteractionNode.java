package org.scribble.ast.local;

import org.scribble.ast.InteractionNode;
import org.scribble.sesstype.kind.Local;


// Alternatively to interface, use GlobalNode subclass with delegation to "super" base (e.g. Choice) classes
public interface LInteractionNode extends InteractionNode<Local>, LNode
{
	//Role getSelfRole();
}
