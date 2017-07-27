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
import org.scribble.ast.MessageNode;
import org.scribble.type.kind.SigKind;
import org.scribble.type.name.MessageSigName;

public class MessageSigNameNode extends MemberNameNode<SigKind> implements MessageNode
{
	public MessageSigNameNode(CommonTree source, String... elems)
	{
		super(source, elems);
	}
	
	@Override
	public MessageNode project(AstFactory af)
	{
		return this;
	}

	@Override
	protected MessageSigNameNode copy()
	{
		return new MessageSigNameNode(this.source, this.elems);
	}
	
	@Override
	public MessageSigNameNode clone(AstFactory af)
	{
		return (MessageSigNameNode) af.QualifiedNameNode(this.source, SigKind.KIND, this.elems);
	}
	
	@Override
	public MessageSigName toName()
	{
		MessageSigName membname = new MessageSigName(getLastElement());
		return isPrefixed()
				? new MessageSigName(getModuleNamePrefix(), membname)
				: membname;
	}

	@Override
	public boolean isMessageSigNameNode()
	{
		return true;
	}

	@Override
	public MessageSigName toMessage()  // Difference between toName and toMessage is scope? does that make sense?
	{
		return toName();
	}

	@Override
	public MessageSigName toArg()
	{
		return toMessage();
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof MessageSigNameNode))
		{
			return false;
		}
		return ((MessageSigNameNode) o).canEqual(this) && super.equals(o);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof MessageSigNameNode;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 421;
		hash = 31 * hash + this.elems.hashCode();
		return hash;
	}
}
