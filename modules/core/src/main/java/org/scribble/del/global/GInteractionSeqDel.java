package org.scribble.del.global;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.Continue;
import org.scribble.ast.InteractionNode;
import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GInteractionNode;
import org.scribble.ast.global.GInteractionSeq;
import org.scribble.ast.local.LInteractionNode;
import org.scribble.ast.local.LInteractionSeq;
import org.scribble.ast.local.LNode;
import org.scribble.del.InteractionSeqDel;
import org.scribble.main.ScribbleException;
import org.scribble.model.global.ModelAction;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.ModelBuilder;
import org.scribble.visit.Projector;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.env.InlineProtocolEnv;
import org.scribble.visit.env.ModelEnv;
import org.scribble.visit.env.ProjectionEnv;


public class GInteractionSeqDel extends InteractionSeqDel
{
	@Override
	public void enterProjection(ScribNode parent, ScribNode child, Projector proj) throws ScribbleException
	{
		//pushVisitorEnv(parent, child, proj);  // Unlike WF-choice and Reachability, Projection uses an Env for InteractionSequences
		pushVisitorEnv(this, proj);  // Unlike WF-choice and Reachability, Projection uses an Env for InteractionSequences
	}
	
	@Override
	public GInteractionSeq leaveProjection(ScribNode parent, ScribNode child, Projector proj, ScribNode visited) throws ScribbleException
	{
		GInteractionSeq gis = (GInteractionSeq) visited;
		List<LInteractionNode> lis = new LinkedList<>();
		for (InteractionNode<Global> gi : gis.actions)
		{
			LNode ln = (LNode) ((ProjectionEnv) gi.del().env()).getProjection();
			if (ln instanceof LInteractionSeq)  // Self comm sequence
			{
				lis.addAll(((LInteractionSeq) ln).getActions());
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
		LInteractionSeq projection = AstFactoryImpl.FACTORY.LInteractionSeq(lis);
		proj.pushEnv(proj.popEnv().setProjection(projection));
		//return (GInteractionSeq) popAndSetVisitorEnv(parent, child, proj, gis);
		return (GInteractionSeq) popAndSetVisitorEnv(this, proj, gis);
	}
	
	@Override
	public ScribNode leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner builder, ScribNode visited) throws ScribbleException
	{
		GInteractionSeq gis = (GInteractionSeq) visited;
		List<GInteractionNode> gins = new LinkedList<GInteractionNode>();
		for (GInteractionNode gi : gis.getActions())
		{
			ScribNode inlined = ((InlineProtocolEnv) gi.del().env()).getTranslation();
			if (inlined instanceof GInteractionSeq)  // A do got inlined
			{
				gins.addAll(((GInteractionSeq) inlined).getActions());
			}
			else
			{
				gins.add((GInteractionNode) inlined);
			}
		}
		GInteractionSeq inlined = AstFactoryImpl.FACTORY.GInteractionSeq(gins);
		builder.pushEnv(builder.popEnv().setTranslation(inlined));
		//return (GInteractionSeq) popAndSetVisitorEnv(parent, child, builder, gis);
		return (GInteractionSeq) popAndSetVisitorEnv(this, builder, gis);
	}
	
	@Override
	public void enterModelBuilding(ScribNode parent, ScribNode child, ModelBuilder builder) throws ScribbleException
	{
		//pushVisitorEnv(parent, child, builder);
		pushVisitorEnv(this, builder);
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
		//GInteractionSeq tmp = (GInteractionSeq) popAndSetVisitorEnv(parent, child, builder, visited);
		GInteractionSeq tmp = (GInteractionSeq) popAndSetVisitorEnv(this, builder, visited);
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
}
