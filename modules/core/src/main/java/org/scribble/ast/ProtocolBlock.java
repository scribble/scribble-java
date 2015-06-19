package org.scribble.ast;

import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.visit.AstVisitor;


public abstract class ProtocolBlock<K extends ProtocolKind> extends CompoundInteraction
{
	public final InteractionSeq<K> seq;

	public ProtocolBlock(InteractionSeq<K> seq)
	{
		this.seq = seq;
	}
	
	protected abstract ProtocolBlock<K> reconstruct(InteractionSeq<K> seq);

	@Override
	public ProtocolBlock<K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		InteractionSeq<K> seq = visitChildWithClassCheck(this, this.seq, nv);
		return reconstruct(seq);
	}
	
	public boolean isEmpty()
	{
		return this.seq.isEmpty();
	}

	@Override
	public String toString()
	{
		return "{\n" + this.seq + "\n}";
	}
}
