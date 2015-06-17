package org.scribble.del.global;

import java.util.Map;
import java.util.Set;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.ast.local.LInteractionSeq;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.ast.visit.ModelBuilder;
import org.scribble.ast.visit.Projector;
import org.scribble.ast.visit.env.ModelEnv;
import org.scribble.ast.visit.env.ProjectionEnv;
import org.scribble.del.ProtocolBlockDel;
import org.scribble.main.ScribbleException;
import org.scribble.model.global.ModelAction;
import org.scribble.sesstype.name.Role;


public class GProtocolBlockDel extends ProtocolBlockDel
{
	@Override
	//public Projector enterProjection(ModelNode parent, ModelNode child, Projector proj) throws ScribbleException
	public void enterProjection(ScribNode parent, ScribNode child, Projector proj) throws ScribbleException
	{
		//return (Projector) pushEnv(parent, child, proj);
		pushVisitorEnv(parent, child, proj);
	}

	@Override
	public GProtocolBlock leaveProjection(ScribNode parent, ScribNode child, Projector proj, ScribNode visited) throws ScribbleException
	{
		GProtocolBlock gpd = (GProtocolBlock) visited;
		LInteractionSeq seq = (LInteractionSeq) ((ProjectionEnv) gpd.seq.del().env()).getProjection();	
		//LocalProtocolBlock projection = ModelFactoryImpl.FACTORY.LocalProtocolBlock(ModelFactoryImpl.FACTORY.LocalInteractionSequence(Collections.emptyList()));
		LProtocolBlock projection = AstFactoryImpl.FACTORY.LProtocolBlock(seq);
		//this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleDelegate(), projection));
		ProjectionEnv env = proj.popEnv();
		//proj.pushEnv(new ProjectionEnv(env.getJobContext(), env.getModuleDelegate(), projection));
		proj.pushEnv(new ProjectionEnv(projection));
		return (GProtocolBlock) popAndSetVisitorEnv(parent, child, proj, gpd);
	}
	
	@Override
	public void enterModelBuilding(ScribNode parent, ScribNode child, ModelBuilder builder) throws ScribbleException
	{
		pushVisitorEnv(parent, child, builder);
	}

	@Override
	public GProtocolBlock leaveModelBuilding(ScribNode parent, ScribNode child, ModelBuilder builder, ScribNode visited) throws ScribbleException
	{
		GProtocolBlock gpb = (GProtocolBlock) visited;
		Set<ModelAction> as = ((ModelEnv) gpb.seq.del().env()).getActions();
		Map<Role, ModelAction> leaves = ((ModelEnv) gpb.seq.del().env()).getLeaves();
		ModelEnv env = builder.popEnv();
		builder.pushEnv(env.setActions(as, leaves));
		return (GProtocolBlock) popAndSetVisitorEnv(parent, child, builder, gpb);
	}
}
