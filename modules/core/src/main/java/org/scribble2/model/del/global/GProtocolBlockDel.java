package org.scribble2.model.del.global;

import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.ModelNode;
import org.scribble2.model.del.ProtocolBlockDel;
import org.scribble2.model.global.GProtocolBlock;
import org.scribble2.model.local.LInteractionSeq;
import org.scribble2.model.local.LProtocolBlock;
import org.scribble2.model.visit.Projector;
import org.scribble2.model.visit.env.ProjectionEnv;
import org.scribble2.util.ScribbleException;


public class GProtocolBlockDel extends ProtocolBlockDel
{
	@Override
	//public Projector enterProjection(ModelNode parent, ModelNode child, Projector proj) throws ScribbleException
	public void enterProjection(ModelNode parent, ModelNode child, Projector proj) throws ScribbleException
	{
		//return (Projector) pushEnv(parent, child, proj);
		pushVisitorEnv(parent, child, proj);
	}

	@Override
	public GProtocolBlock leaveProjection(ModelNode parent, ModelNode child, Projector proj, ModelNode visited) throws ScribbleException
	{
		GProtocolBlock gpd = (GProtocolBlock) visited;
		LInteractionSeq seq = (LInteractionSeq) ((ProjectionEnv) gpd.seq.del().env()).getProjection();	
		//LocalProtocolBlock projection = ModelFactoryImpl.FACTORY.LocalProtocolBlock(ModelFactoryImpl.FACTORY.LocalInteractionSequence(Collections.emptyList()));
		LProtocolBlock projection = ModelFactoryImpl.FACTORY.LProtocolBlock(seq);
		//this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleDelegate(), projection));
		ProjectionEnv env = proj.popEnv();
		//proj.pushEnv(new ProjectionEnv(env.getJobContext(), env.getModuleDelegate(), projection));
		proj.pushEnv(new ProjectionEnv(projection));
		return (GProtocolBlock) super.popAndSetVisitorEnv(parent, child, proj, gpd);
	}
}
