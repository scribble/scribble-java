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
import org.scribble.ast.name.NameNode;
import org.scribble.ast.name.simple.SimpleNameNode;
import org.scribble.core.job.ScribbleException;
import org.scribble.core.type.kind.ParamKind;
import org.scribble.core.type.name.Role;
import org.scribble.del.ScribDel;
import org.scribble.visit.AstVisitor;

// Names that are declared in a protocol header (roles and parameters -- not the protocol name though)
// RoleKind or (NonRole)ParamKind
public abstract class HeaderParamDecl<K extends ParamKind>
		extends NameDeclNode<K>
{
	// ScribTreeAdaptor#create constructor
	public HeaderParamDecl(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public HeaderParamDecl(HeaderParamDecl<K> node)
	{
		super(node);
	}
	
	@Override
	public abstract NameNode<K> getNameNodeChild();  // Always a "simple" name (e.g., like Role), but Type/Sig names are not SimpleNames

	public abstract HeaderParamDecl<K> dupNode();

	public HeaderParamDecl<K> reconstruct(NameNode<K> name)  // Always a "simple" name (e.g., like Role), but Type/Sig names are not SimpleNames
	{
		HeaderParamDecl<K> pd = dupNode();
		ScribDel del = del();
		pd.addChild(name);
		pd.setDel(del);  // No copy
		return pd;
	}
	
	@Override
	public HeaderParamDecl<K> visitChildren(AstVisitor nv)
			throws ScribbleException
	{
		NameNode<K> name = 
				visitChildWithClassEqualityCheck(this, getNameNodeChild(), nv);
		return reconstruct(name);
	}

	public abstract String getKeyword();
	
	public abstract HeaderParamDecl<K> project(AstFactory af, Role self);  // Move to delegate?
	
	@Override
	public String toString()
	{
		return getKeyword() + " " + getDeclName().toString();
	}
	
	
	
	
	
	
	
	
	
	
	

	protected HeaderParamDecl(CommonTree source, SimpleNameNode<K> name)
	{
		super(source, name);
	}
}
