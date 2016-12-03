package org.scribble.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.visit.AstVisitor;

public abstract class ProtocolDef<K extends ProtocolKind> extends ScribNodeBase implements ProtocolKindNode<K>
{
	public final ProtocolBlock<K> block;

	protected ProtocolDef(CommonTree source, ProtocolBlock<K> block)
	{
		super(source);
		this.block = block;
	}
	
	public abstract ProtocolDef<K> reconstruct(ProtocolBlock<K> block);

	public abstract ProtocolBlock<K> getBlock();
	
	@Override
	public ProtocolDef<K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		ProtocolBlock<K> block = visitChildWithClassEqualityCheck(this, this.block, nv);
		return reconstruct(block);
	}

	@Override
	public String toString()
	{
		return this.block.toString();
	}
}
