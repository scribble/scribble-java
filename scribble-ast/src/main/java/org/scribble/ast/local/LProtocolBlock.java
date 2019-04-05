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

import java.util.Set;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.ProtocolBlock;
import org.scribble.core.type.kind.Local;
import org.scribble.core.type.session.Message;

public class LProtocolBlock extends ProtocolBlock<Local> implements LScribNode
{
	// ScribTreeAdaptor#create constructor
	public LProtocolBlock(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	protected LProtocolBlock(LProtocolBlock node)
	{
		super(node);
	}

	@Override
	public LProtocolBlock dupNode()
	{
		return new LProtocolBlock(this);
	}
	
	@Override
	public LProtocolBlock clone()
	{
		return (LProtocolBlock) super.clone();
	}

	@Override
	public LInteractionSeq getInteractSeqChild()
	{
		return (LInteractionSeq) getChild(0);
	}
	
	public Set<Message> getEnabling()
	{
		return getInteractSeqChild().getEnabling();
	}
	
	public LProtocolBlock merge(LProtocolBlock lpb)
	{
		throw new RuntimeException("TODO: " + this + ", " + lpb);
	}
	
	
	
	
	
	
	
	
	

	public LProtocolBlock(CommonTree source, LInteractionSeq seq)
	{
		super(source, seq);
	}

	/*@Override
	protected LProtocolBlock copy()
	{
		return new LProtocolBlock(this.source, getInteractSeqChild());
	}
	
	@Override
	public LProtocolBlock clone(AstFactory af)
	{
		LInteractionSeq lis = getInteractSeqChild().clone(af);
		return af.LProtocolBlock(this.source, lis);
	}

	@Override
	public LProtocolBlock reconstruct(InteractionSeq<Local> seq)
	{
		ScribDel del = del();
		LProtocolBlock lpb = new LProtocolBlock(this.source, (LInteractionSeq) seq);
		lpb = (LProtocolBlock) lpb.del(del);
		return lpb;
	}*/
}
