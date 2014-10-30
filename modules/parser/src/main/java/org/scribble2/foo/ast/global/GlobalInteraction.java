package org.scribble2.foo.ast.global;

import org.scribble2.foo.ast.InteractionNode;


// Alternatively to interface, use GlobalNode subclass with delegation to "super" base (e.g. Choice) classes
public interface GlobalInteraction extends InteractionNode, GlobalNode
{
	
}
