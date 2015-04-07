package org.scribble2.model.del.global;

import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.ModelNode;
import org.scribble2.model.del.ProtocolBlockDelegate;
import org.scribble2.model.global.GlobalProtocolBlock;
import org.scribble2.model.local.LocalInteractionSequence;
import org.scribble2.model.local.LocalProtocolBlock;
import org.scribble2.model.visit.Projector;
import org.scribble2.model.visit.env.ProjectionEnv;
import org.scribble2.util.ScribbleException;


public class GlobalProtocolBlockDelegate extends ProtocolBlockDelegate
{
	@Override
	public Projector enterProjection(ModelNode parent, ModelNode child, Projector proj) throws ScribbleException
	{
		return (Projector) pushEnv(parent, child, proj);
	}

	@Override
	public GlobalProtocolBlock leaveProjection(ModelNode parent, ModelNode child, Projector proj, ModelNode visited) throws ScribbleException
	{
		GlobalProtocolBlock gpd = (GlobalProtocolBlock) visited;
		LocalInteractionSequence seq = (LocalInteractionSequence) ((ProjectionEnv) gpd.seq.del().getEnv()).getProjection();	
		//LocalProtocolBlock projection = ModelFactoryImpl.FACTORY.LocalProtocolBlock(ModelFactoryImpl.FACTORY.LocalInteractionSequence(Collections.emptyList()));
		LocalProtocolBlock projection = ModelFactoryImpl.FACTORY.LocalProtocolBlock(seq);
		//this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleDelegate(), projection));
		ProjectionEnv env = proj.popEnv();
		proj.pushEnv(new ProjectionEnv(env.getJobContext(), env.getModuleDelegate(), projection));
		return (GlobalProtocolBlock) super.popAndSetEnv(parent, child, proj, gpd);
	}
}
