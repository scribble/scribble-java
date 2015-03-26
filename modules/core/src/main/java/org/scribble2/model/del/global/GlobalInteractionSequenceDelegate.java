package org.scribble2.model.del.global;

import java.util.Collections;

import org.scribble2.model.ModelNode;
import org.scribble2.model.del.ModelDelegateBase;
import org.scribble2.model.global.GlobalInteractionSequence;
import org.scribble2.model.local.LocalInteractionSequence;
import org.scribble2.model.visit.Projector;
import org.scribble2.model.visit.env.ProjectionEnv;
import org.scribble2.util.ScribbleException;


// FIXME: should be a CompoundInteractionDelegate?
// FIXME: make InteractionSequenceDelegate (and LocalInteractionSequenceDelegate?)
public class GlobalInteractionSequenceDelegate extends ModelDelegateBase
{
	@Override
	public GlobalInteractionSequence leaveProjection(ModelNode parent, ModelNode child, Projector proj, ModelNode visited) throws ScribbleException
	{
		/*GlobalProtocolBlock gpd = (GlobalProtocolBlock) visited;
		LocalInteractionSequence seq = (LocalInteractionSequence) ((ProjectionEnv) gpd.seq.del().getEnv()).getProjection();*/
		LocalInteractionSequence projection = new LocalInteractionSequence(Collections.emptyList());
		ProjectionEnv env = proj.popEnv();
		proj.pushEnv(new ProjectionEnv(env.getJobContext(), env.getModuleDelegate(), projection));
		return (GlobalInteractionSequence) super.leaveProjection(parent, child, proj, visited);
	}
}
