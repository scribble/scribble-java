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

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.ProtocolBlock;
import org.scribble.core.type.kind.Global;

public class GProtocolBlock extends ProtocolBlock<Global> implements GScribNode
{
	// ScribTreeAdaptor#create constructor
	public GProtocolBlock(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	protected GProtocolBlock(GProtocolBlock node)
	{
		super(node);
	}

	@Override
	public GProtocolBlock dupNode()
	{
		return new GProtocolBlock(this);
	}

	@Override
	public GInteractionSeq getInteractSeqChild()
	{
		return (GInteractionSeq) getChild(0);
	}
	
	
	
	
	
	
	
	
	
	
	
	

	public GProtocolBlock(CommonTree source, GInteractionSeq seq)
	{
		super(source, seq);
	}

	/*@Override
	protected GProtocolBlock copy()
	{
		return new GProtocolBlock(this.source, getInteractSeqChild());
	}
	
	@Override
	public GProtocolBlock clone(AstFactory af)
	{
		GInteractionSeq gis = getInteractSeqChild().clone(af);
		return af.GProtocolBlock(this.source, gis);
	}*/

	/*@Override
	public GProtocolBlock reconstruct(InteractionSeq<Global> seq)
	{
		ScribDel del = del();
		GProtocolBlock gpb = new GProtocolBlock(this.source, (GInteractionSeq) seq);
		gpb = (GProtocolBlock) gpb.del(del);
		return gpb;
	}*/
}
