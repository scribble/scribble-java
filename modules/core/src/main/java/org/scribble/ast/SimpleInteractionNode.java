package org.scribble.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.sesstype.kind.ProtocolKind;

// Name should be read (Simple ( InteractionNode ))
public abstract class SimpleInteractionNode<K extends ProtocolKind>
		extends ScribNodeBase implements InteractionNode<K>
{
	protected SimpleInteractionNode(CommonTree source)
	{
		super(source);
	}
}
