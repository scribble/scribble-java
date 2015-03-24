package org.scribble2.model.global;

import org.scribble2.model.ProtocolDefinition;
import org.scribble2.model.del.ModelDelegate;

public class GlobalProtocolDefinition extends ProtocolDefinition<GlobalProtocolBlock> implements GlobalNode
{
	public GlobalProtocolDefinition(GlobalProtocolBlock block)
	{
		super(block);
	}

	@Override
	protected ProtocolDefinition<GlobalProtocolBlock> reconstruct(GlobalProtocolBlock block)
	{
		ModelDelegate del = del();
		GlobalProtocolDefinition gpd = new GlobalProtocolDefinition(block);
		gpd = (GlobalProtocolDefinition) gpd.del(del);
		return gpd;
	}

	@Override
	protected GlobalProtocolDefinition copy()
	{
		return new GlobalProtocolDefinition(this.block);
	}

	/*@Override
	protected GlobalProtocolDefinition reconstruct(CommonTree ct, GlobalProtocolBlock block)
	{
		return new GlobalProtocolDefinition(ct, block);
	}
	
	@Override
	public GlobalProtocolDefinition leaveProjection(Projector proj) //throws ScribbleException
	{
		LocalProtocolBlock block = (LocalProtocolBlock) ((ProjectionEnv) this.block.getEnv()).getProjection();	
		LocalProtocolDefinition projection = new LocalProtocolDefinition(null, block);
		this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleContext(), projection));
		return this;
	}

	/*@Override
	public GlobalProtocolDefinition visitChildren(NodeVisitor nv) throws ScribbleException
	{
		ProtocolDefinition<GlobalProtocolBlock> def = super.visitChildren(nv);
		return new GlobalProtocolDefinition(def.ct, def.block);
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
