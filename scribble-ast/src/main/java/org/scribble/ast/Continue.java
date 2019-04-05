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

public abstract class Continue<K extends ProtocolKind>
		extends SimpleSessionNode<K>
{
	// ScribTreeAdaptor#create constructor
	public Continue(Token t)
	{
		super(t);
		this.recvar = null;
	}

	// Tree#dupNode constructor
	protected Continue(Continue<K> node)
	{
		super(node);
		this.recvar = null;
	}
	
	public abstract Continue<K> dupNode();

	public RecVarNode getRecVarChild()
	{
		return (RecVarNode) getChild(0);
	}

	public Continue<K> reconstruct(RecVarNode recvar)
	{
		Continue<K> r = dupNode();
		ScribDel del = del();
		r.addChild(recvar);
		r.setDel(del);  // No copy
		return r;
	}

	@Override
	public Continue<K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		RecVarNode recvar = (RecVarNode) visitChild(getRecVarChild(), nv);
		return reconstruct(recvar);
	}

	@Override
	public String toString()
	{
		return Constants.CONTINUE_KW + " " + getRecVarChild() + ";";
	}
	
	
	
	
	
	
	
	
	
	
	private final RecVarNode recvar;

	protected Continue(CommonTree source, RecVarNode recvar)
	{
		super(source);
		this.recvar = recvar;
	}
}
