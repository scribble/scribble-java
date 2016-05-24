package org.scribble.del.local;

import org.scribble.ast.MessageSigNode;
import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LAccept;
import org.scribble.del.ConnectDel;
import org.scribble.main.ScribbleException;
import org.scribble.model.local.Accept;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.EndpointGraphBuilder;
import org.scribble.visit.ProjectedChoiceSubjectFixer;

public class LAcceptDel extends ConnectDel implements LSimpleInteractionNodeDel
{
	@Override
	public LAccept leaveEndpointGraphBuilding(ScribNode parent, ScribNode child, EndpointGraphBuilder builder, ScribNode visited) throws ScribbleException
	{
		LAccept la = (LAccept) visited;
		Role peer = la.src.toName();
		/*MessageId<?> mid = la.msg.toMessage().getId();
		Payload payload = la.msg.isMessageSigNode()  // Hacky?
					? ((MessageSigNode) la.msg).payloads.toPayload()
					: Payload.EMPTY_PAYLOAD;
		builder.builder.addEdge(builder.builder.getEntry(), new Accept(peer, mid, payload), builder.builder.getExit());*/
		builder.builder.addEdge(builder.builder.getEntry(), new Accept(peer), builder.builder.getExit());
		//builder.builder.addEdge(builder.builder.getEntry(), Receive.get(peer, mid, payload), builder.builder.getExit());
		return (LAccept) super.leaveEndpointGraphBuilding(parent, child, builder, la);
		//throw new RuntimeException("TODO: " + visited);
	}

	@Override
	public void enterProjectedChoiceSubjectFixing(ScribNode parent, ScribNode child, ProjectedChoiceSubjectFixer fixer)
	{
		fixer.setChoiceSubject(((LAccept) child).src.toName());
	}
}
