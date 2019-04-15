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
package org.scribble.core.type.name;

import org.scribble.core.type.kind.ProtoKind;


// Potentially qualified/canonical protocol name; not the AST primitive identifier
public abstract class ProtoName<K extends ProtoKind> extends MemberName<K>
{
	private static final long serialVersionUID = 1L;

	public ProtoName(K kind, ModuleName modname, ProtoName<K> membname)
	{
		super(kind, modname, membname);
	}
	
	public ProtoName(K kind, String simpname)
	{
		super(kind, simpname);
	}

	@Override
	public abstract ProtoName<K> getSimpleName();
}
