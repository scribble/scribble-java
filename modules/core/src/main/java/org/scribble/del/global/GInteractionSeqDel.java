package org.scribble.del.global;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ast.Continue;
import org.scribble.ast.InteractionNode;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GInteractionSeq;
import org.scribble.ast.local.LInteractionNode;
import org.scribble.ast.local.LInteractionSeq;
import org.scribble.ast.local.LNode;
import org.scribble.ast.visit.ModelBuilder;
import org.scribble.ast.visit.Projector;
import org.scribble.ast.visit.env.ModelEnv;
import org.scribble.ast.visit.env.ProjectionEnv;
import org.scribble.del.InteractionSeqDel;
import org.scribble.main.ScribbleException;
import org.scribble.model.global.ModelAction;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.name.Role;


// FIXME: should be a CompoundInteractionDelegate? -- no: compound interaction delegates for typing contexts (done for block only, not seqs)
public class GInteractionSeqDel extends InteractionSeqDel
{
	@Override
	//public Projector enterProjection(ModelNode parent, ModelNode child, Projector proj) throws ScribbleException
	public void enterProjection(ScribNode parent, ScribNode child, Projector proj) throws ScribbleException
	{
		//return (Projector) pushEnv(parent, child, proj);  // Unlike WF-choice and Reachability, Projection uses an Env for InteractionSequences
		pushVisitorEnv(parent, child, proj);  // Unlike WF-choice and Reachability, Projection uses an Env for InteractionSequences
	}
	
	@Override
	public GInteractionSeq leaveProjection(ScribNode parent, ScribNode child, Projector proj, ScribNode visited) throws ScribbleException
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
			LNode ln = (LNode) ((ProjectionEnv) gi.del().env()).getProjection();
			if (ln instanceof LInteractionSeq)  // Self comm sequence
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
		LInteractionSeq projection = AstFactoryImpl.FACTORY.LInteractionSequence(lis);
		ProjectionEnv env = proj.popEnv();
		//proj.pushEnv(new ProjectionEnv(env.getJobContext(), env.getModuleDelegate(), projection));
		proj.pushEnv(env.setProjection(projection));
		//return gis;
		return (GInteractionSeq) popAndSetVisitorEnv(parent, child, proj, gis);  // records the current checker Env to the current del; also pops and merges that env into the parent env
	}
	
	@Override
	public void enterModelBuilding(ScribNode parent, ScribNode child, ModelBuilder builder) throws ScribbleException
	{
		pushVisitorEnv(parent, child, builder);
	}

	@Override
	public GInteractionSeq leaveModelBuilding(ScribNode parent, ScribNode child, ModelBuilder builder, ScribNode visited) throws ScribbleException
	{
		GInteractionSeq gis = (GInteractionSeq) visited;
		Set<ModelAction> all = new HashSet<>();
		Map<Role, ModelAction> leaves = null;
		for (InteractionNode<Global> gi : gis.actions)
		{
			ModelEnv env = ((ModelEnv) gi.del().env());
			Set<ModelAction> as = env.getActions();
			all.addAll(as);
			//Map<Role, ModelAction> tmp = ((ModelEnv) gi.del().env()).getLeaves();
			
			/*Set<ModelAction> tmp = ((ModelEnv) gi.del().env()).getLeaves().values().stream().filter((a) -> !a.getDependencies().isEmpty()).collect(Collectors.toSet());
			for (ModelAction a : tmp)	
			{
				// FIXME: doesn't support self comm
				addDepedency(leaves, a, a.src);
				addDepedency(leaves, a, a.action.peer);
			}*/
			
			if (leaves == null)
			{
				leaves = new HashMap<>(env.getLeaves());
			}
			else
			{
				Set<ModelAction> init = as.stream().filter((a) -> a.getDependencies().isEmpty()).collect(Collectors.toSet());
				addDeps(leaves, init);
				setLeaves(leaves, env.getLeaves().values());
			}
		}

		ModelEnv env = builder.popEnv();
		env = env.setActions(all, leaves);
		builder.pushEnv(env);
		GInteractionSeq tmp = (GInteractionSeq) popAndSetVisitorEnv(parent, child, builder, visited);
		return tmp;
	}
	
	private static void addDeps(Map<Role, ModelAction> leaves, Set<ModelAction> next)
	{
		for (ModelAction a : next)
		{
			if (leaves.containsKey(a.src))
			{
				a.addDependency(leaves.get(a.src));
			}
		}
	}
	
	private static void setLeaves(Map<Role, ModelAction> leaves, Collection<ModelAction> as)
	{
		for (ModelAction a : as)
		{
			leaves.put(a.src, a);
		}
	}
	

	/*private void addDepedency(Map<Role, ModelAction> leaves, ModelAction a, Role r)
	{
		if (!leaves.containsKey(r))
		{
			leaves.put(r, a);
		}
		else
		{
			ModelAction dep = leaves.get(r);
			a.addDependency(dep);
			leaves.put(r, a);
		}
	}*/
}
