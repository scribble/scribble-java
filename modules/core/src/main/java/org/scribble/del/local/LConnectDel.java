package org.scribble.del.local;

import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LConnect;
import org.scribble.del.MessageTransferDel;
import org.scribble.main.ScribbleException;
import org.scribble.visit.EndpointGraphBuilder;
import org.scribble.visit.ProjectedChoiceSubjectFixer;

public class LConnectDel extends MessageTransferDel implements LSimpleInteractionNodeDel
{
	@Override
	public LConnect leaveEndpointGraphBuilding(ScribNode parent, ScribNode child, EndpointGraphBuilder graph, ScribNode visited) throws ScribbleException
	{
		/*LConnect ls = (LConnect) visited;
		RoleNode dest = ls.dest;
		Role peer = dest.toName();
		graph.builder.addEdge(graph.builder.getEntry(), new Send(peer, mid, payload), graph.builder.getExit());
		//builder.builder.addEdge(builder.builder.getEntry(), Send.get(peer, mid, payload), builder.builder.getExit());
		return (LSend) super.leaveEndpointGraphBuilding(parent, child, graph, ls);*/
		throw new RuntimeException("TODO: " + visited);
	}

	// Could make a LMessageTransferDel to factor this out with LReceiveDel
	@Override
	public void enterProjectedChoiceSubjectFixing(ScribNode parent, ScribNode child, ProjectedChoiceSubjectFixer fixer)
	{
		//fixer.setChoiceSubject(((LSend) child).src.toName());
		throw new RuntimeException("TODO: " + child);
	}
}
