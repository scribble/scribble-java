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

import org.antlr.runtime.Token;
import org.scribble.core.type.kind.ProtoKind;

// Redundant?  Is always a CompoundInteraction -- cf. SimpleSessionNode
public abstract class CompoundSessionNode<K extends ProtoKind>
		extends ScribNodeBase implements SessionNode<K>
{
	// ScribTreeAdaptor#create constructor
	public CompoundSessionNode(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	protected CompoundSessionNode(CompoundSessionNode<K> node)
	{
		super(node);
	}
}
