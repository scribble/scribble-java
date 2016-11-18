package org.scribble.del.local;

import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LDisconnect;
import org.scribble.del.ConnectionActionDel;
import org.scribble.main.ScribbleException;
import org.scribble.model.endpoint.actions.LMDisconnect;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.context.EndpointGraphBuilder;
import org.scribble.visit.context.ProjectedChoiceSubjectFixer;

public class LDisconnectDel extends ConnectionActionDel implements LSimpleInteractionNodeDel
{
	@Override
	public LDisconnect leaveEndpointGraphBuilding(ScribNode parent, ScribNode child, EndpointGraphBuilder builder, ScribNode visited) throws ScribbleException
	{
		LDisconnect ld = (LDisconnect) visited;
		Role peer = ld.peer.toName();
		builder.builder.addEdge(builder.builder.getEntry(), new LMDisconnect(peer), builder.builder.getExit());
		return (LDisconnect) super.leaveEndpointGraphBuilding(parent, child, builder, ld);
	}

	@Override
	public void enterProjectedChoiceSubjectFixing(ScribNode parent, ScribNode child, ProjectedChoiceSubjectFixer fixer)
	{
		fixer.setChoiceSubject(((LDisconnect) child).src.toName());
	}
}
