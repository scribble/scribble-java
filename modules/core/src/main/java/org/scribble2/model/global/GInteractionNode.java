package org.scribble2.model.global;

import org.scribble2.model.InteractionNode;
import org.scribble2.sesstype.kind.Global;


// Alternatively to interface, use GlobalNode subclass with delegation to "super" base (e.g. Choice) classes
public interface GInteractionNode extends InteractionNode<Global>, GlobalNode
{
	
}
