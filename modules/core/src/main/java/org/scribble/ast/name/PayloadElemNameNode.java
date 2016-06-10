package org.scribble.ast.name;

import org.scribble.ast.NonRoleArgNode;
import org.scribble.sesstype.kind.DataTypeKind;
import org.scribble.sesstype.name.PayloadType;


// A datatype kind node: DataTypeNode or NonRoleParameterNode -- not necessarily simple nor qualified
// Actually, datatype or global protocol kind, if delegation supported -- for "structural delegation" this would not directly be a name node any more (like MessageNode)
//public interface PayloadElemNameNode<K extends PayloadTypeKind> extends NonRoleArgNode
public interface PayloadElemNameNode extends NonRoleArgNode
{
	//PayloadType<? extends PayloadTypeKind> toPayloadType();
	PayloadType<DataTypeKind> toPayloadType();  // Currently can assume the only possible kind is DataTypeKind (delegation is by (non-ambig) delegationelem)
	//PayloadType<K> toPayloadType();
}
