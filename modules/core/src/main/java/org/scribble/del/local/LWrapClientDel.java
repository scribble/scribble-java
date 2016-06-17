package org.scribble.del.local;

import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LWrapClient;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ConnectionActionDel;
import org.scribble.main.ScribbleException;
import org.scribble.model.local.WrapClient;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.EndpointGraphBuilder;
import org.scribble.visit.ProjectedChoiceSubjectFixer;

public class LWrapClientDel extends ConnectionActionDel implements LSimpleInteractionNodeDel
{
	@Override
	public LWrapClient leaveEndpointGraphBuilding(ScribNode parent, ScribNode child, EndpointGraphBuilder graph, ScribNode visited) throws ScribbleException
	{
		LWrapClient lc = (LWrapClient) visited;
		RoleNode dest = lc.dest;
		Role peer = dest.toName();
		graph.builder.addEdge(graph.builder.getEntry(), new WrapClient(peer), graph.builder.getExit());
		return (LWrapClient) super.leaveEndpointGraphBuilding(parent, child, graph, lc);
	}

	@Override
	public void enterProjectedChoiceSubjectFixing(ScribNode parent, ScribNode child, ProjectedChoiceSubjectFixer fixer)
	{
		fixer.setChoiceSubject(((LWrapClient) child).src.toName());
	}
}
