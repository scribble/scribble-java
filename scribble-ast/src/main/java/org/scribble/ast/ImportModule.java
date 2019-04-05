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
import org.scribble.core.type.kind.ModuleKind;
import org.scribble.core.type.name.ModuleName;
import org.scribble.del.ScribDel;
import org.scribble.util.Constants;
import org.scribble.util.ScribException;
import org.scribble.visit.AstVisitor;

public class ImportModule extends ImportDecl<ModuleKind>
{
	// ScribTreeAdaptor#create constructor
	public ImportModule(Token payload)
	{
		super(payload);
		this.modname = null;
		this.alias = null;
	}

	// Tree#dupNode constructor
	protected ImportModule(ImportModule node)
	{
		super(node);
		this.modname = null;
		this.alias = null;
	}

	// Full name ("import x.y.Z")
	public ModuleNameNode getModuleNameNodeChild()
	{
		return (ModuleNameNode) getChild(0);
	}

	// Pre: hasAlias
	// Simple name
	// No child (null) if no alias
	public ModuleNameNode getAliasNameNodeChild()
	{
		return (ModuleNameNode) getChild(1);
	}

	// Cf. CommonTree#dupNode
	@Override
	public ImportModule dupNode()
	{
		return new ImportModule(this);
	}
	
	// alias == null if no alias
	public ImportModule reconstruct(ModuleNameNode modname, ModuleNameNode alias)
	{
		ImportModule im = dupNode();
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
	public ImportModule visitChildren(AstVisitor nv) throws ScribException
	{
		ModuleNameNode modname = (ModuleNameNode) 
				visitChild(getModuleNameNodeChild(), nv);
		ModuleNameNode alias = hasAlias()
				? (ModuleNameNode) visitChild(getAliasNameNodeChild(), nv) 
				: null;
		return reconstruct(modname, alias);
	}
	
	@Override
	public boolean isImportModule()
	{
		return true;
	}
	
	@Override
	public boolean hasAlias()
	{
		//return this.alias != null;
		return getChildCount() > 1;
	}
	
	@Override
	public ModuleName getAlias()
	{
		return getAliasNameNodeChild().toName();
	}
	
	/*@Override
	public ModuleName getVisibleName()
	{
		return isAliased() ? getAlias() : this.modname.toName();
	}*/
	
	@Override
	public String toString()
	{
		String s = Constants.IMPORT_KW + " " + getModuleNameNodeChild();
		if (hasAlias())
		{
			s += " " + Constants.AS_KW + " " + getAlias();
		}
		return s + ";";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private final ModuleNameNode modname;
	private final ModuleNameNode alias;  // Factor up to ImportDecl

	public ImportModule(CommonTree source, ModuleNameNode modname, ModuleNameNode alias)
	{
		super(source);
		this.modname = modname;
		this.alias = alias;
	}

	/*@Override
	protected ImportModule copy()
	{
		return new ImportModule(this.source, this.modname, this.alias);
	}
	
	@Override
	public ImportModule clone(AstFactory af)
	{
		ModuleNameNode name = (ModuleNameNode) this.modname.clone(af);
		ModuleNameNode alias = (ModuleNameNode) this.alias.clone(af);
		return af.ImportModule(this.source, name, alias);
	}*/
}
