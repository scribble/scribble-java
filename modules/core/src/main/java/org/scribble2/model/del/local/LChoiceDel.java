package org.scribble2.model.del.local;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.scribble2.fsm.FsmBuilder;
import org.scribble2.fsm.ProtocolState;
import org.scribble2.fsm.ScribbleFsm;
import org.scribble2.model.ModelNode;
import org.scribble2.model.local.LChoice;
import org.scribble2.model.visit.FsmConverter;
import org.scribble2.model.visit.ReachabilityChecker;
import org.scribble2.model.visit.env.FsmBuildingEnv;
import org.scribble2.model.visit.env.ReachabilityEnv;
import org.scribble2.util.ScribbleException;

public class LChoiceDel extends LCompoundInteractionNodeDel
{
	@Override
	public LChoice leaveReachabilityCheck(ModelNode parent, ModelNode child, ReachabilityChecker checker, ModelNode visited) throws ScribbleException
	{
		LChoice cho = (LChoice) visited;
		List<ReachabilityEnv> benvs =
				cho.blocks.stream().map((b) -> (ReachabilityEnv) b.del().env()).collect(Collectors.toList());
		ReachabilityEnv merged = checker.popEnv().mergeForChoice(benvs);
		checker.pushEnv(merged);
		return (LChoice) super.leaveReachabilityCheck(parent, child, checker, visited);  // records the current checker Env to the current del; also pops and merges that env into the parent env
	}

	@Override
	public LChoice leaveFsmConversion(ModelNode parent, ModelNode child, FsmConverter conv, ModelNode visited)
	{
		LChoice lc = (LChoice) visited;
		FsmBuilder b = new FsmBuilder();
		ProtocolState init = b.makeInit(Collections.emptySet());
		ProtocolState term = b.newState(Collections.emptySet());

		List<FsmBuildingEnv> benvs =
				lc.blocks.stream().map((block) -> (FsmBuildingEnv) block.del().env()).collect(Collectors.toList());
			
		ScribbleFsm f = benvs.get(0).getFsm();
		f = f.embed(init, f, term);
		for (FsmBuildingEnv env : benvs.subList(1, benvs.size()))
		{
			f = f.embed(init, env.getFsm(), term);
		}
		
		//b.addEdge(init, new Send(peer, mid), term);
		//ScribbleFsm f = b.build();
		FsmBuildingEnv env = conv.popEnv();
		conv.pushEnv(env.setFsm(f));
		return (LChoice) super.leaveFsmConversion(parent, child, conv, lc);
	}
}
