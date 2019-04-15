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
package org.scribble.ast;

import org.scribble.core.type.kind.NonRoleArgKind;
import org.scribble.core.type.session.Arg;

// sig or payloadtype kinds that can be used as do arg vals, cf. RoleNode
// "Value nodes" (sigs or names) that can be used as non-role subprotocol arguments (doesn't include role instantation arguments)
// N.B. not the actual argument node itself (that is NonRoleArg, element of NonRoleArgList, which wraps these nodes)
public interface NonRoleArgNode extends DoArgNode
{
	Arg<? extends NonRoleArgKind> toArg();

	// Not kinded: point of this interface is don't know which kind the node is -- so use the "is" methods -- cf. AmbigNameNode inherits both sig and data kind
	// And not all values are names, e.g. message sigs
	default boolean isSigLitNode()
	{
		return false;
	}

	default boolean isSigNameNode()
	{
		return false;
	}

	default boolean isDataNameNode()
	{
		return false;
	}

	default boolean isSigParamNode()
	{
		return false;
	}

	default boolean isDataParamNode()
	{
		return false;
	}
}
