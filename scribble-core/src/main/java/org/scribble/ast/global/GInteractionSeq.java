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
package org.scribble.ast.global;

import java.util.List;
import java.util.stream.Collectors;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.InteractionNode;
import org.scribble.ast.InteractionSeq;
import org.scribble.ast.local.LInteractionNode;
import org.scribble.ast.local.LInteractionSeq;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.name.Role;
import org.scribble.util.ScribUtil;

public class GInteractionSeq extends InteractionSeq<Global> implements GNode
{
	public GInteractionSeq(CommonTree source, List<GInteractionNode> actions)
	{
		super(source, actions);
	}

	// Move node-specific projects to G nodes (not dels) and take child projections as params, bit like reconstruct
	public LInteractionSeq project(Role self, List<LInteractionNode> lis)
	{
		LInteractionSeq projection = AstFactoryImpl.FACTORY.LInteractionSeq(this.source, lis);
		return projection;
	}

	@Override
	protected GInteractionSeq copy()
	{
		return new GInteractionSeq(this.source, getInteractions());
	}
	
	@Override
	public GInteractionSeq clone()
	{
		List<GInteractionNode> gis = ScribUtil.cloneList(getInteractions());
		return AstFactoryImpl.FACTORY.GInteractionSeq(this.source, gis);
	}

	@Override
	public GInteractionSeq reconstruct(List<? extends InteractionNode<Global>> ins)
	{
		ScribDel del = del();
		GInteractionSeq gis = new GInteractionSeq(this.source, castNodes(ins));
		gis = (GInteractionSeq) gis.del(del);
		return gis;
	}
	
	@Override
	public List<GInteractionNode> getInteractions()
	{
		return castNodes(super.getInteractions());
	}
	
	private static List<GInteractionNode> castNodes(List<? extends InteractionNode<Global>> nodes)
	{
		return nodes.stream().map((n) -> (GInteractionNode) n).collect(Collectors.toList());
	}
	
	// FIXME: shouldn't be needed, but here due to Eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=436350
	@Override
	public Global getKind()
	{
		return GNode.super.getKind();
	}
}
