package org.scribble2.model.del.global;

import java.util.LinkedList;
import java.util.List;

import org.scribble2.model.Continue;
import org.scribble2.model.InteractionNode;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.ModelNode;
import org.scribble2.model.del.InteractionSeqDel;
import org.scribble2.model.global.GInteractionSeq;
import org.scribble2.model.local.LInteractionNode;
import org.scribble2.model.local.LInteractionSeq;
import org.scribble2.model.local.LocalNode;
import org.scribble2.model.visit.Projector;
import org.scribble2.model.visit.env.ProjectionEnv;
import org.scribble2.sesstype.kind.Global;
import org.scribble2.sesstype.kind.Local;
import org.scribble2.util.ScribbleException;


// FIXME: should be a CompoundInteractionDelegate? -- no: compound interaction delegates for typing contexts (done for block only, not seqs)
public class GInteractionSeqDel extends InteractionSeqDel
{
	@Override
	//public Projector enterProjection(ModelNode parent, ModelNode child, Projector proj) throws ScribbleException
	public void enterProjection(ModelNode parent, ModelNode child, Projector proj) throws ScribbleException
	{
		//return (Projector) pushEnv(parent, child, proj);  // Unlike WF-choice and Reachability, Projection uses an Env for InteractionSequences
		pushVisitorEnv(parent, child, proj);  // Unlike WF-choice and Reachability, Projection uses an Env for InteractionSequences
	}
	
	@Override
	public GInteractionSeq leaveProjection(ModelNode parent, ModelNode child, Projector proj, ModelNode visited) throws ScribbleException
	{
		/*LocalInteractionSequence projection = new LocalInteractionSequence(Collections.emptyList());
		ProjectionEnv env = proj.popEnv();
		proj.pushEnv(new ProjectionEnv(env.getJobContext(), env.getModuleDelegate(), projection));
		return (GlobalInteractionSequence) super.leaveProjection(parent, child, proj, visited);*/
		
		GInteractionSeq gis = (GInteractionSeq) visited;
		//List<LocalInteractionNode> lis = new LinkedList<>();
		List<InteractionNode<Local>> lis = new LinkedList<>();
			//this.actions.stream().map((action) -> (LocalInteraction) ((ProjectionEnv) ((LocalNode) action).getEnv()).getProjection()).collect(Collectors.toList());	
		//for (GlobalInteractionNode gi : gis.actions)
		for (InteractionNode<Global> gi : gis.actions)
		{
			LocalNode ln = (LocalNode) ((ProjectionEnv) gi.del().env()).getProjection();
			if (ln instanceof LInteractionSeq)
			{
				lis.addAll(((LInteractionSeq) ln).actions);
			}
			else if (ln != null)
			{
				lis.add((LInteractionNode) ln);
			}
		}
		if (lis.size() == 1)
		{
			if (lis.get(0) instanceof Continue)
			{
				lis.clear();
			}
		}
		LInteractionSeq projection = ModelFactoryImpl.FACTORY.LocalInteractionSequence(lis);
		ProjectionEnv env = proj.popEnv();
		//proj.pushEnv(new ProjectionEnv(env.getJobContext(), env.getModuleDelegate(), projection));
		proj.pushEnv(new ProjectionEnv(projection));
		//return gis;
		return (GInteractionSeq) super.popAndSetVisitorEnv(parent, child, proj, gis);  // records the current checker Env to the current del; also pops and merges that env into the parent env
	}
}