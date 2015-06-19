package org.scribble.ast.name;

import org.scribble.ast.NonRoleArgNode;
import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.name.PayloadType;


// A datatype kind node: DataTypeNode or NonRoleParameterNode -- not necessarily simple nor qualified
// Actually, datatype or protocol kind, if delegation supported -- for delegation this would not directly be a name node any more (like MessageNode)
public interface PayloadElemNameNode extends NonRoleArgNode
{
	PayloadType<? extends Kind> toPayloadType();
}
