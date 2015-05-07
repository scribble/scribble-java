package org.scribble2.model;

import org.scribble2.model.visit.ModelVisitor;
import org.scribble2.sesstype.kind.Kind;
import org.scribble2.util.ScribbleException;


//public class ProtocolDefinition extends AbstractNode
//public abstract class ProtocolDefinition<T extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>
public abstract class ProtocolDefinition<K extends Kind>
		extends ModelNodeBase
{
	public final ProtocolBlock<K> block;
	//public final T block;

	//protected ProtocolDefinition(T block)
	protected ProtocolDefinition(ProtocolBlock<K> block)
	{
		this.block = block;
	}
	
	//protected abstract ProtocolDefinition<T> reconstruct(T block);
	protected abstract ProtocolDefinition<K> reconstruct(ProtocolBlock<K> block);
	
	@Override
	public ProtocolDefinition<K> visitChildren(ModelVisitor nv) throws ScribbleException
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
