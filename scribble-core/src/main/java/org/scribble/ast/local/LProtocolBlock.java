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

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.InteractionSeq;
import org.scribble.ast.ProtocolBlock;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.Message;
import org.scribble.sesstype.kind.Local;

public class LProtocolBlock extends ProtocolBlock<Local> implements LNode
{
	public LProtocolBlock(CommonTree source, LInteractionSeq seq)
	{
		super(source, seq);
	}

	@Override
	protected LProtocolBlock copy()
	{
		return new LProtocolBlock(this.source, getInteractionSeq());
	}
	
	@Override
	public LProtocolBlock clone(AstFactory af)
	{
		LInteractionSeq lis = getInteractionSeq().clone(af);
		return af.LProtocolBlock(this.source, lis);
	}

	@Override
	public LProtocolBlock reconstruct(InteractionSeq<Local> seq)
	{
		ScribDel del = del();
		LProtocolBlock lpb = new LProtocolBlock(this.source, (LInteractionSeq) seq);
		lpb = (LProtocolBlock) lpb.del(del);
		return lpb;
	}

	@Override
	public LInteractionSeq getInteractionSeq()
	{
		return (LInteractionSeq) this.seq;
	}
	
	// FIXME: shouldn't be needed, but here due to Eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=436350
	@Override
	public Local getKind()
	{
		return LNode.super.getKind();
	}
	
	public LProtocolBlock merge(LProtocolBlock lpb)
	{
		throw new RuntimeException("TODO: " + this + ", " + lpb);
	}
	
	public Set<Message> getEnabling()
	{
		return getInteractionSeq().getEnabling();
	}
}
