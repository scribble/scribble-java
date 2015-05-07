package org.scribble2.model.local;

import org.scribble2.model.ProtocolBlock;
import org.scribble2.model.ProtocolDefinition;
import org.scribble2.model.del.ModelDelegate;
import org.scribble2.sesstype.kind.Local;

//public class LocalProtocolDefinition extends ProtocolDefinition<LocalProtocolBlock> implements LocalNode
public class LocalProtocolDefinition extends ProtocolDefinition<Local> implements LocalNode
{
	//public LocalProtocolDefinition(LocalProtocolBlock block)
	public LocalProtocolDefinition(ProtocolBlock<Local> block)
	{
		super(block);
	}

	@Override
	//protected LocalProtocolDefinition reconstruct(LocalProtocolBlock block)
	protected LocalProtocolDefinition reconstruct(ProtocolBlock<Local> block)
	{
		ModelDelegate del = del();
		LocalProtocolDefinition lpd = new LocalProtocolDefinition(block);
		lpd = (LocalProtocolDefinition) lpd.del(del);
		return lpd;
	}

	@Override
	protected LocalProtocolDefinition copy()
	{
		return new LocalProtocolDefinition(this.block);
	}
	
	/*@Override
	public LocalProtocolDefinition visitChildren(NodeVisitor nv) throws ScribbleException
	{
		ProtocolDefinition<LocalProtocolBlock> def = super.visitChildren(nv);
		return new LocalProtocolDefinition(def.ct, def.block);
	}*/
	
	/*@Override
	public LocalProtocolDefinition project(Projector proj) throws ScribbleException
	{
		LocalProtocolBlock block = (LocalProtocolBlock) proj.visit(this.block); 
		if (block == null)
		{
			LocalInteractionSequence seq = new LocalInteractionSequence(null, Collections.<LocalInteraction>emptyList());
			block = new LocalProtocolBlock(null, seq);
		}
		return new LocalProtocolDefinition(null, block);
	}*/
}
