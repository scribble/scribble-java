package org.scribble.del.global;

import java.util.List;

import org.scribble.ast.Continue;
import org.scribble.ast.InteractionNode;
import org.scribble.ast.ModelFactoryImpl;
import org.scribble.ast.ModelNode;
import org.scribble.ast.global.GRecursion;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.ast.local.LRecursion;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.ast.visit.Projector;
import org.scribble.ast.visit.WellFormedChoiceChecker;
import org.scribble.ast.visit.env.ProjectionEnv;
import org.scribble.ast.visit.env.WellFormedChoiceEnv;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.kind.RecVarKind;
import org.scribble.util.ScribbleException;

public class GRecursionDel extends GCompoundInteractionNodeDel
{
	@Override
	//public void leave(Recursion<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>> rec, WellFormedChoiceChecker checker)
	public GRecursion leaveWFChoiceCheck(ModelNode parent, ModelNode child, WellFormedChoiceChecker checker, ModelNode visited) throws ScribbleException
	{
		GRecursion rec = (GRecursion) visited;
		//WellFormedChoiceEnv parent = checker.getEnv().getParent();
		WellFormedChoiceEnv merged = checker.popEnv().mergeContext((WellFormedChoiceEnv) rec.block.del().env());
		checker.pushEnv(merged);
		return rec;
	}

	@Override
	public GRecursion leaveProjection(ModelNode parent, ModelNode child, Projector proj, ModelNode visited) throws ScribbleException
	{
		GRecursion gr = (GRecursion) visited;
		//RecursionVarNode recvar = new RecursionVarNode(gr.recvar.toName().toString());
		//RecursionVarNode recvar = (RecursionVarNode) ModelFactoryImpl.FACTORY.SimpleNameNode(ModelFactory.SIMPLE_NAME.RECURSIONVAR, gr.recvar.toName().toString());
		RecVarNode recvar = (RecVarNode) ModelFactoryImpl.FACTORY.SimpleNameNode(RecVarKind.KIND, gr.recvar.toName().toString());
		LProtocolBlock block = (LProtocolBlock) ((ProjectionEnv) gr.block.del().env()).getProjection();	
		LRecursion projection = null;
		if (!block.isEmpty())
		{
			//List<LocalInteractionNode> lis = block.seq.actions;
			List<? extends InteractionNode<Local>> lis = block.seq.actions;
			if (!(lis.size() == 1 && lis.get(0) instanceof Continue))
			{
				projection = ModelFactoryImpl.FACTORY.LRecursion(recvar, block);
			}
		}
		//this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleContext(), projection));
		ProjectionEnv env = proj.popEnv();
		//proj.pushEnv(new ProjectionEnv(env.getJobContext(), env.getModuleDelegate(), projection));
		proj.pushEnv(new ProjectionEnv(projection));
		return (GRecursion) super.leaveProjection(parent, child, proj, gr);  // records the current checker Env to the current del; also pops and merges that env into the parent env
	}
}
