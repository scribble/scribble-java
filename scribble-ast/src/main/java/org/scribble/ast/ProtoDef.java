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

public abstract class ProtoDef<K extends ProtoKind> extends ScribNodeBase
		implements ProtoKindNode<K>
{
	public static final int BLOCK_CHILD_INDEX = 0;

	// ScribTreeAdaptor#create constructor
	public ProtoDef(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	protected ProtoDef(ProtoDef<K> node)
	{
		super(node);
	}
	public abstract ProtoBlock<K> getBlockChild();

	// "add", not "set"
	public void addScribChildren(ProtoBlock<K> block)
	{
		// Cf. above getters and Scribble.g children order
		addChild(block);
	}
	
	public abstract ProtoDef<K> dupNode();
	
	public ProtoDef<K> reconstruct(ProtoBlock<K> block)
	{
		ProtoDef<K> dup = dupNode();
		dup.addScribChildren(block);
		dup.setDel(del());  // No copy
		return dup;
	}
	
	@Override
	public ProtoDef<K> visitChildren(AstVisitor nv) throws ScribException
	{
		ProtoBlock<K> block = 
				visitChildWithClassEqualityCheck(this, getBlockChild(), nv);
		return reconstruct(block);
	}

	@Override
	public String toString()
	{
		return getBlockChild().toString();
	}
}
