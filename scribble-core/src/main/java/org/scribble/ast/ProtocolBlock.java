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

import org.antlr.runtime.tree.CommonTree;
import org.scribble.main.ScribbleException;
import org.scribble.type.kind.ProtocolKind;
import org.scribble.visit.AstVisitor;

public abstract class ProtocolBlock<K extends ProtocolKind> extends CompoundInteraction implements ProtocolKindNode<K>
{
	public final InteractionSeq<K> seq;

	public ProtocolBlock(CommonTree source, InteractionSeq<K> seq)
	{
		super(source);
		this.seq = seq;
	}
	
	@Override
	public abstract ProtocolBlock<K> clone(AstFactory af);

	public abstract ProtocolBlock<K> reconstruct(InteractionSeq<K> seq);

	public abstract InteractionSeq<K> getInteractionSeq();

	@Override
	public ProtocolBlock<K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		InteractionSeq<K> seq = visitChildWithClassEqualityCheck(this, this.seq, nv);
		return reconstruct(seq);
	}
	
	public boolean isEmpty()
	{
		return this.seq.isEmpty();
	}

	@Override
	public String toString()
	{
		return "{\n" + this.seq + "\n}";  // Empty block will contain an blank line
	}
}
