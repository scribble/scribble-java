package org.scribble2.model;

import org.scribble2.sesstype.kind.ProtocolKind;


// Make a compound interaction subclass for choice/parallel etc?
public interface InteractionNode<K extends ProtocolKind> extends ModelNode//, ContextNode, EnvNode
{
	//InteractionNodeContext getContext();  // Combine with ContextNode?
}
