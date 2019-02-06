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
package org.scribble.ast.context;

import java.util.HashMap;
import java.util.Map;

import org.scribble.ast.DataTypeDecl;
import org.scribble.ast.ImportDecl;
import org.scribble.ast.ImportModule;
import org.scribble.ast.MessageSigNameDecl;
import org.scribble.ast.Module;
import org.scribble.ast.NonProtocolDecl;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.ast.local.LProtocolDecl;
import org.scribble.job.ScribbleException;
import org.scribble.type.kind.Global;
import org.scribble.type.kind.Kind;
import org.scribble.type.kind.ProtocolKind;
import org.scribble.type.kind.SigKind;
import org.scribble.type.name.DataType;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.LProtocolName;
import org.scribble.type.name.MessageSigName;
import org.scribble.type.name.ModuleName;
import org.scribble.type.name.Name;
import org.scribble.type.name.ProtocolName;

// Context information specific to each module as a root (wrt. to visitor passes)
// CHECKME: move to main package?
public class ModuleContext
{
	public final ModuleName root;  // full name  // The root Module for this ModuleContext (cf. the "main" root module from CLI)

  // All transitive name dependencies of this module: all names fully qualified
	// The ScribNames maps are basically just used as sets (identity map)
	// Cf. ProtocolDeclContext protocol dependencies from protocoldecl as root
	private final ScribNames deps = new ScribNames();

	// The modules and member names that are visible from this Module -- mapped to "cannonical" (fully qualified) names
	// visible names -> fully qualified names
  // Directly visible names from this module
	private final ScribNames visible = new ScribNames();

	// Made by ModuleContextBuilder
	// ModuleContext is the root context
	public ModuleContext(//JobContext jcontext, 
			Map<ModuleName, Module> parsed,  // From MainContext (N.B., in a different non-visible package)
			Module root) throws ScribbleException
	{
		ModuleName fullname = root.getFullModuleName(); 
		ModuleName simpname = root.getModuleDeclChild().getDeclName();

		this.root = fullname;
		
		// All transitive dependencies (for inlined and subprotocol visiting)
		addModule(this.deps, root, fullname);
		addImportDependencies(parsed, root);

		// Names directly visible from this module
		addModule(this.visible, root, fullname);
		if (!fullname.equals(simpname))
		{
			addModule(this.visible, root, simpname);  
					// Adds simple name of root as visible, and members qualified by simple name
		}
		addVisible(parsed, root);  
				// Adds imports and members by their "direct" names (unqualified, except for no-alias imports)
	}

	// Register mod under name modname, modname could be the full module name or an import alias
	// Adds member names qualified by mod
	private static void addModule(ScribNames names, Module mod,
			ModuleName modname) throws ScribbleException
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
			throws ScribbleException
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
			throws ScribbleException
	{
		// Unlike for deps, visible is not done transitively
		for (ImportDecl<?> id : root.getImportDeclChildren())
		{
			if (id.isImportModule())
			{
				ImportModule im = (ImportModule) id;
				ModuleName fullname = im.getModuleNameNodeChild().toName();
				ModuleName visname = (im.isAliased()) ? im.getAlias() : fullname;
						// getVisibleName doesn't use fullname
				if (this.visible.modules.containsKey(visname))
				{
					throw new ScribbleException(id.getSource(),
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

	/*public boolean isDataTypeDependency(DataType typename)
	{
		return this.deps.data.keySet().contains(typename);
	}

	public boolean isMessageSigNameDependency(Name<? extends SigKind> signame)
	{
		return this.deps.sigs.containsKey(signame);
	}*/

	// TODO: deprecate -- now redundant: proto should already be full name by namedisamb (and this.deps only stores full names)
	// Refactored as a "check" for now (although still redundant, not actually checking anything)
	public <K extends ProtocolKind> ProtocolName<K> checkProtocolDeclDependencyFullName(
			ProtocolName<K> proto)
	{
		return getProtocolDeclFullName(this.deps, proto);
	}

	public boolean isDataTypeVisible(DataType typename)
	{
		return this.visible.data.keySet().contains(typename);
	}

	public boolean isMessageSigNameVisible(Name<? extends SigKind> signame)
	{
		return this.visible.sigs.containsKey(signame);
	}
	
	public DataType getVisibleDataTypeFullName(DataType visname)
	{
		return getFullName(this.visible.data, visname);
	}
	
	public boolean isVisibleDataType(DataType visname)
	{
		return this.visible.isVisibleDataType(visname);
	}

	public MessageSigName getVisibleMessageSigNameFullName(MessageSigName visname)
	{
		return getFullName(this.visible.sigs, visname);
	}
	
	public <K extends ProtocolKind> ProtocolName<K> getVisibleProtocolDeclFullName(
			ProtocolName<K> visname)
	{
		return getProtocolDeclFullName(this.visible, visname);
	}
	
	public <K extends ProtocolKind> boolean isVisibleProtocolDeclName(
			ProtocolName<K> visname)
	{
		return this.visible.isVisibleProtocolDeclName(visname);
	}

	public static <K extends ProtocolKind> ProtocolName<K> getProtocolDeclFullName(
			ScribNames names, ProtocolName<K> proto)
	{
		ProtocolName<? extends ProtocolKind> pn = (proto.getKind()
				.equals(Global.KIND))
						? getFullName(names.globals, (GProtocolName) proto)
						: getFullName(names.locals, (LProtocolName) proto);
		@SuppressWarnings("unchecked")
		ProtocolName<K> tmp = (ProtocolName<K>) pn;
		return tmp;
	}

	private static <T extends Name<K>, K extends Kind> T getFullName(
			Map<T, T> map, T visname)
	{
		if (!map.containsKey(visname))
		{
			// FIXME: runtime exception bad -- make a guard method
			throw new RuntimeException("Unknown name: " + visname);
		}
		return map.get(visname);
	}

	@Override 
	public String toString()
	{
		return "[deps=" + this.deps + ", visible=" + this.visible + "]";
	}
}

class ScribNames
{
	// names -> fully qualified names
	protected final Map<ModuleName, ModuleName> modules = new HashMap<>();
	protected final Map<DataType, DataType> data = new HashMap<>();
	protected final Map<MessageSigName, MessageSigName> sigs = new HashMap<>();
	protected final Map<GProtocolName, GProtocolName> globals = new HashMap<>();
	protected final Map<LProtocolName, LProtocolName> locals = new HashMap<>();
	
	@Override
	public String toString()
	{
		return "(modules=" + this.modules + ", types=" + this.data + ", sigs=" + this.sigs
				+ ", globals=" + this.globals + ", locals=" + this.locals + ")";
	}
	
	public <K extends ProtocolKind> boolean isVisibleProtocolDeclName(ProtocolName<K> visname)
	{
		return this.globals.containsKey(visname) || this.locals.containsKey(visname);
	}
	
	public boolean isVisibleDataType(DataType visname)
	{
		return this.data.containsKey(visname);
	}
}	
