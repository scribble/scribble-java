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
package org.scribble.sesstype.name;

import org.scribble.sesstype.kind.ProtocolKind;


// Potentially qualified/canonical protocol name; not the AST primitive identifier
public abstract class ProtocolName<K extends ProtocolKind> extends MemberName<K>
{
	private static final long serialVersionUID = 1L;

	public ProtocolName(K kind, ModuleName modname, ProtocolName<K> membname)
	{
		super(kind, modname, membname);
	}
	
	public ProtocolName(K kind, String simpname)
	{
		super(kind, simpname);
	}

	@Override
	public abstract ProtocolName<K> getSimpleName();
}
