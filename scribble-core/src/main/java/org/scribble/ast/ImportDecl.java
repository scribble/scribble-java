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
import org.scribble.ast.name.qualified.ModuleNameNode;
import org.scribble.del.ScribDel;
import org.scribble.job.ScribbleException;
import org.scribble.type.kind.ImportKind;
import org.scribble.type.name.Name;
import org.scribble.visit.AstVisitor;

// TODO: factor out stuff from ImportModule and ImportMember into here, e.g. alias/name, reconstruct
public abstract class ImportDecl<K extends ImportKind> extends ScribNodeBase//, ModuleMember //implements NameDeclaration 
{
	// ScribTreeAdaptor#create constructor
	public ImportDecl(Token payload)
	{
		super(payload);
	}

	// Tree#dupNode constructor
	protected ImportDecl(ImportDecl<K> node)
	{
		super(node);
	}
	
	public abstract ImportDecl<K> dupNode();
	
	public abstract Name<K> getAlias();
	//public abstract Name<K> getVisibleName();
	
	public boolean isImportModule()
	{
		return false;
	}
	
	public ModuleNameNode getModuleNameNodeChild()
	{
		return (ModuleNameNode) getChild(0);
	}

	public ModuleNameNode getAliasNameNodeChild()
	{
		return (ModuleNameNode) getChild(1);
	}
	
	public boolean isAliased()
	{
		//return this.alias != null;
		return getChildCount() > 1;
	}
	
	// alias == null if no alias
	public ImportDecl<K> reconstruct(ModuleNameNode modname, ModuleNameNode alias)
	{
		ImportDecl<K> im = dupNode();
		ScribDel del = del();
		im.addChild(modname);
		if (alias != null)
		{
			im.addChild(alias);
		}
		im.setDel(del);  // No copy
		return im;
	}

	@Override
	public ImportDecl<K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		ModuleNameNode modname = (ModuleNameNode) 
				visitChild(getModuleNameNodeChild(), nv);
		ModuleNameNode alias = isAliased()
				? (ModuleNameNode) visitChild(getAliasNameNodeChild(), nv) 
				: null;
		return reconstruct(modname, alias);
	}

	
	
	
	
	
	
	
	
	protected ImportDecl(CommonTree source)
	{
		super(source);
	}
}
