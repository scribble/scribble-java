package org.scribble2.model.local;

import org.scribble2.model.InteractionNode;
import org.scribble2.sesstype.kind.LocalKind;


// Alternatively to interface, use GlobalNode subclass with delegation to "super" base (e.g. Choice) classes
public interface LocalInteractionNode extends InteractionNode<LocalKind>, LocalNode
{
	//Role getSelfRole();
}
