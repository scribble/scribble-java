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
package org.scribble.ast.global;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.Recursion;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.core.type.kind.Global;


public class GRecursion extends Recursion<Global>
		implements GCompoundSessionNode
{
	// ScribTreeAdaptor#create constructor
	public GRecursion(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	protected GRecursion(GRecursion node)
	{
		super(node);
	}
	
	@Override
	public GRecursion dupNode()
	{
		return new GRecursion(this);
	}

	@Override
	public GProtocolBlock getBlockChild()
	{
		return (GProtocolBlock) getChild(1);
	}

	public GRecursion(CommonTree source, RecVarNode recvar, GProtocolBlock block)
	{
		super(source, recvar, block);
	}

	/*@Override
	protected GRecursion copy()
	{
		return new GRecursion(this.source, this.recvar, getBlockChild());
	}
	
	@Override
	public GRecursion clone(AstFactory af)
	{
		RecVarNode recvar = this.recvar.clone(af);
		GProtocolBlock block = getBlockChild().clone(af);
		return af.GRecursion(this.source, recvar, block);
	}

	@Override
	public GRecursion reconstruct(RecVarNode recvar, ProtocolBlock<Global> block)
	{
		ScribDel del = del();
		GRecursion gr = new GRecursion(this.source, recvar, (GProtocolBlock) block);
		gr = (GRecursion) gr.del(del);
		return gr;
	}*/
}
