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

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.InteractionNode;
import org.scribble.ast.InteractionSeq;
import org.scribble.ast.ScribNodeBase;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.Message;
import org.scribble.sesstype.kind.Local;
import org.scribble.util.ScribUtil;

public class LInteractionSeq extends InteractionSeq<Local> implements LNode
{
	public LInteractionSeq(CommonTree source, List<LInteractionNode> lis)
	{
		super(source, lis);
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new LInteractionSeq(this.source, getInteractions());
	}
	
	@Override
	public LInteractionSeq clone(AstFactory af)
	{
		List<LInteractionNode> lis = ScribUtil.cloneList(af, getInteractions());
		return af.LInteractionSeq(this.source, lis);
	}

	@Override
	public LInteractionSeq reconstruct(List<? extends InteractionNode<Local>> actions)
	{
		ScribDel del = del();
		LInteractionSeq lis = new LInteractionSeq(this.source, castNodes(actions));
		lis = (LInteractionSeq) lis.del(del);
		return lis;
	}
	
	@Override
	public List<LInteractionNode> getInteractions()
	{
		return castNodes(super.getInteractions());
	}

	@Override	
	public boolean isLocal()
	{
		return true;
	}
	
	private static List<LInteractionNode> castNodes(List<? extends InteractionNode<Local>> nodes)
	{
		return nodes.stream().map((n) -> (LInteractionNode) n).collect(Collectors.toList());
	}

	public Set<Message> getEnabling()
	{
		if (!this.isEmpty())
		{
			for (LInteractionNode ln : getInteractions())
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
	
	/*// FIXME: shouldn't be needed, but here due to Eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=436350
	@Override
	public Local getKind()
	{
		return LNode.super.getKind();
	}*/
}
