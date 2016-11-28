package org.scribble.del.local;

import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LWrapClient;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ConnectionActionDel;
import org.scribble.main.ScribbleException;
import org.scribble.model.endpoint.actions.EWrapClient;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.context.EGraphBuilder;
import org.scribble.visit.context.ProjectedChoiceSubjectFixer;

public class LWrapClientDel extends ConnectionActionDel implements LSimpleInteractionNodeDel
{
	@Override
	public LWrapClient leaveEGraphBuilding(ScribNode parent, ScribNode child, EGraphBuilder graph, ScribNode visited) throws ScribbleException
	{
		LWrapClient lc = (LWrapClient) visited;
		RoleNode dest = lc.dest;
		Role peer = dest.toName();
		graph.util.addEdge(graph.util.getEntry(), new EWrapClient(peer), graph.util.getExit());
		return (LWrapClient) super.leaveEGraphBuilding(parent, child, graph, lc);
	}

	@Override
	public void enterProjectedChoiceSubjectFixing(ScribNode parent, ScribNode child, ProjectedChoiceSubjectFixer fixer)
	{
		fixer.setChoiceSubject(((LWrapClient) child).src.toName());
	}
}
