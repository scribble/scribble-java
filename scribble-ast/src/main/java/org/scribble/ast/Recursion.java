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
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.core.job.ScribbleException;
import org.scribble.core.type.kind.ProtocolKind;
import org.scribble.del.ScribDel;
import org.scribble.util.Constants;
import org.scribble.visit.AstVisitor;

public abstract class Recursion<K extends ProtocolKind>
		extends CompoundInteraction<K>
{
	// ScribTreeAdaptor#create constructor
	public Recursion(Token t)
	{
		super(t);
		this.recvar = null;
		this.block = null;
	}

	// Tree#dupNode constructor
	protected Recursion(Recursion<K> node)
	{
		super(node);
		this.recvar = null;
		this.block = null;
	}
	
	public abstract Recursion<K> dupNode();
	
	public RecVarNode getRecVarChild()
	{
		return (RecVarNode) getChild(0);
	}
	
	public abstract ProtocolBlock<K> getBlockChild();

	public Recursion<K> reconstruct(RecVarNode recvar, ProtocolBlock<K> block)
	{
		Recursion<K> r = dupNode();
		ScribDel del = del();
		r.addChild(recvar);
		r.addChild(block);
		r.setDel(del);  // No copy
		return r;
	}

	@Override
	public Recursion<K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		RecVarNode recvar = (RecVarNode) visitChild(getRecVarChild(), nv);
		ProtocolBlock<K> block = visitChildWithClassEqualityCheck(this,
				getBlockChild(), nv);
		return reconstruct(recvar, block);
	}

	@Override
	public String toString()
	{
		return Constants.REC_KW + " " + getRecVarChild() + " " + getBlockChild();
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	private final RecVarNode recvar;
	private final ProtocolBlock<K> block;

	protected Recursion(CommonTree source, RecVarNode recvar, ProtocolBlock<K> block)
	{
		super(source);
		this.recvar = recvar;
		this.block = block;
	}
	
	/*@Override
	public abstract Recursion<K> clone(AstFactory af);*/

}
