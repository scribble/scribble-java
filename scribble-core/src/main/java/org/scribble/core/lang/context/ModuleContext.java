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
package org.scribble.core.lang.context;

import java.util.Map;

import org.scribble.core.type.kind.Global;
import org.scribble.core.type.kind.Kind;
import org.scribble.core.type.kind.ProtocolKind;
import org.scribble.core.type.kind.SigKind;
import org.scribble.core.type.name.DataType;
import org.scribble.core.type.name.GProtocolName;
import org.scribble.core.type.name.LProtocolName;
import org.scribble.core.type.name.MessageSigName;
import org.scribble.core.type.name.ModuleName;
import org.scribble.core.type.name.Name;
import org.scribble.core.type.name.ProtocolName;
import org.scribble.util.ScribException;

// Context information specific to each module as a root (wrt. to visitor passes)
// CHECKME: currently unused within core -- refactor out to lang package?
public class ModuleContext
{
	public final ModuleName root;  // full name  // The root Module for this ModuleContext (cf. the "main" root module from CLI)

  // All transitive name dependencies of this module: all names fully qualified
	// The ScribNames maps are basically just used as sets (identity map)
	// Cf. ProtocolDeclContext protocol dependencies from protocoldecl as root
	private final ScribNames deps;

	// The modules and member names that are visible from this Module -- mapped to "cannonical" (fully qualified) names
	// visible names -> fully qualified names
  // Directly visible names from this module
	private final ScribNames visible;

	// Made by ModuleContextBuilder
	// ModuleContext is the root context
	public ModuleContext(ModuleName root, ScribNames deps, ScribNames visible) throws ScribException
	{
		this.root = root;
		this.deps = deps;
		this.visible = visible;
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
