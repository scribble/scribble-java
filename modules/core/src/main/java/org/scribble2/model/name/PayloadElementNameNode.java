package org.scribble2.model.name;

import org.scribble2.sesstype.kind.Kind;
import org.scribble2.sesstype.name.PayloadType;



// A type kind node: PayloadTypeNode or ParameterNode -- not necessarily simple nor qualified
// (type counterpart to MessageNode)
public interface PayloadElementNameNode //extends ArgumentNode
{
	PayloadType<? extends Kind> toPayloadType();
}
