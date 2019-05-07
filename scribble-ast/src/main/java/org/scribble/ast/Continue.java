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

public abstract class Continue<K extends ProtoKind>
		extends SimpleSessionNode<K>
{
	public static final int RECVAR_CHILD_INDEX = 0;
	
	// ScribTreeAdaptor#create constructor
	public Continue(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	protected Continue(Continue<K> node)
	{
		super(node);
	}
	
	// "add", not "set"
	public void addScribChildren(RecVarNode rv)
	{
		// Cf. above getters and Scribble.g children order
		addChild(rv);
	}
	
	public abstract Continue<K> dupNode();

	public RecVarNode getRecVarChild()
	{
		return (RecVarNode) getChild(RECVAR_CHILD_INDEX);
	}

	public Continue<K> reconstruct(RecVarNode rv)
	{
		Continue<K> dup = dupNode();
		dup.addScribChildren(rv);
		dup.setDel(del());  // No copy
		return dup;
	}

	@Override
	public Continue<K> visitChildren(AstVisitor nv) throws ScribException
	{
		RecVarNode recvar = (RecVarNode) visitChild(getRecVarChild(), nv);
		return reconstruct(recvar);
	}

	@Override
	public String toString()
	{
		return Constants.CONTINUE_KW + " " + getRecVarChild() + ";";
	}
}
