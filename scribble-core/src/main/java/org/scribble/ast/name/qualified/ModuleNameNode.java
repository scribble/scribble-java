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

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.sesstype.kind.ModuleKind;
import org.scribble.sesstype.name.ModuleName;
import org.scribble.sesstype.name.PackageName;

public class ModuleNameNode extends QualifiedNameNode<ModuleKind>
{
	public ModuleNameNode(CommonTree source, String... ns)
	{
		super(source, ns);
	}

	@Override
	protected ModuleNameNode copy()
	{
		return new ModuleNameNode(this.source, this.elems);
	}
	
	@Override
	public ModuleNameNode clone(AstFactory af)
	{
		return (ModuleNameNode) af.QualifiedNameNode(this.source, ModuleKind.KIND, this.elems);
	}
	
	@Override
	public ModuleName toName()
	{
		ModuleName modname = new ModuleName(getLastElement());
		return isPrefixed()
				? new ModuleName(new PackageName(getPrefixElements()), modname)
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
		return ((ModuleNameNode) o).canEqual(this) && super.equals(o);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof ModuleNameNode;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 409;
		hash = 31 * hash + this.elems.hashCode();
		return hash;
	}
}
