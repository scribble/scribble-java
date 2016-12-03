package org.scribble.ast.local;

import java.util.Set;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
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
	public LProtocolBlock clone()
	{
		LInteractionSeq lis = getInteractionSeq().clone();
		return AstFactoryImpl.FACTORY.LProtocolBlock(this.source, lis);
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
