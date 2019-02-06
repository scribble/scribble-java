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
import org.antlr.runtime.tree.CommonTree;
import org.scribble.del.ScribDel;
import org.scribble.job.ScribbleException;
import org.scribble.type.kind.ProtocolKind;
import org.scribble.visit.AstVisitor;

public abstract class ProtocolBlock<K extends ProtocolKind>
		extends CompoundInteraction<K> implements ProtocolKindNode<K>
{
	// ScribTreeAdaptor#create constructor
	public ProtocolBlock(Token t)
	{
		super(t);
		this.seq = null;
	}

	// Tree#dupNode constructor
	protected ProtocolBlock(ProtocolBlock<K> node)
	{
		super(node);
		this.seq = null;
	}
	
	@Override
	public abstract ProtocolBlock<K> dupNode();

	public abstract InteractionSeq<K> getInteractSeqChild();
	
	public boolean isEmpty()
	{
		return getInteractSeqChild().isEmpty();
	}

	public ProtocolBlock<K> reconstruct(InteractionSeq<K> seq)
	{
		ProtocolBlock<K> pd = dupNode();
		ScribDel del = del();
		pd.addChild(seq);
		pd.setDel(del);  // No copy
		return pd;
	}

	@Override
	public ProtocolBlock<K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		InteractionSeq<K> seq = 
				visitChildWithClassEqualityCheck(this, getInteractSeqChild(), nv);
		return reconstruct(seq);
	}

	@Override
	public String toString()
	{
		return "{\n" + getInteractSeqChild() + "\n}";  // Empty block will contain an blank line
	}

	
	
	
	
	
	
	
	
	
	private final InteractionSeq<K> seq;

	public ProtocolBlock(CommonTree source, InteractionSeq<K> seq)
	{
		super(source);
		this.seq = seq;
	}
	
	/*@Override
	public abstract ProtocolBlock<K> clone(AstFactory af);*/
}
