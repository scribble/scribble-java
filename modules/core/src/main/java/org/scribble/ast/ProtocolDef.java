package org.scribble.ast;

import org.scribble.ast.visit.ModelVisitor;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;


//public class ProtocolDefinition extends AbstractNode
//public abstract class ProtocolDefinition<T extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>
public abstract class ProtocolDef<K extends ProtocolKind>
		extends ScribNodeBase
{
	public final ProtocolBlock<K> block;
	//public final T block;

	//protected ProtocolDefinition(T block)
	protected ProtocolDef(ProtocolBlock<K> block)
	{
		this.block = block;
	}
	
	//protected abstract ProtocolDefinition<T> reconstruct(T block);
	protected abstract ProtocolDef<K> reconstruct(ProtocolBlock<K> block);
	
	@Override
	public ProtocolDef<K> visitChildren(ModelVisitor nv) throws ScribbleException
	//public ProtocolDefinition<T> visitChildren(ModelVisitor nv) throws ScribbleException
	{
		//T block = visitChildWithClassCheck(this, this.block, nv);
		ProtocolBlock<K> block = visitChildWithClassCheck(this, this.block, nv);
		//return new ProtocolDefinition<T>(this.ct, block);
		return reconstruct(block);
	}

	@Override
	public String toString()
	{
		return this.block.toString();
	}
}
