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

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.InteractionSeq;
import org.scribble.core.type.kind.Global;

public class GInteractionSeq extends InteractionSeq<Global> implements GScribNode
{
	// ScribTreeAdaptor#create constructor
	public GInteractionSeq(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public GInteractionSeq(GInteractionSeq node)
	{
		super(node);
	}
	
	@Override
	public GInteractionSeq dupNode()
	{
		return new GInteractionSeq(this);
	}
	
	@Override
	public List<GSessionNode> getInteractionChildren()
	{
		return getChildren().stream().map(n -> (GSessionNode) n)
				.collect(Collectors.toList());
	}
	
	
	
	
	
	
	
	
	
	
	

	public GInteractionSeq(CommonTree source, List<GSessionNode> actions)
	{
		super(source, actions);
	}

	/*@Override
	protected GInteractionSeq copy()
	{
		return new GInteractionSeq(this.source, getInteractNodeChildren());
	}
	
	@Override
	public GInteractionSeq clone(AstFactory af)
	{
		List<GInteractionNode> gis = ScribUtil.cloneList(af, getInteractNodeChildren());
		return af.GInteractionSeq(this.source, gis);
	}

	@Override
	public GInteractionSeq reconstruct(List<? extends InteractionNode<Global>> ins)
	{
		ScribDel del = del();
		GInteractionSeq gis = new GInteractionSeq(this.source, castNodes(ins));
		gis = (GInteractionSeq) gis.del(del);
		return gis;
	}*/
}
