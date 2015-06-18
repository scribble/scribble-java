package org.scribble.ast.name;

import org.scribble.ast.NonRoleArgNode;
import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.name.PayloadType;



// A type kind node: PayloadTypeNode or ParameterNode -- not necessarily simple nor qualified
// (type counterpart to MessageNode)
public interface PayloadElemNameNode extends NonRoleArgNode
{
	PayloadType<? extends Kind> toPayloadType();
	//PayloadType<K> toPayloadType();
}
