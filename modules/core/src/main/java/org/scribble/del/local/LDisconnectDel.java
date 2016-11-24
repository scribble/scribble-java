package org.scribble.del.local;

import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LDisconnect;
import org.scribble.del.ConnectionActionDel;
import org.scribble.main.ScribbleException;
import org.scribble.model.endpoint.actions.EDisconnect;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.context.EGraphBuilder;
import org.scribble.visit.context.ProjectedChoiceSubjectFixer;

public class LDisconnectDel extends ConnectionActionDel implements LSimpleInteractionNodeDel
{
	@Override
	public LDisconnect leaveEGraphBuilding(ScribNode parent, ScribNode child, EGraphBuilder builder, ScribNode visited) throws ScribbleException
	{
		LDisconnect ld = (LDisconnect) visited;
		Role peer = ld.peer.toName();
		builder.util.addEdge(builder.util.getEntry(), new EDisconnect(peer), builder.util.getExit());
		return (LDisconnect) super.leaveEGraphBuilding(parent, child, builder, ld);
	}

	@Override
	public void enterProjectedChoiceSubjectFixing(ScribNode parent, ScribNode child, ProjectedChoiceSubjectFixer fixer)
	{
		fixer.setChoiceSubject(((LDisconnect) child).src.toName());
	}
}
