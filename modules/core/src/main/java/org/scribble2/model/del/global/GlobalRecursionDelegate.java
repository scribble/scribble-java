package org.scribble2.model.del.global;

import java.util.List;

import org.scribble2.model.Continue;
import org.scribble2.model.InteractionNode;
import org.scribble2.model.ModelFactory;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.ModelNode;
import org.scribble2.model.global.GlobalRecursion;
import org.scribble2.model.local.LocalProtocolBlock;
import org.scribble2.model.local.LocalRecursion;
import org.scribble2.model.name.simple.RecursionVarNode;
import org.scribble2.model.visit.Projector;
import org.scribble2.model.visit.WellFormedChoiceChecker;
import org.scribble2.model.visit.env.ProjectionEnv;
import org.scribble2.model.visit.env.WellFormedChoiceEnv;
import org.scribble2.sesstype.kind.Local;
import org.scribble2.util.ScribbleException;

public class GlobalRecursionDelegate extends GlobalCompoundInteractionNodeDelegate
{
	@Override
	//public void leave(Recursion<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>> rec, WellFormedChoiceChecker checker)
	public GlobalRecursion leaveWFChoiceCheck(ModelNode parent, ModelNode child, WellFormedChoiceChecker checker, ModelNode visited) throws ScribbleException
	{
		GlobalRecursion rec = (GlobalRecursion) visited;
		//WellFormedChoiceEnv parent = checker.getEnv().getParent();
		WellFormedChoiceEnv merged = checker.popEnv().mergeContext((WellFormedChoiceEnv) rec.block.del().env());
		checker.pushEnv(merged);
		return rec;
	}

	@Override
	public GlobalRecursion leaveProjection(ModelNode parent, ModelNode child, Projector proj, ModelNode visited) throws ScribbleException
	{
		GlobalRecursion gr = (GlobalRecursion) visited;
		//RecursionVarNode recvar = new RecursionVarNode(gr.recvar.toName().toString());
		RecursionVarNode recvar = (RecursionVarNode) ModelFactoryImpl.FACTORY.SimpleNameNode(ModelFactory.SIMPLE_NAME.RECURSIONVAR, gr.recvar.toName().toString());
		LocalProtocolBlock block = (LocalProtocolBlock) ((ProjectionEnv) gr.block.del().env()).getProjection();	
		LocalRecursion projection = null;
		if (!block.isEmpty())
		{
			//List<LocalInteractionNode> lis = block.seq.actions;
			List<? extends InteractionNode<Local>> lis = block.seq.actions;
			if (!(lis.size() == 1 && lis.get(0) instanceof Continue))
			{
				projection = ModelFactoryImpl.FACTORY.LocalRecursion(recvar, block);
			}
		}
		//this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleContext(), projection));
		ProjectionEnv env = proj.popEnv();
		//proj.pushEnv(new ProjectionEnv(env.getJobContext(), env.getModuleDelegate(), projection));
		proj.pushEnv(new ProjectionEnv(projection));
		return (GlobalRecursion) super.leaveProjection(parent, child, proj, gr);  // records the current checker Env to the current del; also pops and merges that env into the parent env
	}
}
