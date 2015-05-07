package org.scribble2.model.del.global;

import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.ModelNode;
import org.scribble2.model.del.ProtocolDefDel;
import org.scribble2.model.global.GProtocolDef;
import org.scribble2.model.local.LProtocolBlock;
import org.scribble2.model.local.LProtocolDef;
import org.scribble2.model.visit.Projector;
import org.scribble2.model.visit.env.ProjectionEnv;
import org.scribble2.util.ScribbleException;

public class GProtocolDefDel extends ProtocolDefDel
{
	public GProtocolDefDel()
	{

	}

	@Override
	//public Projector enterProjection(ModelNode parent, ModelNode child, Projector proj) throws ScribbleException
	public void enterProjection(ModelNode parent, ModelNode child, Projector proj) throws ScribbleException
	{
		//return (Projector) pushEnv(parent, child, proj);
		pushVisitorEnv(parent, child, proj);
	}

	@Override
	public GProtocolDef leaveProjection(ModelNode parent, ModelNode child, Projector proj, ModelNode visited) throws ScribbleException
	{
		GProtocolDef gpd = (GProtocolDef) visited;
		LProtocolBlock block = (LProtocolBlock) ((ProjectionEnv) gpd.block.del().env()).getProjection();	
		//LocalProtocolDefinition projection = new LocalProtocolDefinition(block);
		LProtocolDef projection = ModelFactoryImpl.FACTORY.LocalProtocolDefinition(block);
		//this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleDelegate(), projection));
		ProjectionEnv env = proj.popEnv();
		//proj.pushEnv(new ProjectionEnv(env.getJobContext(), env.getModuleDelegate(), projection));
		proj.pushEnv(new ProjectionEnv(projection));
		return (GProtocolDef) popAndSetVisitorEnv(parent, child, proj, gpd);
	}
}
