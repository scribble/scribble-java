package org.scribble.del.local;

import org.scribble.ast.MessageSigNode;
import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LReceive;
import org.scribble.del.MessageTransferDel;
import org.scribble.model.local.Receive;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.EndpointGraphBuilder;
import org.scribble.visit.ProjectedChoiceSubjectFixer;

public class LReceiveDel extends MessageTransferDel implements LSimpleInteractionNodeDel
{
	@Override
	public LReceive leaveGraphBuilding(ScribNode parent, ScribNode child, EndpointGraphBuilder graph, ScribNode visited)
	{
		LReceive lr = (LReceive) visited;
		Role peer = lr.src.toName();
		MessageId<?> mid = lr.msg.toMessage().getId();
		Payload payload = (lr.msg.isMessageSigNode())  // Hacky?
				? ((MessageSigNode) lr.msg).payloads.toPayload()
				: Payload.EMPTY_PAYLOAD;
		graph.builder.addEdge(graph.builder.getEntry(), new Receive(peer, mid, payload), graph.builder.getExit());
		return (LReceive) super.leaveGraphBuilding(parent, child, graph, lr);
	}

	@Override
	public void enterProjectedChoiceSubjectFixing(ScribNode parent, ScribNode child, ProjectedChoiceSubjectFixer fixer)
	{
		fixer.setChoiceSubject(((LReceive) child).src.toName());
	}
}
