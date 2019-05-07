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

public class ImportModule extends ImportDecl<ModuleKind>
{
	public static final int MODULENAME_CHILD = 0;
	public static final int ALIAS_CHILD = 1;

	// ScribTreeAdaptor#create constructor
	public ImportModule(Token payload)
	{
		super(payload);
	}

	// Tree#dupNode constructor
	protected ImportModule(ImportModule node)
	{
		super(node);
	}

	// Full name ("import x.y.Z")
	public ModuleNameNode getModuleNameNodeChild()
	{
		return (ModuleNameNode) getChild(MODULENAME_CHILD);
	}

	// Pre: hasAlias -- no alias means no child
	// Simple name
	// No child if no alias (cf. hasAlias) -- cf. addScribChildren, alias == null
	public ModuleNameNode getAliasNameNodeChild()
	{
		return (ModuleNameNode) getChild(ALIAS_CHILD);
	}

	// "add", not "set"
	public void addScribChildren(ModuleNameNode modname, ModuleNameNode alias)
	{
		// Cf. above getters and Scribble.g children order
		addChild(modname);
		if (alias != null)
		{
			addChild(alias);
		}
	}

	// Cf. CommonTree#dupNode
	@Override
	public ImportModule dupNode()
	{
		return new ImportModule(this);
	}
	
	@Override
	public void decorateDel(DelFactory df)
	{
		df.ImportModule(this);
	}
	
	// alias == null if no alias
	public ImportModule reconstruct(ModuleNameNode modname, ModuleNameNode alias)
	{
		ImportModule dup = dupNode();
		dup.addScribChildren(modname, alias);
		dup.setDel(del());  // No copy
		return dup;
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
		return getChildCount() > 1;
	}
	
	@Override
	public ModuleName getAlias()
	{
		return getAliasNameNodeChild().toName();
	}
	
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
}
