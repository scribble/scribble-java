package org.scribble.del.local;

import org.scribble.ast.Module;
import org.scribble.ast.ScribNode;
import org.scribble.ast.context.local.LProtocolDeclContext;
import org.scribble.ast.local.LProtocolDecl;
import org.scribble.del.ProtocolDeclDel;
import org.scribble.main.ScribbleException;
import org.scribble.model.local.EndpointGraph;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.name.LProtocolName;
import org.scribble.sesstype.name.ProtocolName;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.EndpointGraphBuilder;
import org.scribble.visit.JobContext;
import org.scribble.visit.ProtocolDeclContextBuilder;

public class LProtocolDeclDel extends ProtocolDeclDel<Local>
{
	public LProtocolDeclDel()
	{

	}
	
	@Override
	protected LProtocolDeclDel copy()
	{
		return new LProtocolDeclDel();
	}

	@Override
	protected void addSelfDependency(ProtocolDeclContextBuilder builder, ProtocolName<?> proto, Role role)
	{
		builder.addLocalProtocolDependency(role, (LProtocolName) proto, role);
	}
	
	@Override
	public void enterProtocolDeclContextBuilding(ScribNode parent, ScribNode child, ProtocolDeclContextBuilder builder) throws ScribbleException
	{
		super.enterProtocolDeclContextBuilding(parent, child, builder);
	}

	@Override
	public LProtocolDecl leaveProtocolDeclContextBuilding(ScribNode parent, ScribNode child, ProtocolDeclContextBuilder builder, ScribNode visited) throws ScribbleException
	{
		LProtocolDecl lpd = (LProtocolDecl) visited;
		LProtocolDeclContext lcontext = new LProtocolDeclContext(builder.getLocalProtocolDependencyMap());
		LProtocolDeclDel del = (LProtocolDeclDel) setProtocolDeclContext(lcontext);
		return (LProtocolDecl) lpd.del(del);
	}

	@Override
	public void enterGraphBuilding(ScribNode parent, ScribNode child, EndpointGraphBuilder graph)
	{
		graph.builder.reset();
	}

	@Override
	public ScribNode leaveGraphBuilding(ScribNode parent, ScribNode child, EndpointGraphBuilder graph, ScribNode visited)
	{
		LProtocolDecl lpd = (LProtocolDecl) visited;
		EndpointGraph fsm = new EndpointGraph(graph.builder.getEntry(), graph.builder.getExit());
		JobContext jc = graph.getJobContext();
		jc.addEndpointGraph(lpd.getFullMemberName((Module) parent), fsm);
		return visited;
	}
	
	@Override
	public LProtocolDeclContext getProtocolDeclContext()
	{
		return (LProtocolDeclContext) super.getProtocolDeclContext();
	}
}
