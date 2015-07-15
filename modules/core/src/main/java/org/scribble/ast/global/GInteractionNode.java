package org.scribble.ast.global;

import org.scribble.ast.InteractionNode;
import org.scribble.sesstype.kind.Global;

// Alternatively to interface, use GlobalNode subclass with delegation to "super" base (e.g. Choice) classes
public interface GInteractionNode extends InteractionNode<Global>, GNode
{

}
