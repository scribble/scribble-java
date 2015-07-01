package org.scribble.del.local;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LInteractionSeq;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.del.ProtocolBlockDel;
import org.scribble.main.ScribbleException;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.ReachabilityChecker;
import org.scribble.visit.env.InlineProtocolEnv;


public class LProtocolBlockDel extends ProtocolBlockDel
{
	@Override
	public ScribNode leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner builder, ScribNode visited) throws ScribbleException
	{
		LProtocolBlock gpd = (LProtocolBlock) visited;
		LInteractionSeq seq = (LInteractionSeq) ((InlineProtocolEnv) gpd.seq.del().env()).getTranslation();	
		LProtocolBlock inlined = AstFactoryImpl.FACTORY.LProtocolBlock(seq);
		builder.pushEnv(builder.popEnv().setTranslation(inlined));
		return (LProtocolBlock) popAndSetVisitorEnv(parent, child, builder, gpd);
	}

	@Override
	public void enterReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker) throws ScribbleException
	{
		pushVisitorEnv(parent, child, checker);
	}

	@Override
	public LProtocolBlock leaveReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker, ScribNode visited) throws ScribbleException
	{
		return (LProtocolBlock) popAndSetVisitorEnv(parent, child, checker, visited);  // records the current checker Env to the current del; also pops and merges that env into the parent env
	}
}
