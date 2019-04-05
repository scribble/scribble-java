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
package org.scribble.lang.context;

import java.util.Map;

import org.scribble.ast.DataTypeDecl;
import org.scribble.ast.ImportDecl;
import org.scribble.ast.ImportModule;
import org.scribble.ast.MessageSigNameDecl;
import org.scribble.ast.Module;
import org.scribble.ast.NonProtocolDecl;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.ast.local.LProtocolDecl;
import org.scribble.core.lang.context.ModuleContext;
import org.scribble.core.lang.context.ScribNames;
import org.scribble.core.type.name.DataType;
import org.scribble.core.type.name.GProtocolName;
import org.scribble.core.type.name.LProtocolName;
import org.scribble.core.type.name.MessageSigName;
import org.scribble.core.type.name.ModuleName;
import org.scribble.util.ScribException;

// TODO: rename and refactor
public class ModuleContextMaker
{
	private Module root;  // full name  // The root Module for this ModuleContext (cf. the "main" root module from CLI)

  // All transitive name dependencies of this module: all names fully qualified
	// The ScribNames maps are basically just used as sets (identity map)
	// Cf. ProtocolDeclContext protocol dependencies from protocoldecl as root
	private ScribNames deps;// = new ScribNames();

	// The modules and member names that are visible from this Module -- mapped to "cannonical" (fully qualified) names
	// visible names -> fully qualified names
  // Directly visible names from this module
	private ScribNames visible;// = new ScribNames();

	// Made by ModuleContextBuilder
	// ModuleContext is the root context
	public ModuleContextMaker() //JobContext jcontext, 
	{

	}
	
	public ModuleContext make(
			Map<ModuleName, Module> parsed,  // From MainContext (N.B., in a different non-visible package)
			Module root) throws ScribException
	{
		this.root = root;
		this.deps = new ScribNames();
		this.visible = new ScribNames();

		ModuleName fullname = this.root.getFullModuleName(); 
		ModuleName simpname = this.root.getModuleDeclChild().getDeclName();

		// All transitive dependencies (for inlined and subprotocol visiting)
		addModule(this.deps, this.root, fullname);
		addImportDependencies(parsed, this.root);

		// Names directly visible from this module
		addModule(this.visible, this.root, fullname);
		if (!fullname.equals(simpname))
		{
			addModule(this.visible, this.root, simpname);  
					// Adds simple name of root as visible, and members qualified by simple name
		}
		addVisible(parsed, root);  
				// Adds imports and members by their "direct" names (unqualified, except for no-alias imports)
		
		return new ModuleContext(root.getFullModuleName(), deps, visible);
	}
	

	// Register mod under name modname, modname could be the full module name or an import alias
	// Adds member names qualified by mod
	private static void addModule(ScribNames names, Module mod,
			ModuleName modname) throws ScribException
	{
		names.modules.put(modname, mod.getFullModuleName());
		for (NonProtocolDecl<?> npd : mod.getNonProtoDeclChildren())
		{
			if (npd.isDataTypeDecl())
			{
				DataTypeDecl dtd = (DataTypeDecl) npd;
				DataType qualif = new DataType(modname, dtd.getDeclName());
				names.data.put(qualif, dtd.getFullMemberName(mod));
			}
			else //if (npd.isMessageSigNameDecl())
			{
				MessageSigNameDecl msnd = (MessageSigNameDecl) npd;
				MessageSigName qualif = new MessageSigName(modname, msnd.getDeclName());
				names.sigs.put(qualif, msnd.getFullMemberName(mod));
			}
		}
		for (GProtocolDecl gpd : mod.getGProtoDeclChildren())
		{
			GProtocolName qualif = new GProtocolName(modname,
					gpd.getHeaderChild().getDeclName());
			names.globals.put(qualif, gpd.getFullMemberName(mod));
		}
		for (LProtocolDecl lpd : mod.getLProtoDeclChildren())
		{
			LProtocolName qualif = new LProtocolName(modname,
					lpd.getHeaderChild().getDeclName());
			names.locals.put(qualif, lpd.getFullMemberName(mod));
		}
	}
	
	// Could move to ImportModule but would need a defensive copy setter, or cache info in builder and create on leave
	private void addImportDependencies(Map<ModuleName, Module> parsed, Module mod)
			throws ScribException
	{
		for (ImportDecl<?> id : mod.getImportDeclChildren())
		{
			if (id.isImportModule())
			{
				ImportModule im = (ImportModule) id;
				ModuleName fullmodname = im.getModuleNameNodeChild().toName();
				if (!this.deps.modules.containsKey(fullmodname))
				{
					Module imported = parsed.get(fullmodname);  // TODO: Can get from MainContext instead of JobContext?
					addModule(this.deps, imported, fullmodname);  // Unlike for visible, only doing full names here
					addImportDependencies(parsed, imported);
				}
			}
			else
			{
				throw new RuntimeException("TODO: " + id);
			}
		}
	}

	// Adds "local" imports by alias or full name
	// Adds "local" members by simple names
	private void addVisible(Map<ModuleName, Module> parsed, Module root)
			throws ScribException
	{
		// Unlike for deps, visible is not done transitively
		for (ImportDecl<?> id : root.getImportDeclChildren())
		{
			if (id.isImportModule())
			{
				ImportModule im = (ImportModule) id;
				ModuleName fullname = im.getModuleNameNodeChild().toName();
				ModuleName visname = (im.hasAlias()) ? im.getAlias() : fullname;
						// getVisibleName doesn't use fullname
				if (this.visible.modules.containsKey(visname))
				{
					throw new ScribException(id.getSource(),
							"Duplicate visible module name: " + visname);
				}
				Module imported = parsed.get(fullname);  // TODO: Can get from MainContext instead of JobContext?
				addModule(this.visible, imported, visname);  
			}
			else
			{
				throw new RuntimeException("TODO: " + id);
			}
		}
		
		for (NonProtocolDecl<?> npd : root.getNonProtoDeclChildren())
		{
			if (npd.isDataTypeDecl())
			{
				DataTypeDecl dtd = (DataTypeDecl) npd;
				DataType visname = new DataType(dtd.getDeclName().toString());
				this.visible.data.put(visname, dtd.getFullMemberName(root));
			}
			else // if (npd.isMessageSigNameDecl())
			{
				MessageSigNameDecl msnd = (MessageSigNameDecl) npd;
				MessageSigName visname = new MessageSigName(
						msnd.getDeclName().toString());
				this.visible.sigs.put(visname, msnd.getFullMemberName(root));
			}
		}
		for (GProtocolDecl gpd : root.getGProtoDeclChildren())
		{
			GProtocolName visname = new GProtocolName(
					gpd.getHeaderChild().getDeclName().toString());
			this.visible.globals.put(visname, gpd.getFullMemberName(root));
		}
		for (LProtocolDecl lpd : root.getLProtoDeclChildren())
		{
			LProtocolName visname = new LProtocolName(
					lpd.getHeaderChild().getDeclName().toString());
			this.visible.locals.put(visname, lpd.getFullMemberName(root));
		}
	}

	/*@Override 
	public String toString()
	{
		return "Maker[deps=" + this.deps + ", visible=" + this.visible + "]";
	}*/
}
