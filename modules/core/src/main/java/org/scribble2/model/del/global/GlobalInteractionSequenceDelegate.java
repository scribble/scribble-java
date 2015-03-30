package org.scribble2.model.del.global;

import java.util.LinkedList;
import java.util.List;

import org.scribble2.model.ModelNode;
import org.scribble2.model.del.ModelDelegateBase;
import org.scribble2.model.global.GlobalInteraction;
import org.scribble2.model.global.GlobalInteractionSequence;
import org.scribble2.model.local.LocalInteraction;
import org.scribble2.model.local.LocalInteractionSequence;
import org.scribble2.model.local.LocalNode;
import org.scribble2.model.visit.Projector;
import org.scribble2.model.visit.env.ProjectionEnv;
import org.scribble2.util.ScribbleException;


// FIXME: should be a CompoundInteractionDelegate? -- no: compound interaction delegates for typing contexts (done for block only, not seqs)
// FIXME: make InteractionSequenceDelegate (and LocalInteractionSequenceDelegate?)
public class GlobalInteractionSequenceDelegate extends ModelDelegateBase
{
	@Override
	public GlobalInteractionSequence leaveProjection(ModelNode parent, ModelNode child, Projector proj, ModelNode visited) throws ScribbleException
	{
		/*LocalInteractionSequence projection = new LocalInteractionSequence(Collections.emptyList());
		ProjectionEnv env = proj.popEnv();
		proj.pushEnv(new ProjectionEnv(env.getJobContext(), env.getModuleDelegate(), projection));
		return (GlobalInteractionSequence) super.leaveProjection(parent, child, proj, visited);*/
		
		GlobalInteractionSequence gis = (GlobalInteractionSequence) visited;
		List<LocalInteraction> lis = new LinkedList<>();
			//this.actions.stream().map((action) -> (LocalInteraction) ((ProjectionEnv) ((LocalNode) action).getEnv()).getProjection()).collect(Collectors.toList());	
		for (GlobalInteraction gi : gis.actions)
		{
			LocalNode ln = (LocalNode) ((ProjectionEnv) gi.del().getEnv()).getProjection();
			if (ln instanceof LocalInteractionSequence)
			{
				lis.addAll(((LocalInteractionSequence) ln).actions);
			}
			else if (ln != null)
			{
				lis.add((LocalInteraction) ln);
			}
		}
		LocalInteractionSequence projection = new LocalInteractionSequence(lis);
		ProjectionEnv env = proj.popEnv();
		proj.pushEnv(new ProjectionEnv(env.getJobContext(), env.getModuleDelegate(), projection));
		//return gis;
		return (GlobalInteractionSequence) super.leaveProjection(parent, child, proj, gis);  // records the current checker Env to the current del; also pops and merges that env into the parent env
	}
}
