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
package org.scribble.core.type.session;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.core.type.kind.ProtoKind;

// SessTypeBase is to SessType as ScribNodeBase is to ScribNode
public abstract class STypeBase<K extends ProtoKind, B extends Seq<K, B>>
		implements SType<K, B>
{
	private final CommonTree source;  // Currently null for "generated" terms (cf. hasSource)

	public STypeBase(CommonTree source)
	{
		/*CommonTree clone = (source == null)
				? null
				: CommonTree.clone();  // clone not visibile*/
				// CHECKME: ScribNodes are technically mutable -- though should be treated immutable (defensive copies) post disamb, i.e., in core passes
		this.source = source;
	}

	@Override
	public boolean hasSource()
	{
		return this.source != null;
	}
	
	// Pre: hasSource
	@Override
	public CommonTree getSource()
	{
		return this.source;
	}

	// Does *not* include this.source -- equals/hashCode is for "surface-level" syntactic equality of SessType only
	@Override
	public int hashCode()
	{
		int hash = 1871;
		hash = 31 * hash + super.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof STypeBase))
		{
			return false;
		}
		STypeBase<?, ?> them = (STypeBase<?, ?>) o;
		return them.canEquals(this);
	}
}
