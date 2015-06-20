package org.scribble.del.global;

import java.util.List;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.Continue;
import org.scribble.ast.InteractionNode;
import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GRecursion;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.ast.local.LRecursion;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.kind.RecVarKind;
import org.scribble.visit.Projector;
import org.scribble.visit.WellFormedChoiceChecker;
import org.scribble.visit.env.ProjectionEnv;
import org.scribble.visit.env.WellFormedChoiceEnv;

public class GRecursionDel extends GCompoundInteractionNodeDel
{
	@Override
	public GRecursion leaveWFChoiceCheck(ScribNode parent, ScribNode child, WellFormedChoiceChecker checker, ScribNode visited) throws ScribbleException
	{
		GRecursion rec = (GRecursion) visited;
		WellFormedChoiceEnv merged = checker.popEnv().mergeContext((WellFormedChoiceEnv) rec.block.del().env());
		checker.pushEnv(merged);
		return rec;
	}

	@Override
	public GRecursion leaveProjection(ScribNode parent, ScribNode child, Projector proj, ScribNode visited) throws ScribbleException
	{
		GRecursion gr = (GRecursion) visited;
		RecVarNode recvar = (RecVarNode) AstFactoryImpl.FACTORY.SimpleNameNode(RecVarKind.KIND, gr.recvar.toName().toString());
		LProtocolBlock block = (LProtocolBlock) ((ProjectionEnv) gr.block.del().env()).getProjection();	
		LRecursion projection = null;
		if (!block.isEmpty())
		{
			List<? extends InteractionNode<Local>> lis = block.seq.actions;
			if (!(lis.size() == 1 && lis.get(0) instanceof Continue))
			{
				projection = AstFactoryImpl.FACTORY.LRecursion(recvar, block);
			}
		}
		proj.pushEnv(proj.popEnv().setProjection(projection));
		return (GRecursion) super.leaveProjection(parent, child, proj, gr);
	}
}
