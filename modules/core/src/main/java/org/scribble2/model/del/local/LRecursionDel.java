package org.scribble2.model.del.local;

import java.util.HashSet;
import java.util.Set;

import org.scribble2.fsm.ProtocolState;
import org.scribble2.model.ModelNode;
import org.scribble2.model.local.LRecursion;
import org.scribble2.model.visit.FsmConstructor;
import org.scribble2.model.visit.ReachabilityChecker;
import org.scribble2.model.visit.env.ReachabilityEnv;
import org.scribble2.sesstype.name.RecVar;
import org.scribble2.util.ScribbleException;

public class LRecursionDel extends LCompoundInteractionNodeDel
{
	@Override
	public LRecursion leaveReachabilityCheck(ModelNode parent, ModelNode child, ReachabilityChecker checker, ModelNode visited) throws ScribbleException
	{
		LRecursion lr = (LRecursion) visited;
		ReachabilityEnv env = checker.popEnv().mergeContext((ReachabilityEnv) lr.block.del().env());
		env = env.removeContinueLabel(lr.recvar.toName());
		//merged.contExitable = this.contExitable;
		checker.pushEnv(env);
		return (LRecursion) super.leaveReachabilityCheck(parent, child, checker, visited);  // records the current checker Env to the current del; also pops and merges that env into the parent env*/
	}
	
	@Override
	public void enterFsmConstruction(ModelNode parent, ModelNode child, FsmConstructor conv)
	{
		super.enterFsmConstruction(parent, child, conv);
		LRecursion lr = (LRecursion) child;
		RecVar rv = lr.recvar.toName();
		/*Set<RecVar> labs = new HashSet<>();
		labs.add(rv);*/
		Set<String> labs = new HashSet<>(conv.builder.getEntry().getLabels());
		labs.add(rv.toString());
		ProtocolState s = conv.builder.newState(labs);
		conv.builder.setEntry(s);
		conv.builder.setRecursionEntry(rv);
	}

	@Override
	public LRecursion leaveFsmConstruction(ModelNode parent, ModelNode child, FsmConstructor conv, ModelNode visited)
	{
		LRecursion lr = (LRecursion) visited;
		RecVar rv = lr.recvar.toName();
		conv.builder.removeRecursionEntry(rv);
		return (LRecursion) super.leaveFsmConstruction(parent, child, conv, lr);
	}
}
