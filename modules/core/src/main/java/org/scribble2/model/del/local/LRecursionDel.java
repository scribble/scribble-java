package org.scribble2.model.del.local;

import java.util.HashSet;
import java.util.Set;

import org.scribble2.fsm.FsmBuilder;
import org.scribble2.fsm.ScribbleFsm;
import org.scribble2.model.ModelNode;
import org.scribble2.model.local.LRecursion;
import org.scribble2.model.visit.FsmConverter;
import org.scribble2.model.visit.ReachabilityChecker;
import org.scribble2.model.visit.env.FsmBuildingEnv;
import org.scribble2.model.visit.env.ReachabilityEnv;
import org.scribble2.sesstype.name.RecVar;
import org.scribble2.util.ScribbleException;

public class LRecursionDel extends LCompoundInteractionNodeDel
{
	@Override
	public LRecursion leaveReachabilityCheck(ModelNode parent, ModelNode child, ReachabilityChecker checker, ModelNode visited) throws ScribbleException
	{
		LRecursion lr = (LRecursion) visited;
		ReachabilityEnv env = checker.popEnv().mergeContext((ReachabilityEnv) lr.block.del().env());  //...HERE: env is null
		env = env.removeContinueLabel(lr.recvar.toName());
		//merged.contExitable = this.contExitable;
		checker.pushEnv(env);
		return (LRecursion) super.leaveReachabilityCheck(parent, child, checker, visited);  // records the current checker Env to the current del; also pops and merges that env into the parent env*/
	}
	
	@Override
	public LRecursion leaveFsmConversion(ModelNode parent, ModelNode child, FsmConverter conv, ModelNode visited)
	{
		LRecursion lr = (LRecursion) visited;
		FsmBuilder b = new FsmBuilder();
		Set<RecVar> labs = new HashSet<>();
		labs.add(lr.recvar.toName());
		b.makeInit(labs);
		ScribbleFsm fr = b.build();
		ScribbleFsm fblock = ((FsmBuildingEnv) lr.block.del().env()).getFsm();
		ScribbleFsm f = fr.stitch(fblock);
		FsmBuildingEnv env = conv.popEnv();
		conv.pushEnv(env.setFsm(f));
		return (LRecursion) super.leaveFsmConversion(parent, child, conv, lr);
	}
}
