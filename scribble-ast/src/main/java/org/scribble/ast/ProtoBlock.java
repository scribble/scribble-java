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
import org.scribble.util.ScribException;
import org.scribble.visit.AstVisitor;

public abstract class ProtoBlock<K extends ProtoKind>
		extends ScribNodeBase
{
	public static final int SEQ_CHILD_INDEX = 0;

	// ScribTreeAdaptor#create constructor
	public ProtoBlock(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	protected ProtoBlock(ProtoBlock<K> node)
	{
		super(node);
	}

	public abstract InteractionSeq<K> getInteractSeqChild();
	
	public boolean isEmpty()
	{
		return getInteractSeqChild().isEmpty();
	}

	// "add", not "set"
	public void addScribChildren(InteractionSeq<K> seq)
	{
		// Cf. above getters and Scribble.g children order
		addChild(seq);
	}
	
	@Override
	public abstract ProtoBlock<K> dupNode();

	public ProtoBlock<K> reconstruct(InteractionSeq<K> seq)
	{
		ProtoBlock<K> dup = dupNode();
		dup.addScribChildren(seq);
		dup.setDel(del());  // No copy
		return dup;
	}

	@Override
	public ProtoBlock<K> visitChildren(AstVisitor nv) throws ScribException
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
}
