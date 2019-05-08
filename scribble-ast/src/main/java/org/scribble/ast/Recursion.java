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
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.core.type.kind.ProtoKind;
import org.scribble.util.Constants;
import org.scribble.util.ScribException;
import org.scribble.visit.AstVisitor;

public abstract class Recursion<K extends ProtoKind>
		extends CompoundInteraction<K>
{
	public static final int RECVAR_CHILD_INDEX = 0;
	public static final int BODY_CHILD_INDEX = 1;

	// ScribTreeAdaptor#create constructor
	public Recursion(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	protected Recursion(Recursion<K> node)
	{
		super(node);
	}
	
	public abstract Recursion<K> dupNode();
	
	public RecVarNode getRecVarChild()
	{
		return (RecVarNode) getChild(RECVAR_CHILD_INDEX);
	}

	// "add", not "set"
	public void addScribChildren(RecVarNode recvar, ProtoBlock<K> block)
	{
		// Cf. above getters and Scribble.g children order
		addChild(recvar);
		addChild(block);
	}
	
	public abstract ProtoBlock<K> getBlockChild();

	public Recursion<K> reconstruct(RecVarNode recvar, ProtoBlock<K> block)
	{
		Recursion<K> dup = dupNode();
		dup.addScribChildren(recvar, block);
		dup.setDel(del());  // No copy
		return dup;
	}

	@Override
	public Recursion<K> visitChildren(AstVisitor nv) throws ScribException
	{
		RecVarNode recvar = (RecVarNode) visitChild(getRecVarChild(), nv);
		ProtoBlock<K> block = visitChildWithClassEqualityCheck(this,
				getBlockChild(), nv);
		return reconstruct(recvar, block);
	}

	@Override
	public String toString()
	{
		return Constants.REC_KW + " " + getRecVarChild() + " " + getBlockChild();
	}
}
