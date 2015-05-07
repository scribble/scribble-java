package org.scribble2.model.local;

import org.scribble2.model.ProtocolBlock;
import org.scribble2.model.ProtocolDef;
import org.scribble2.model.del.ModelDel;
import org.scribble2.sesstype.kind.Local;

//public class LocalProtocolDefinition extends ProtocolDefinition<LocalProtocolBlock> implements LocalNode
public class LProtocolDef extends ProtocolDef<Local> implements LocalNode
{
	//public LocalProtocolDefinition(LocalProtocolBlock block)
	public LProtocolDef(ProtocolBlock<Local> block)
	{
		super(block);
	}

	@Override
	//protected LocalProtocolDefinition reconstruct(LocalProtocolBlock block)
	protected LProtocolDef reconstruct(ProtocolBlock<Local> block)
	{
		ModelDel del = del();
		LProtocolDef lpd = new LProtocolDef(block);
		lpd = (LProtocolDef) lpd.del(del);
		return lpd;
	}

	@Override
	protected LProtocolDef copy()
	{
		return new LProtocolDef(this.block);
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
