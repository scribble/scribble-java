package org.scribble2.foo.ast;

import org.antlr.runtime.Token;

//public class ProtocolDefinition extends AbstractNode
public abstract class ProtocolDefinition<T extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>
		extends ScribbleASTBase
{
	//public final ProtocolBlock block;
	//public final T block;

	protected ProtocolDefinition(Token t)//, T block)
	{
		super(t);
		//this.block = block;
	}
	
	/*protected abstract ProtocolDefinition<T> reconstruct(CommonTree ct, T block);
	
	@Override
	//public ProtocolDefinition<T> visitChildren(NodeVisitor nv) throws ScribbleException
	public ProtocolDefinition<T> visitChildren(NodeVisitor nv) throws ScribbleException
	{
		T block = visitChildWithClassCheck(this, this.block, nv);
		//return new ProtocolDefinition<T>(this.ct, block);
		return reconstruct(this.ct, block);
	}*/

	/*@Override
	public String toString()
	{
		return this.block.toString();
	}*/
}
