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

public abstract class Continue<K extends ProtocolKind> extends SimpleInteractionNode<K>
{
	public final RecVarNode recvar;

	protected Continue(CommonTree source, RecVarNode recvar)
	{
		super(source);
		this.recvar = recvar;
	}

	public abstract Continue<K> reconstruct(RecVarNode recvar);

	@Override
	public Continue<K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		RecVarNode recvar = (RecVarNode) visitChild(this.recvar, nv);
		return reconstruct(recvar);
	}

	@Override
	public String toString()
	{
		return Constants.CONTINUE_KW + " " + this.recvar + ";";
	}
}
