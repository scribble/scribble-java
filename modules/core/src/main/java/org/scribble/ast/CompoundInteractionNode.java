package org.scribble.ast;

import org.scribble.sesstype.kind.ProtocolKind;

// Name should be read (Compound ( InteractionNode ))
public abstract class CompoundInteractionNode<K extends ProtocolKind>
		extends CompoundInteraction implements InteractionNode<K>
{

}
