package org.scribble.del.local;

import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LAccept;
import org.scribble.ast.local.LReceive;
import org.scribble.del.MessageTransferDel;
import org.scribble.main.ScribbleException;
import org.scribble.model.local.Receive;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.EndpointGraphBuilder;
import org.scribble.visit.ProjectedChoiceSubjectFixer;

public class LAcceptDel extends MessageTransferDel implements LSimpleInteractionNodeDel
{
	@Override
	public LAccept leaveEndpointGraphBuilding(ScribNode parent, ScribNode child, EndpointGraphBuilder builder, ScribNode visited) throws ScribbleException
	{
		/*LAccept lr = (LAccept) visited;
		Role peer = lr.src.toName();
		builder.builder.addEdge(builder.builder.getEntry(), new Receive(peer, mid, payload), builder.builder.getExit());
		//builder.builder.addEdge(builder.builder.getEntry(), Receive.get(peer, mid, payload), builder.builder.getExit());
		return (LReceive) super.leaveEndpointGraphBuilding(parent, child, builder, lr);*/
		throw new RuntimeException("TODO: " + visited);
	}

	@Override
	public void enterProjectedChoiceSubjectFixing(ScribNode parent, ScribNode child, ProjectedChoiceSubjectFixer fixer)
	{
		fixer.setChoiceSubject(((LReceive) child).src.toName());
	}
}
