package org.scribble.ast;

import org.scribble.sesstype.kind.ProtocolKind;

// Make a compound interaction subclass for choice/parallel etc?
public interface InteractionNode<K extends ProtocolKind> extends ProtocolKindNode<K>
{
	//Set<MessageId<?>> collectMessageIds();  // Not worth implementing this homomorphically for every case except MessageTransfer, better to use visitChildren pattern
	//Map<Role, MessageId<?>> getEnablingMessages();
}
