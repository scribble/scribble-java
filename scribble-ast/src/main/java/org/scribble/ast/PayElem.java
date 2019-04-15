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

import org.scribble.core.type.kind.PayElemKind;
import org.scribble.core.type.name.PayElemType;

// Not in grammar file -- but cf. DoArg (and PayloadElemList cf. DoArgList) -- i.e. need a wrapper for mixed and initially ambiguous name kinds
public interface PayElem<K extends PayElemKind> extends ScribNode
{
	PayElemType<? extends PayElemKind> toPayloadType();  // Mainly a wrapper method for the wrapped NameNode

	default boolean isGlobalDelegationElem()
	{
		return false;
	}

	default boolean isLocalDelegationElem()
	{
		return false;
	}
}
