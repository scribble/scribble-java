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
import org.scribble.ast.name.qualified.ModuleNameNode;
import org.scribble.core.type.kind.ModuleKind;
import org.scribble.core.type.name.ModuleName;
import org.scribble.del.DelFactory;
import org.scribble.util.Constants;
import org.scribble.util.ScribException;
import org.scribble.visit.AstVisitor;

public class ModuleDecl extends NameDeclNode<ModuleKind>
{
	// ScribTreeAdaptor#create constructor
	public ModuleDecl(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	protected ModuleDecl(ModuleDecl node)
	{
		super(node);
	}
	
	@Override
	public ModuleNameNode getNameNodeChild()
	{
		return (ModuleNameNode) getRawNameNodeChild();
	}

	// "add", not "set"
	public void addScribChildren(ModuleNameNode name)
	{
		// Cf. above getters and Scribble.g children order
		addChild(name);
	}

	// Cf. CommonTree#dupNode
	@Override
	public ModuleDecl dupNode()
	{
		return new ModuleDecl(this);
	}
	
	@Override
	public void decorateDel(DelFactory df)
	{
		df.ModuleDecl(this);
	}

	protected ModuleDecl reconstruct(ModuleNameNode name)
	{
		ModuleDecl dup = dupNode();
		dup.addScribChildren(name);
		dup.setDel(del());  // No copy
		return dup;
	}

	@Override
	public ModuleDecl visitChildren(AstVisitor nv) throws ScribException
	{
		ModuleNameNode name = (ModuleNameNode) visitChild(getNameNodeChild(), nv);
		return reconstruct(name);
	}

	public ModuleName getFullModuleName()
	{
		return (ModuleName) getNameNodeChild().toName();
	}

	@Override
	public ModuleName getDeclName()
	{
		return getNameNodeChild().toName().getSimpleName();  // Uniform with other NameDeclNodes wrt. returning simple name
	}

	@Override
	public String toString()
	{
		return Constants.MODULE_KW + " " + getFullModuleName() + ";";
	}
}
