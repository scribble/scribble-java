package org.scribble.del.local;

import java.util.List;

import org.scribble.ast.MessageSigNode;
import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LMessageTransfer;
import org.scribble.ast.local.LSend;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.main.ScribbleException;
import org.scribble.model.endpoint.actions.ESend;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.context.EGraphBuilder;
import org.scribble.visit.context.ProjectedChoiceSubjectFixer;
import org.scribble.visit.wf.ExplicitCorrelationChecker;

public class LSendDel extends LMessageTransferDel
{
	@Override
	public LSend leaveEGraphBuilding(ScribNode parent, ScribNode child, EGraphBuilder graph, ScribNode visited) throws ScribbleException
	{
		LSend ls = (LSend) visited;
		List<RoleNode> dests = ls.getDestinations();
		if (dests.size() > 1)
		{
			throw new ScribbleException("[TODO] EFSM building for multicast not supported: " + ls);
		}
		Role peer = dests.get(0).toName();
		MessageId<?> mid = ls.msg.toMessage().getId();
		Payload payload = ls.msg.isMessageSigNode()  // Hacky?
					? ((MessageSigNode) ls.msg).payloads.toPayload()
					: Payload.EMPTY_PAYLOAD;
		graph.util.addEdge(graph.util.getEntry(), new ESend(peer, mid, payload), graph.util.getExit());
		//builder.builder.addEdge(builder.builder.getEntry(), Send.get(peer, mid, payload), builder.builder.getExit());
		return (LSend) super.leaveEGraphBuilding(parent, child, graph, ls);
	}

	// Could make a LMessageTransferDel to factor this out with LReceiveDel
	@Override
	public void enterProjectedChoiceSubjectFixing(ScribNode parent, ScribNode child, ProjectedChoiceSubjectFixer fixer)
	{
		fixer.setChoiceSubject(((LSend) child).src.toName());
	}
	
	@Override
	public LMessageTransfer leaveExplicitCorrelationCheck(ScribNode parent, ScribNode child, ExplicitCorrelationChecker checker, ScribNode visited) throws ScribbleException
	{
		LMessageTransfer lmt = (LMessageTransfer) visited;
		checker.pushEnv(checker.popEnv().disableAccept());
		return lmt;
	}
}
