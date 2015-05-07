package org.scribble2.model.global;

import org.scribble2.model.InteractionNode;
import org.scribble2.sesstype.kind.GlobalKind;


// Alternatively to interface, use GlobalNode subclass with delegation to "super" base (e.g. Choice) classes
public interface GlobalInteractionNode extends InteractionNode<GlobalKind>, GlobalNode
{
	
}
