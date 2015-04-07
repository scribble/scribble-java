package org.scribble2.model.del.global;

import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.ModelNode;
import org.scribble2.model.del.ProtocolDefinitionDelegate;
import org.scribble2.model.global.GlobalProtocolDefinition;
import org.scribble2.model.local.LocalProtocolBlock;
import org.scribble2.model.local.LocalProtocolDefinition;
import org.scribble2.model.visit.Projector;
import org.scribble2.model.visit.env.ProjectionEnv;
import org.scribble2.util.ScribbleException;

public class GlobalProtocolDefinitionDelegate extends ProtocolDefinitionDelegate
{
	public GlobalProtocolDefinitionDelegate()
	{

	}

	@Override
	public Projector enterProjection(ModelNode parent, ModelNode child, Projector proj) throws ScribbleException
	{
		return (Projector) pushEnv(parent, child, proj);
	}

	@Override
	public GlobalProtocolDefinition leaveProjection(ModelNode parent, ModelNode child, Projector proj, ModelNode visited) throws ScribbleException
	{
		GlobalProtocolDefinition gpd = (GlobalProtocolDefinition) visited;
		LocalProtocolBlock block = (LocalProtocolBlock) ((ProjectionEnv) gpd.block.del().getEnv()).getProjection();	
		//LocalProtocolDefinition projection = new LocalProtocolDefinition(block);
		LocalProtocolDefinition projection = ModelFactoryImpl.FACTORY.LocalProtocolDefinition(block);
		//this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleDelegate(), projection));
		ProjectionEnv env = proj.popEnv();
		proj.pushEnv(new ProjectionEnv(env.getJobContext(), env.getModuleDelegate(), projection));
		return (GlobalProtocolDefinition) popAndSetEnv(parent, child, proj, gpd);
	}
}
