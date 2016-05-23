package org.scribble.del.local;

import java.util.Iterator;
import java.util.List;

import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LContinue;
import org.scribble.del.ContinueDel;
import org.scribble.main.ScribbleException;
import org.scribble.model.local.EndpointState;
import org.scribble.model.local.IOAction;
import org.scribble.sesstype.name.RecVar;
import org.scribble.visit.EndpointGraphBuilder;
import org.scribble.visit.ReachabilityChecker;
import org.scribble.visit.env.ReachabilityEnv;

public class LContinueDel extends ContinueDel implements LSimpleInteractionNodeDel
{
	@Override
	public LContinue leaveReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker, ScribNode visited) throws ScribbleException
	{
		// "Entering" the continue here in leave, where we can merge the new state into the parent Env
		// Generally: if side effecting Env state to be merged into the parent (not just popped and discarded), leave must be overridden to do so
		LContinue lc = (LContinue) visited;
		ReachabilityEnv env = checker.popEnv().addContinueLabel(lc.recvar.toName());
		setEnv(env);  // Env recording probably not needed for all LocalInteractionNodes, just the compound ones, like for WF-choice checking
		checker.pushEnv(checker.popEnv().mergeContext(env));
		return lc;
	}

	@Override
	public LContinue leaveEndpointGraphBuilding(ScribNode parent, ScribNode child, EndpointGraphBuilder graph, ScribNode visited)
	{
		LContinue lr = (LContinue) visited;
		RecVar rv = lr.recvar.toName();
		//graph.builder.setEntry(graph.builder.getRecursionEntry(rv));
		//if (graph.builder.getPredecessor() == null)  // unguarded choice case
		if (graph.builder.isUnguardedInChoice())
		{
			IOAction a = graph.builder.getEnacting(rv);
			List<EndpointState> ss = graph.builder.getRecursionEntry(rv).acceptAll(a);
			for (EndpointState s : ss)  // FIXME: produces non-det edges to different rec entries -- but equiv, do just pick 1?
			{
				graph.builder.addEdge(graph.builder.getEntry(), a, s);
			}
			//graph.builder.addEdge(graph.builder.getEntry(), a, ss.get(0));  // FIXME: OK to just pick 1? -- maybe: but the original non-det choice before enacting the recursion is still there anyway
		}
		else
		{
			// ** "Overwrites" previous edge built by send/receive(s) leading to this continue
			/*graph.builder.removeLastEdge(graph.builder.getPredecessors());  // Hacky? -- cannot implicitly overwrite (addEdge) given non-det machines
			graph.builder.addEdge(graph.builder.getPredecessors(), graph.builder.getPreviousActions(), graph.builder.getRecursionEntry(rv));*/
			Iterator<EndpointState> preds = graph.builder.getPredecessors().iterator();
			Iterator<IOAction> prevs = graph.builder.getPreviousActions().iterator();
			EndpointState entry = graph.builder.getEntry();
			while (preds.hasNext())
			{
				EndpointState pred = preds.next();
				IOAction prev = prevs.next();
				graph.builder.removeEdge(pred, prev, entry);
				graph.builder.addEdge(pred, prev, graph.builder.getRecursionEntry(rv));
			}
		}
		return (LContinue) super.leaveEndpointGraphBuilding(parent, child, graph, lr);
	}
}
