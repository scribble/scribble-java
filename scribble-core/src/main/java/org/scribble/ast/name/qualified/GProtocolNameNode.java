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
import org.scribble.type.kind.Global;
import org.scribble.type.name.GProtocolName;

public class GProtocolNameNode extends ProtocolNameNode<Global>
{
	public GProtocolNameNode(CommonTree source, String... ns)
	{
		super(source, ns);
	}

	@Override
	protected GProtocolNameNode copy()
	{
		return new GProtocolNameNode(this.source, this.elems);
	}
	
	@Override
	public GProtocolNameNode clone(AstFactory af)
	{
		return (GProtocolNameNode) af.QualifiedNameNode(this.source, Global.KIND, this.elems);
	}
	
	@Override
	public GProtocolName toName()
	{
		GProtocolName membname = new GProtocolName(getLastElement());
		return isPrefixed()
				? new GProtocolName(getModuleNamePrefix(), membname)
				: membname;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof GProtocolNameNode))
		{
			return false;
		}
		return ((GProtocolNameNode) o).canEqual(this) && super.equals(o);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof GProtocolNameNode;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 419;
		hash = 31 * hash + this.elems.hashCode();
		return hash;
	}
}
