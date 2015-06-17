package org.scribble.ast.global;

import org.scribble.ast.ProtocolBlock;
import org.scribble.ast.ProtocolDef;
import org.scribble.ast.del.ModelDel;
import org.scribble.sesstype.kind.Global;

//public class GlobalProtocolDefinition extends ProtocolDefinition<GlobalProtocolBlock> implements GlobalNode
public class GProtocolDef extends ProtocolDef<Global> implements GlobalNode
{
	//public GlobalProtocolDefinition(GlobalProtocolBlock block)
	public GProtocolDef(ProtocolBlock<Global> block)
	{
		super(block);
	}

	@Override
	//protected ProtocolDefinition<GlobalProtocolBlock> reconstruct(GlobalProtocolBlock block)
	protected GProtocolDef reconstruct(ProtocolBlock<Global> block)
	{
		ModelDel del = del();
		GProtocolDef gpd = new GProtocolDef(block);
		gpd = (GProtocolDef) gpd.del(del);
		return gpd;
	}

	@Override
	protected GProtocolDef copy()
	{
		return new GProtocolDef(this.block);
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
