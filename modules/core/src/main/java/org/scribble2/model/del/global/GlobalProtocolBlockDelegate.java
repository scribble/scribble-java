package org.scribble2.model.del.global;

import org.scribble2.model.ModelNode;
import org.scribble2.model.del.ProtocolBlockDelegate;
import org.scribble2.model.global.GlobalProtocolBlock;
import org.scribble2.model.local.LocalInteractionSequence;
import org.scribble2.model.local.LocalProtocolBlock;
import org.scribble2.model.visit.Projector;
import org.scribble2.model.visit.env.ProjectionEnv;


public class GlobalProtocolBlockDelegate extends ProtocolBlockDelegate
{
	@Override
	public GlobalProtocolBlock leaveProjection(ModelNode parent, ModelNode child, Projector proj, ModelNode visited) //throws ScribbleException
	{
		GlobalProtocolBlock gpd = (GlobalProtocolBlock) visited;
		LocalInteractionSequence seq = (LocalInteractionSequence) ((ProjectionEnv) gpd.seq.del().getEnv()).getProjection();	
		LocalProtocolBlock projection = new LocalProtocolBlock(seq);
		//this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleDelegate(), projection));
		//proj.popEnv(). ...
		proj.pushEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleDelegate(), projection));
		return (GlobalProtocolBlock) super.leaveProjection(parent, child, proj, gpd);  // records the current checker Env to the current del; also pops and merges that env into the parent env
	}

	/*@Override
	public EnvVisitor envEnter(ModelNode parent, ModelNode child, EnvVisitor ev) throws ScribbleException
	{
		WellFormedChoiceEnv env = checker.peekEnv().push();
		env = env.clear();
		env = env.enableChoiceSubject(((GlobalChoice) child).subj.toName());
		checker.pushEnv(env);
		return checker;
		
		//.. push env for blocks in EnvVitistor.subprotocolEnter; or override every visitor pass in block delegate -- better in ProtocolBlockDelegate?
	}*/
	
	/*@Override
	public GlobalProtocolBlock leaveWFChoiceCheck(ModelNode parent, ModelNode child, WellFormedChoiceChecker checker, ModelNode visited) throws ScribbleException
	{
		return (GlobalProtocolBlock) super.leaveWFChoiceCheck(parent, child, checker, visited);
	}*/
}
