package org.scribble.del.local;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.ast.local.LRecursion;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.del.RecursionDel;
import org.scribble.main.ScribbleException;
import org.scribble.model.local.ProtocolState;
import org.scribble.sesstype.kind.RecVarKind;
import org.scribble.sesstype.name.RecVar;
import org.scribble.visit.FsmBuilder;
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
		RecVarNode recvar = (RecVarNode) AstFactoryImpl.FACTORY.SimpleNameNode(RecVarKind.KIND, gr.recvar.toName().toString());
		LProtocolBlock block = (LProtocolBlock) ((InlineProtocolEnv) gr.block.del().env()).getTranslation();	
		LRecursion inlined = AstFactoryImpl.FACTORY.LRecursion(recvar, block);
		builder.pushEnv(builder.popEnv().setTranslation(inlined));
		return (LRecursion) super.leaveProtocolInlining(parent, child, builder, gr);
	}

	/*@Override
	public void enterInlinedProtocolUnfolding(ScribNode parent, ScribNode child, InlinedProtocolUnfolder unf) throws ScribbleException
	{
		super.enterInlinedProtocolUnfolding(parent, child, unf);
		LRecursion lr = (LRecursion) child;
		RecVar recvar = lr.recvar.toName();
		//LInteractionSeq gis = gr.getBlock().getInteractionSeq();  // FIXME: should clone with fresh dels -- though currently the only dels to store persistent state are protocoldecl and gprotocoldef, which are outside of the main session type (protocol body) visiting
		LProtocolBlock lpb = lr.getBlock();
		//unf.setRecVar(recvar, lpb);
		unf.setRecVar(recvar, lr);
	}

	@Override
	public ScribNode leaveInlinedProtocolUnfolding(ScribNode parent, ScribNode child, InlinedProtocolUnfolder unf, ScribNode visited) throws ScribbleException
	{
		LRecursion rec = (LRecursion) visited;
		RecVar recvar = rec.recvar.toName();
		unf.removeRecVar(recvar);
		UnfoldingEnv merged = unf.popEnv().mergeContext((UnfoldingEnv) rec.block.del().env());
		unf.pushEnv(merged);
		return (LRecursion) super.leaveInlinedProtocolUnfolding(parent, child, unf, rec);
	}*/

	@Override
	public LRecursion leaveReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker, ScribNode visited) throws ScribbleException
	{
		LRecursion lr = (LRecursion) visited;
		ReachabilityEnv env = checker.popEnv().mergeContext((ReachabilityEnv) lr.block.del().env());
		env = env.removeContinueLabel(lr.recvar.toName());
		//merged.contExitable = this.contExitable;
		checker.pushEnv(env);
		return (LRecursion) LCompoundInteractionNodeDel.super.leaveReachabilityCheck(parent, child, checker, visited);  // records the current checker Env to the current del; also pops and merges that env into the parent env*/
	}
	
	@Override
	public void enterFsmBuilder(ScribNode parent, ScribNode child, FsmBuilder conv)
	{
		super.enterFsmBuilder(parent, child, conv);
		LRecursion lr = (LRecursion) child;
		RecVar rv = lr.recvar.toName();
		/*Set<String> labs = new HashSet<>(conv.builder.getEntry().getLabels());
		labs.add(rv.toString());
		//ProtocolState s = conv.builder.newState(labs);  // FIXME: need to update existing state, not replace it -- cf. LDoDel
		conv.builder.setEntry(s)*/
		ProtocolState s = conv.builder.getEntry();
		s.addLabel(rv.toString());
		conv.builder.setRecursionEntry(rv);
	}

	@Override
	public LRecursion leaveFsmBuilder(ScribNode parent, ScribNode child, FsmBuilder conv, ScribNode visited)
	{
		LRecursion lr = (LRecursion) visited;
		RecVar rv = lr.recvar.toName();
		conv.builder.removeRecursionEntry(rv);
		return (LRecursion) super.leaveFsmBuilder(parent, child, conv, lr);
	}
}
