package org.scribble2.model;

import org.scribble2.model.visit.ModelVisitor;
import org.scribble2.util.ScribbleException;


//public class ProtocolDefinition extends AbstractNode
public abstract class ProtocolDefinition<T extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>
		extends ModelNodeBase
{
	//public final ProtocolBlock block;
	public final T block;

	protected ProtocolDefinition(T block)
	{
		this.block = block;
	}
	
	protected abstract ProtocolDefinition<T> reconstruct(T block);
	
	@Override
	//public ProtocolDefinition<T> visitChildren(NodeVisitor nv) throws ScribbleException
	public ProtocolDefinition<T> visitChildren(ModelVisitor nv) throws ScribbleException
	{
		T block = visitChildWithClassCheck(this, this.block, nv);
		//return new ProtocolDefinition<T>(this.ct, block);
		return reconstruct(block);
	}

	@Override
	public String toString()
	{
		return this.block.toString();
	}
}
