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

import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.name.qualified.ModuleNameNode;
import org.scribble.del.ScribDel;
import org.scribble.main.ScribbleException;
import org.scribble.type.kind.ModuleKind;
import org.scribble.type.name.ModuleName;
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

	public ModuleName getFullModuleName()
	{
		return (ModuleName) getNameNodeChild().toName();
	}

	// Cf. CommonTree#dupNode
	@Override
	public ModuleDecl dupNode()
	{
		return new ModuleDecl(this);
	}

	protected ModuleDecl reconstruct(ModuleNameNode name)
	{
		ModuleDecl md = dupNode();
		ScribDel del = del();
		List<ScribNode> children = new LinkedList<>();
		children.add(name);
		md.setChildren(children);
		md.setDel(del);  // No copy
		return md;
	}

	@Override
	public ModuleDecl visitChildren(AstVisitor nv) throws ScribbleException
	{
		ModuleNameNode name = (ModuleNameNode) visitChild(getNameNodeChild(), nv);
		//return nv.job.af.ModuleDecl(this.source, fullmodname);  // cf., reconstruct
		return reconstruct(name);
	}
	
	@Override
	public ModuleNameNode getNameNodeChild()
	{
		return (ModuleNameNode) getRawNameNodeChild();
	}

	@Override
	public ModuleName getDeclName()
	{
		//return (ModuleName) super.getDeclName();  // Would return full name
		//return ((ModuleName) super.getDeclName()).getSimpleName();  // Uniform with other NameDeclNodes wrt. returning simple name
		return getNameNodeChild().toName().getSimpleName();  // Uniform with other NameDeclNodes wrt. returning simple name
	}

	@Override
	public String toString()
	{
		return Constants.MODULE_KW + " " + getFullModuleName() + ";";
	}

	
	
	
	
	
	
	
	// Deprecate

	public ModuleDecl(CommonTree source, ModuleNameNode fullmodname)
	{
		super(source, fullmodname);
	}

	/*@Override
	protected ModuleDecl copy()
	{
		return new ModuleDecl(this.source, (ModuleNameNode) this.name);
	}*/
	
	/*@Override
	public ModuleDecl clone(AstFactory af)
	{
		ModuleNameNode modname = (ModuleNameNode) this.name.clone(af);
		return af.ModuleDecl(this.source, modname);
	}*/
}
