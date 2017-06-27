/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.scribble.ast.name;

import org.scribble.ast.NonRoleArgNode;
import org.scribble.sesstype.kind.PayloadTypeKind;
import org.scribble.sesstype.name.PayloadType;


// A datatype kind node: DataTypeNode or NonRoleParameterNode -- not necessarily simple nor qualified
// Actually, datatype or global protocol kind, if delegation supported -- for "structural delegation" this would not directly be a name node any more (like MessageNode)
//public interface PayloadElemNameNode extends NonRoleArgNode
public interface PayloadElemNameNode<K extends PayloadTypeKind> extends NonRoleArgNode
{
	//PayloadType<? extends PayloadTypeKind> toPayloadType();
	//PayloadType<DataTypeKind> toPayloadType();  // Currently can assume the only possible kind is DataTypeKind (delegation is by (non-ambig) delegationelem)
	//PayloadType<? extends PayloadTypeKind> toPayloadType();  // FIXME: generic parameter for kind (data/local)
	PayloadType<K> toPayloadType();
}
