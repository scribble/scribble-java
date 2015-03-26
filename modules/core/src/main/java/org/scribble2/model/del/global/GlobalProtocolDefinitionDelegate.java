package org.scribble2.model.del.global;

import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.ModelNode;
import org.scribble2.model.del.ProtocolDeclDelegate;
import org.scribble2.model.global.GlobalProtocolDefinition;
import org.scribble2.model.local.LocalProtocolBlock;
import org.scribble2.model.local.LocalProtocolDefinition;
import org.scribble2.model.visit.Projector;
import org.scribble2.model.visit.env.ProjectionEnv;
import org.scribble2.util.ScribbleException;

public class GlobalProtocolDefinitionDelegate extends ProtocolDeclDelegate
{
	public GlobalProtocolDefinitionDelegate()
	{
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
		return (GlobalProtocolDefinition) super.leaveProjection(parent, child, proj, gpd);  // records the current checker Env to the current del; also pops and merges that env into the parent env
	}
}
