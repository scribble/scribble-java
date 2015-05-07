package org.scribble2.model;

import org.scribble2.sesstype.kind.Kind;


// Make a compound interaction subclass for choice/parallel etc?
public interface InteractionNode<K extends Kind> extends ModelNode//, ContextNode, EnvNode
{
	//InteractionNodeContext getContext();  // Combine with ContextNode?
}
