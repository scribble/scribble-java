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
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.context.Projector;
import org.scribble.visit.context.env.ProjectionEnv;
import org.scribble.visit.env.InlineProtocolEnv;

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
}
