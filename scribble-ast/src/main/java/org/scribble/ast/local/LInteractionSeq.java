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
package org.scribble.ast.local;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.InteractionSeq;
import org.scribble.core.type.kind.Local;
import org.scribble.core.type.session.Message;

public class LInteractionSeq extends InteractionSeq<Local> implements LScribNode
{
	// ScribTreeAdaptor#create constructor
	public LInteractionSeq(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public LInteractionSeq(LInteractionSeq node)
	{
		super(node);
	}
	
	@Override
	public LInteractionSeq dupNode()
	{
		return new LInteractionSeq(this);
	}
	
	@Override
	public List<LSessionNode> getInteractionChildren()
	{
		return getChildren().stream().map(n -> (LSessionNode) n)
				.collect(Collectors.toList());
	}

	@Override	
	public boolean isLocal()
	{
		return true;
	}

	public Set<Message> getEnabling()
	{
		if (!this.isEmpty())
		{
			for (LSessionNode ln : getInteractionChildren())
			{
				Set<Message> enab = ln.getEnabling();
				if (!enab.isEmpty())
				{
					return enab;
				}
			}
		}
		return Collections.emptySet();
	}
	
	
	
	
	
	
	
	
	
	
	
	

	public LInteractionSeq(CommonTree source, List<LSessionNode> lis)
	{
		super(source, lis);
	}

	/*@Override
	protected ScribNodeBase copy()
	{
		return new LInteractionSeq(this.source, getInteractNodeChildren());
	}
	
	@Override
	public LInteractionSeq clone(AstFactory af)
	{
		List<LInteractionNode> lis = ScribUtil.cloneList(af, getInteractNodeChildren());
		return af.LInteractionSeq(this.source, lis);
	}

	@Override
	public LInteractionSeq reconstruct(List<? extends InteractionNode<Local>> actions)
	{
		ScribDel del = del();
		LInteractionSeq lis = new LInteractionSeq(this.source, castNodes(actions));
		lis = (LInteractionSeq) lis.del(del);
		return lis;
	}*/
}
