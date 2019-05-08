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
package org.scribble.ast.name.qualified;

import org.antlr.runtime.Token;
import org.scribble.core.type.kind.ModuleKind;
import org.scribble.core.type.name.ModuleName;
import org.scribble.core.type.name.PackageName;
import org.scribble.del.DelFactory;

public class ModuleNameNode extends QualNameNode<ModuleKind>
{
	// ScribTreeAdaptor#create constructor
	public ModuleNameNode(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	protected ModuleNameNode(ModuleNameNode node)
	{
		super(node);
	}
	
	@Override
	public ModuleNameNode dupNode()
	{
		return new ModuleNameNode(this);
	}
	
	@Override
	public void decorateDel(DelFactory df)
	{
		df.ModuleNameNode(this);
	}
	
	@Override
	public ModuleName toName()
	{
		ModuleName modname = new ModuleName(getLastElement());
		return isPrefixed()
				? new ModuleName(
						new PackageName(getPrefixElements().toArray(new String[0])),
						modname)
				: modname;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof ModuleNameNode))
		{
			return false;
		}
		return ((ModuleNameNode) o).canEquals(this) && super.equals(o);
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof ModuleNameNode;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 409;
		hash = 31 * hash + super.hashCode();
		return hash;
	}
}
