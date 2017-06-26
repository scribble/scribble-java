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
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.visit.AstVisitor;

public abstract class Recursion<K extends ProtocolKind> extends CompoundInteractionNode<K>
{
	public final RecVarNode recvar;
	public final ProtocolBlock<K> block;

	protected Recursion(CommonTree source, RecVarNode recvar, ProtocolBlock<K> block)
	{
		super(source);
		this.recvar = recvar;
		this.block = block;
	}

	public abstract Recursion<K> reconstruct(RecVarNode recvar, ProtocolBlock<K> block);
	
	@Override
	public abstract Recursion<K> clone(AstFactory af);

	@Override
	public Recursion<K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		RecVarNode recvar = (RecVarNode) visitChild(this.recvar, nv);
		ProtocolBlock<K> block = visitChildWithClassEqualityCheck(this, this.block, nv);
		return reconstruct(recvar, block);
	}
	
	public abstract ProtocolBlock<K> getBlock();

	@Override
	public String toString()
	{
		return Constants.REC_KW + " " + this.recvar + " " + block;
	}
}
