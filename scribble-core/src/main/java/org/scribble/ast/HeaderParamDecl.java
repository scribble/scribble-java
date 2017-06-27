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
import org.scribble.ast.name.simple.SimpleNameNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ParamKind;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.AstVisitor;

// Names that are declared in a protocol header (roles and parameters -- not the protocol name though)
// RoleKind or (NonRole)ParamKind
public abstract class HeaderParamDecl<K extends ParamKind> extends NameDeclNode<K>
{
	protected HeaderParamDecl(CommonTree source, SimpleNameNode<K> name)
	{
		super(source, name);
	}

	public abstract HeaderParamDecl<K> reconstruct(SimpleNameNode<K> name);
	
	@Override
	public HeaderParamDecl<K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		SimpleNameNode<K> name = visitChildWithClassEqualityCheck(this, (SimpleNameNode<K>) this.name, nv);
		return reconstruct(name);
	}
	
	public abstract HeaderParamDecl<K> project(AstFactory af, Role self);  // Move to delegate?

	public abstract String getKeyword();
	
	@Override
	public String toString()
	{
		return getKeyword() + " " + getDeclName().toString();
	}
}
