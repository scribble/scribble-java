package org.scribble2.model.local;

import org.scribble2.model.InteractionNode;
import org.scribble2.sesstype.kind.Local;


// Alternatively to interface, use GlobalNode subclass with delegation to "super" base (e.g. Choice) classes
public interface LInteractionNode extends InteractionNode<Local>, LocalNode
{
	//Role getSelfRole();
}