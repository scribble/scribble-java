package org.scribble.del.global;

import java.util.LinkedList;
import java.util.List;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GInteractionNode;
import org.scribble.ast.global.GInteractionSeq;
import org.scribble.ast.local.LInteractionNode;
import org.scribble.ast.local.LInteractionSeq;
import org.scribble.ast.local.LNode;
import org.scribble.del.InteractionSeqDel;
import org.scribble.del.ScribDelBase;
import org.scribble.main.ScribbleException;
import org.scribble.visit.Projector;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.env.InlineProtocolEnv;
import org.scribble.visit.env.ProjectionEnv;

public class GInteractionSeqDel extends InteractionSeqDel
{
	// enter in super
	@Override
	public ScribNode leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner inl, ScribNode visited) throws ScribbleException
	{
		GInteractionSeq gis = (GInteractionSeq) visited;
		List<GInteractionNode> gins = new LinkedList<GInteractionNode>();
		for (GInteractionNode gi : gis.getInteractions())
		{
			ScribNode inlined = ((InlineProtocolEnv) gi.del().env()).getTranslation();
			if (inlined instanceof GInteractionSeq)  // A do got inlined
			{
				gins.addAll(((GInteractionSeq) inlined).getInteractions());
			}
			else
			{
				gins.add((GInteractionNode) inlined);
			}
		}
		GInteractionSeq inlined = AstFactoryImpl.FACTORY.GInteractionSeq(gins);
		inl.pushEnv(inl.popEnv().setTranslation(inlined));
		return (GInteractionSeq) ScribDelBase.popAndSetVisitorEnv(this, inl, gis);
	}

	@Override
	public void enterProjection(ScribNode parent, ScribNode child, Projector proj) throws ScribbleException
	{
		ScribDelBase.pushVisitorEnv(this, proj);  // Unlike WF-choice and Reachability, Projection uses an Env for InteractionSequences
	}
	
	@Override
	public GInteractionSeq leaveProjection(ScribNode parent, ScribNode child, Projector proj, ScribNode visited) throws ScribbleException
	{
		GInteractionSeq gis = (GInteractionSeq) visited;
		List<LInteractionNode> lis = new LinkedList<>();
		for (GInteractionNode gi : gis.getInteractions())
		{
			LNode ln = (LNode) ((ProjectionEnv) gi.del().env()).getProjection();
			//LNode ln = ((GInteractionNodeDel) gi.del()).project(gi, self);  // FIXME: won't work for do
			// FIXME: move node-specific projects to G nodes (not dels) and take child projections as params, bit like reconstruct
			if (ln instanceof LInteractionSeq)  // Self comm sequence
			{
				lis.addAll(((LInteractionSeq) ln).getInteractions());
			}
			else if (ln != null) // null is used for empty projection
			{
				lis.add((LInteractionNode) ln);
			}
		}
		/*if (lis.size() == 1)  // NO: needed for e.g. rec X { 1() from A to B; choice at A { continue X; } or { 2() from A to B; } } -- do instead in GRecursion
		{
			if (lis.get(0) instanceof Continue)
			{
				lis.clear();
			}
		}*/
		LInteractionSeq projection = gis.project(proj.peekSelf(), lis);
		proj.pushEnv(proj.popEnv().setProjection(projection));
		return (GInteractionSeq) ScribDelBase.popAndSetVisitorEnv(this, proj, gis);
	}
	
	/*public GInteractionSeq visitForGlobalModelBuilding(GlobalModelBuilder conv, GInteractionSeq child)
	{
		GModelState entry = conv.builder.getEntry();
		GModelState exit = conv.builder.getExit();
		try
		{
			for (int i = 0; i < child.getInteractions().size(); i++)
			{
				if (i == child.getInteractions().size() - 1)
				{
					conv.builder.setExit(exit);
					child.getInteractions().get(i).accept(conv);
				}
				else
				{
					GModelState tmp = conv.builder.newState(Collections.emptySet());
					conv.builder.setExit(tmp);
					child.getInteractions().get(i).accept(conv);
					conv.builder.setEntry(conv.builder.getExit());  // exit may not be tmp, entry/exit can be modified, e.g. continue
				}
			}
		}
		catch (ScribbleException e)
		{
			throw new RuntimeException("Shouldn't get in here: " + e);
		}
		//conv.builder.setExit(exit);
		conv.builder.setEntry(entry);
		return child;	
	}*/

	/*@Override
	public void enterModelBuilding(ScribNode parent, ScribNode child, GlobalModelBuilder builder) throws ScribbleException
	{
		ScribDelBase.pushVisitorEnv(this, builder);
	}

	@Override
	public GInteractionSeq leaveModelBuilding(ScribNode parent, ScribNode child, GlobalModelBuilder builder, ScribNode visited) throws ScribbleException
	{
		/*GInteractionSeq gis = (GInteractionSeq) visited;
		Set<GModelAction> all = new HashSet<>();
		Map<Role, GModelAction> leaves = null;
		for (InteractionNode<Global> gi : gis.getInteractions())
		{
			ModelEnv env = ((ModelEnv) gi.del().env());
			Set<GModelAction> as = env.getActions();
			all.addAll(as);
			if (leaves == null)
			{
				leaves = new HashMap<>(env.getLeaves());
			}
			else
			{
				Set<GModelAction> init = as.stream().filter((a) -> a.getDependencies().isEmpty()).collect(Collectors.toSet());
				addDeps(leaves, init);
				setLeaves(leaves, env.getLeaves().values());
			}
		}

		ModelEnv env = builder.popEnv();
		env = env.setActions(all, leaves);
		builder.pushEnv(env);
		GInteractionSeq tmp = (GInteractionSeq) ScribDelBase.popAndSetVisitorEnv(this, builder, visited);
		return tmp;* /
		throw new RuntimeException("TODO: " + visited);
	}
	
	private static void addDeps(Map<Role, GModelAction> leaves, Set<GModelAction> next)
	{
		/*for (GModelAction a : next)
		{
			if (leaves.containsKey(a.src))
			{
				a.addDependency(leaves.get(a.src));
			}
		}* /
		throw new RuntimeException("TODO: ");
	}
	
	private static void setLeaves(Map<Role, GModelAction> leaves, Collection<GModelAction> as)
	{
		for (GModelAction a : as)
		{
			leaves.put(a.src, a);
		}
	}*/
}
