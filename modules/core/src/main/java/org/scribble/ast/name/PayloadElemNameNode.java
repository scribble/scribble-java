package org.scribble.ast.name;

import org.scribble.ast.ArgNode;
import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.name.PayloadType;



// A type kind node: PayloadTypeNode or ParameterNode -- not necessarily simple nor qualified
// (type counterpart to MessageNode)
public interface PayloadElemNameNode extends ArgNode
{
	PayloadType<? extends Kind> toPayloadType();
	//PayloadType<K> toPayloadType();
}
