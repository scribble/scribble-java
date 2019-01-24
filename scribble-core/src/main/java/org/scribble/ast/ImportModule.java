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
import org.scribble.type.kind.ModuleKind;
import org.scribble.type.name.ModuleName;

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

	// Cf. CommonTree#dupNode
	@Override
	public ImportModule dupNode()
	{
		return new ImportModule(this);
	}
	
	@Override
	public boolean isImportModule()
	{
		return true;
	}
	
	/*@Override
	public ModuleName getVisibleName()
	{
		return isAliased() ? getAlias() : this.modname.toName();
	}*/
	
	@Override
	public ModuleName getAlias()
	{
		return getAliasNameNodeChild().toName();
	}
	
	@Override
	public String toString()
	{
		String s = Constants.IMPORT_KW + " " + getModuleNameNodeChild();
		if (isAliased())
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
