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
import org.scribble.ast.name.NameNode;
import org.scribble.core.type.kind.ParamKind;
import org.scribble.util.ScribException;
import org.scribble.visit.AstVisitor;

// Names that are declared in a protocol header (roles and parameters -- not the protocol name though)
// RoleKind or (NonRole)ParamKind
public abstract class ParamDecl<K extends ParamKind>
		extends NameDeclNode<K>
{
	// ScribTreeAdaptor#create constructor
	public ParamDecl(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public ParamDecl(ParamDecl<K> node)
	{
		super(node);
	}
	
	@Override
	public abstract NameNode<K> getNameNodeChild();  // Always a "simple" name (e.g., like Role), but Type/Sig names are not SimpleNames

	public abstract String getKeyword();

	// "add", not "set"
	public void addScribChildren(NameNode<K> name)
	{
		// Cf. above getters and Scribble.g children order
		addChild(name);
	}

	public abstract ParamDecl<K> dupNode();

	public ParamDecl<K> reconstruct(NameNode<K> name)  // Always a "simple" name (e.g., like Role), but Type/Sig names are not SimpleNames
	{
		ParamDecl<K> dup = dupNode();
		dup.addScribChildren(name);
		dup.setDel(del());  // No copy
		return dup;
	}
	
	@Override
	public ParamDecl<K> visitChildren(AstVisitor nv)
			throws ScribException
	{
		NameNode<K> name = 
				visitChildWithClassEqualityCheck(this, getNameNodeChild(), nv);
		return reconstruct(name);
	}
	
	@Override
	public String toString()
	{
		return getKeyword() + " " + getDeclName().toString();
	}
}
