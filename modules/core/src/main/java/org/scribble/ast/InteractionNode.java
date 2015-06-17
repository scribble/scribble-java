package org.scribble.ast;

import org.scribble.sesstype.kind.ProtocolKind;


// Make a compound interaction subclass for choice/parallel etc?
public interface InteractionNode<K extends ProtocolKind> extends ScribNode//, ContextNode, EnvNode
{
	//InteractionNodeContext getContext();  // Combine with ContextNode?
}
