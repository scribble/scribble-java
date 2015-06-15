package org.scribble2.model.del.global;

import java.util.Map;
import java.util.Set;

import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.ModelNode;
import org.scribble2.model.del.ProtocolBlockDel;
import org.scribble2.model.global.GProtocolBlock;
import org.scribble2.model.local.LInteractionSeq;
import org.scribble2.model.local.LProtocolBlock;
import org.scribble2.model.model.ModelAction;
import org.scribble2.model.visit.ModelBuilder;
import org.scribble2.model.visit.Projector;
import org.scribble2.model.visit.env.ModelEnv;
import org.scribble2.model.visit.env.ProjectionEnv;
import org.scribble2.sesstype.name.Role;
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
		return (GProtocolBlock) popAndSetVisitorEnv(parent, child, proj, gpd);
	}
	
	@Override
	public void enterModelBuilding(ModelNode parent, ModelNode child, ModelBuilder builder) throws ScribbleException
	{
		pushVisitorEnv(parent, child, builder);
	}

	@Override
	public GProtocolBlock leaveModelBuilding(ModelNode parent, ModelNode child, ModelBuilder builder, ModelNode visited) throws ScribbleException
	{
		GProtocolBlock gpb = (GProtocolBlock) visited;
		Set<ModelAction> as = ((ModelEnv) gpb.seq.del().env()).getActions();
		Map<Role, ModelAction> leaves = ((ModelEnv) gpb.seq.del().env()).getLeaves();
		ModelEnv env = builder.popEnv();
		builder.pushEnv(env.setActions(as, leaves));
		return (GProtocolBlock) popAndSetVisitorEnv(parent, child, builder, gpb);
	}
}
