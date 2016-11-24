package org.scribble.del.local;

import org.scribble.ast.MessageSigNode;
import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LConnect;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.main.ScribbleException;
import org.scribble.model.endpoint.actions.EConnect;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.context.EGraphBuilder;
import org.scribble.visit.context.ProjectedChoiceSubjectFixer;

public class LConnectDel extends LConnectionActionDel implements LSimpleInteractionNodeDel
{
	@Override
	public LConnect leaveEGraphBuilding(ScribNode parent, ScribNode child, EGraphBuilder graph, ScribNode visited) throws ScribbleException
	{
		LConnect lc = (LConnect) visited;
		RoleNode dest = lc.dest;
		Role peer = dest.toName();
		MessageId<?> mid = lc.msg.toMessage().getId();
		Payload payload = lc.msg.isMessageSigNode()  // Hacky?
					? ((MessageSigNode) lc.msg).payloads.toPayload()
					: Payload.EMPTY_PAYLOAD;
		graph.util.addEdge(graph.util.getEntry(), new EConnect(peer, mid, payload), graph.util.getExit());
		//graph.builder.addEdge(graph.builder.getEntry(), new Connect(peer), graph.builder.getExit());
		////builder.builder.addEdge(builder.builder.getEntry(), Send.get(peer, mid, payload), builder.builder.getExit());
		return (LConnect) super.leaveEGraphBuilding(parent, child, graph, lc);
	}

	@Override
	public void enterProjectedChoiceSubjectFixing(ScribNode parent, ScribNode child, ProjectedChoiceSubjectFixer fixer)
	{
		fixer.setChoiceSubject(((LConnect) child).src.toName());
	}
}
