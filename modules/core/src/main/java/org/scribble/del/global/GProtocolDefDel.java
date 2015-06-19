package org.scribble.del.global;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GProtocolDef;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.ast.local.LProtocolDef;
import org.scribble.del.ProtocolDefDel;
import org.scribble.main.ScribbleException;
import org.scribble.visit.Projector;
import org.scribble.visit.env.ProjectionEnv;

public class GProtocolDefDel extends ProtocolDefDel
{
	public GProtocolDefDel()
	{

	}

	@Override
	//public Projector enterProjection(ModelNode parent, ModelNode child, Projector proj) throws ScribbleException
	public void enterProjection(ScribNode parent, ScribNode child, Projector proj) throws ScribbleException
	{
		//return (Projector) pushEnv(parent, child, proj);
		pushVisitorEnv(parent, child, proj);
	}

	@Override
	public GProtocolDef leaveProjection(ScribNode parent, ScribNode child, Projector proj, ScribNode visited) throws ScribbleException
	{
		GProtocolDef gpd = (GProtocolDef) visited;
		LProtocolBlock block = (LProtocolBlock) ((ProjectionEnv) gpd.block.del().env()).getProjection();	
		//LocalProtocolDefinition projection = new LocalProtocolDefinition(block);
		LProtocolDef projection = AstFactoryImpl.FACTORY.LProtocolDefinition(block);
		//this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleDelegate(), projection));
		ProjectionEnv env = proj.popEnv();
		//proj.pushEnv(new ProjectionEnv(env.getJobContext(), env.getModuleDelegate(), projection));
		proj.pushEnv(env.setProjection(projection));
		return (GProtocolDef) popAndSetVisitorEnv(parent, child, proj, gpd);
	}
}
