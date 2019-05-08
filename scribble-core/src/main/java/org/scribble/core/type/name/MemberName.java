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

import org.scribble.core.type.kind.Kind;


// Simple name or qualified name
public abstract class MemberName<K extends Kind> extends QualName<K>
{
	private static final long serialVersionUID = 1L;
	
	public MemberName(K kind, ModuleName modname, Name<K> membname)
	{
		super(kind, compileMemberName(modname, membname));
	}
	
	public MemberName(K kind, String simplename)
	{
		super(kind, Name.compileElements(ModuleName.EMPTY_MODULENAME.getElements(), simplename));
	}
	
	@Override
	public ModuleName getPrefix()
	{
		return new ModuleName(getPrefixElements());
	}
	
	// Similar in ModuleName
	private static String[] compileMemberName(ModuleName modname, Name<? extends Kind> membname)
	{
		return Name.compileElements(modname.getElements(), membname.getLastElement());
	}
}
