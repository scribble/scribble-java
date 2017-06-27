/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.scribble.del.local;

import org.scribble.ast.ScribNode;
import org.scribble.ast.context.local.LProtocolDeclContext;
import org.scribble.ast.local.LProtocolDecl;
import org.scribble.del.ProtocolDeclDel;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.name.LProtocolName;
import org.scribble.sesstype.name.ProtocolName;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.context.EGraphBuilder;
import org.scribble.visit.context.ProtocolDeclContextBuilder;

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
	public void enterEGraphBuilding(ScribNode parent, ScribNode child, EGraphBuilder graph)
	{
		graph.util.reset();  // Same util is used for multiple protos, need to reset
	}

	@Override
	public ScribNode leaveEGraphBuilding(ScribNode parent, ScribNode child, EGraphBuilder graph, ScribNode visited)
	{
		/*LProtocolDecl lpd = (LProtocolDecl) visited;  // Refactored into JobContext
		
		JobContext jc = graph.getJobContext();  
		// FIXME: should just bypass builder visit if already built
		LProtocolName lpn = lpd.getFullMemberName((Module) parent);
		if (jc.getEndpointGraph(lpn) == null)  // FIXME: what is the routine to obtain gpn from lpn?
		{
			//EndpointGraph fsm = new EndpointGraph(graph.builder.getEntry(), graph.builder.getExit());
			EndpointGraph graph = graph.builder.finalise();

			jc.addEndpointGraph(lpn, graph);
		}*/
		return visited;
	}
	
	@Override
	public LProtocolDeclContext getProtocolDeclContext()
	{
		return (LProtocolDeclContext) super.getProtocolDeclContext();
	}
}
