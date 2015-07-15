package org.scribble.del.local;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.ast.local.LRecursion;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.del.RecursionDel;
import org.scribble.main.ScribbleException;
import org.scribble.model.local.ProtocolState;
import org.scribble.sesstype.name.RecVar;
import org.scribble.visit.FsmGenerator;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.ReachabilityChecker;
import org.scribble.visit.env.InlineProtocolEnv;
import org.scribble.visit.env.ReachabilityEnv;

public class LRecursionDel extends RecursionDel implements LCompoundInteractionNodeDel
{
	@Override
	public ScribNode leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner builder, ScribNode visited) throws ScribbleException
	{
		LRecursion gr = (LRecursion) visited;
		RecVarNode recvar = gr.recvar.clone();
		LProtocolBlock block = (LProtocolBlock) ((InlineProtocolEnv) gr.block.del().env()).getTranslation();	
		LRecursion inlined = AstFactoryImpl.FACTORY.LRecursion(recvar, block);
		builder.pushEnv(builder.popEnv().setTranslation(inlined));
		return (LRecursion) super.leaveProtocolInlining(parent, child, builder, gr);
	}

	@Override
	public LRecursion leaveReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker, ScribNode visited) throws ScribbleException
	{
		LRecursion lr = (LRecursion) visited;
		ReachabilityEnv env = checker.popEnv().mergeContext((ReachabilityEnv) lr.block.del().env());
		env = env.removeContinueLabel(lr.recvar.toName());
		checker.pushEnv(env);
		return (LRecursion) LCompoundInteractionNodeDel.super.leaveReachabilityCheck(parent, child, checker, visited);  // records the current checker Env to the current del; also pops and merges that env into the parent env*/
	}
	
	@Override
	public void enterFsmGeneration(ScribNode parent, ScribNode child, FsmGenerator conv)
	{
		super.enterFsmGeneration(parent, child, conv);
		LRecursion lr = (LRecursion) child;
		RecVar rv = lr.recvar.toName();
		// Update existing state, not replace it -- cf. LDoDel
		ProtocolState s = conv.builder.getEntry();
		s.addLabel(rv.toString());
		conv.builder.setRecursionEntry(rv);
	}

	@Override
	public LRecursion leaveFsmGeneration(ScribNode parent, ScribNode child, FsmGenerator conv, ScribNode visited)
	{
		LRecursion lr = (LRecursion) visited;
		RecVar rv = lr.recvar.toName();
		conv.builder.removeRecursionEntry(rv);
		return (LRecursion) super.leaveFsmGeneration(parent, child, conv, lr);
	}
}
