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

import java.util.HashMap;
import java.util.Map;

import org.scribble.core.type.name.DataName;
import org.scribble.core.type.name.GProtoName;
import org.scribble.core.type.name.LProtoName;
import org.scribble.core.type.name.SigName;
import org.scribble.core.type.name.ModuleName;
import org.scribble.core.type.name.ProtoName;

// TODO: fix mutable public collection fields -- currently hacked for ModuleContext(Collector) internal use
// TODO: rename better
public class ScribNames
{
	// names -> fully qualified names
	public final Map<ModuleName, ModuleName> modules = new HashMap<>();
	public final Map<DataName, DataName> data = new HashMap<>();
	public final Map<SigName, SigName> sigs = new HashMap<>();
	public final Map<GProtoName, GProtoName> globals = new HashMap<>();
	public final Map<LProtoName, LProtoName> locals = new HashMap<>();
	
	@Override
	public String toString()
	{
		return "(modules="
				+ this.modules + ", types=" + this.data + ", sigs=" + this.sigs
				+ ", globals=" + this.globals + ", locals=" + this.locals
				+ ")";
	}

	public boolean isVisibleProtocolDeclName(ProtoName<?> visname)
	{
		return this.globals.containsKey(visname)
				|| this.locals.containsKey(visname);
	}

	public boolean isVisibleDataType(DataName visname)
	{
		return this.data.containsKey(visname);
	}
}