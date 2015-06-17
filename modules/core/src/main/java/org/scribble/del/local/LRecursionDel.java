package org.scribble.del.local;

import java.util.HashSet;
import java.util.Set;

import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LRecursion;
import org.scribble.ast.visit.FsmConstructor;
import org.scribble.ast.visit.ReachabilityChecker;
import org.scribble.ast.visit.env.ReachabilityEnv;
import org.scribble.main.ScribbleException;
import org.scribble.model.local.ProtocolState;
import org.scribble.sesstype.name.RecVar;

public class LRecursionDel extends LCompoundInteractionNodeDel
{
	@Override
	public LRecursion leaveReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker, ScribNode visited) throws ScribbleException
	{
		LRecursion lr = (LRecursion) visited;
		ReachabilityEnv env = checker.popEnv().mergeContext((ReachabilityEnv) lr.block.del().env());
		env = env.removeContinueLabel(lr.recvar.toName());
		//merged.contExitable = this.contExitable;
		checker.pushEnv(env);
		return (LRecursion) super.leaveReachabilityCheck(parent, child, checker, visited);  // records the current checker Env to the current del; also pops and merges that env into the parent env*/
	}
	
	@Override
	public void enterFsmConstruction(ScribNode parent, ScribNode child, FsmConstructor conv)
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
	public LRecursion leaveFsmConstruction(ScribNode parent, ScribNode child, FsmConstructor conv, ScribNode visited)
	{
		LRecursion lr = (LRecursion) visited;
		RecVar rv = lr.recvar.toName();
		conv.builder.removeRecursionEntry(rv);
		return (LRecursion) super.leaveFsmConstruction(parent, child, conv, lr);
	}
}
