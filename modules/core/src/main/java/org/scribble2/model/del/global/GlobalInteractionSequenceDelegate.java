package org.scribble2.model.del.global;

import java.util.LinkedList;
import java.util.List;

import org.scribble2.model.Continue;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.ModelNode;
import org.scribble2.model.del.InteractionSequenceDelegate;
import org.scribble2.model.global.GlobalInteractionNode;
import org.scribble2.model.global.GlobalInteractionSequence;
import org.scribble2.model.local.LocalInteractionNode;
import org.scribble2.model.local.LocalInteractionSequence;
import org.scribble2.model.local.LocalNode;
import org.scribble2.model.visit.Projector;
import org.scribble2.model.visit.env.ProjectionEnv;
import org.scribble2.util.ScribbleException;


// FIXME: should be a CompoundInteractionDelegate? -- no: compound interaction delegates for typing contexts (done for block only, not seqs)
public class GlobalInteractionSequenceDelegate extends InteractionSequenceDelegate
{
	@Override
	//public Projector enterProjection(ModelNode parent, ModelNode child, Projector proj) throws ScribbleException
	public void enterProjection(ModelNode parent, ModelNode child, Projector proj) throws ScribbleException
	{
		//return (Projector) pushEnv(parent, child, proj);  // Unlike WF-choice and Reachability, Projection uses an Env for InteractionSequences
		pushVisitorEnv(parent, child, proj);  // Unlike WF-choice and Reachability, Projection uses an Env for InteractionSequences
	}
	
	@Override
	public GlobalInteractionSequence leaveProjection(ModelNode parent, ModelNode child, Projector proj, ModelNode visited) throws ScribbleException
	{
		/*LocalInteractionSequence projection = new LocalInteractionSequence(Collections.emptyList());
		ProjectionEnv env = proj.popEnv();
		proj.pushEnv(new ProjectionEnv(env.getJobContext(), env.getModuleDelegate(), projection));
		return (GlobalInteractionSequence) super.leaveProjection(parent, child, proj, visited);*/
		
		GlobalInteractionSequence gis = (GlobalInteractionSequence) visited;
		List<LocalInteractionNode> lis = new LinkedList<>();
			//this.actions.stream().map((action) -> (LocalInteraction) ((ProjectionEnv) ((LocalNode) action).getEnv()).getProjection()).collect(Collectors.toList());	
		for (GlobalInteractionNode gi : gis.actions)
		{
			LocalNode ln = (LocalNode) ((ProjectionEnv) gi.del().getEnv()).getProjection();
			if (ln instanceof LocalInteractionSequence)
			{
				lis.addAll(((LocalInteractionSequence) ln).actions);
			}
			else if (ln != null)
			{
				lis.add((LocalInteractionNode) ln);
			}
		}
		if (lis.size() == 1)
		{
			if (lis.get(0) instanceof Continue)
			{
				lis.clear();
			}
		}
		LocalInteractionSequence projection = ModelFactoryImpl.FACTORY.LocalInteractionSequence(lis);
		ProjectionEnv env = proj.popEnv();
		//proj.pushEnv(new ProjectionEnv(env.getJobContext(), env.getModuleDelegate(), projection));
		proj.pushEnv(new ProjectionEnv(projection));
		//return gis;
		return (GlobalInteractionSequence) super.popAndSetVisitorEnv(parent, child, proj, gis);  // records the current checker Env to the current del; also pops and merges that env into the parent env
	}
}
