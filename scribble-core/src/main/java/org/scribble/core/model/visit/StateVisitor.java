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
package org.scribble.core.model.visit;

import java.util.HashSet;
import java.util.Set;

import org.scribble.core.model.MAction;
import org.scribble.core.model.MState;
import org.scribble.core.type.kind.ProtoKind;

public abstract class StateVisitor
<
		L,
		A extends MAction<K>,
		S extends MState<L, A, S, K>,
		K extends ProtoKind
>
{
	// "One-time" traveral (visitor no for reuse)
	private final Set<S> seen = new HashSet<>();
	
	public boolean hasSeen(S s)
	{
		return this.seen.contains(s);
	}
	
	protected void setSeen(S s)
	{
		//if (!this.seen.contains(s))  // Worth?  Or just do add
		{
			this.seen.add(s);
		}
	}
}
	