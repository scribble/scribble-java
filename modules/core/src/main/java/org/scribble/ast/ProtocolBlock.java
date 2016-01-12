package org.scribble.ast;

import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.visit.AstVisitor;

public abstract class ProtocolBlock<K extends ProtocolKind> extends CompoundInteraction implements ProtocolKindNode<K>
{
	public final InteractionSeq<K> seq;

	public ProtocolBlock(InteractionSeq<K> seq)
	{
		this.seq = seq;
	}
	
	@Override
	public abstract ProtocolBlock<K> clone();

	public abstract ProtocolBlock<K> reconstruct(InteractionSeq<K> seq);

	public abstract InteractionSeq<K> getInteractionSeq();

	@Override
	public ProtocolBlock<K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		InteractionSeq<K> seq = visitChildWithClassEqualityCheck(this, this.seq, nv);
		return reconstruct(seq);
	}
	
	public boolean isEmpty()
	{
		return this.seq.isEmpty();
	}

	@Override
	public String toString()
	{
		return "{\n" + this.seq + "\n}";  // Empty block will contain an blank line
	}
}
