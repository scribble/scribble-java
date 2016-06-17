package org.scribble.del.global;

import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GContinue;
import org.scribble.ast.global.GNode;
import org.scribble.ast.local.LContinue;
import org.scribble.del.ContinueDel;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.Projector;

public class GContinueDel extends ContinueDel implements GSimpleInteractionNodeDel
{
	public LContinue project(GNode n, Role self)
	{
		GContinue gc = (GContinue) n;
		LContinue projection = gc.project(self);
		return projection;
	}

	@Override
	public GContinue leaveProjection(ScribNode parent, ScribNode child, Projector proj, ScribNode visited) throws ScribbleException
	{
		GContinue gc = (GContinue) visited;
		LContinue projection = project(gc, proj.peekSelf());
		proj.pushEnv(proj.popEnv().setProjection(projection));
		return (GContinue) GSimpleInteractionNodeDel.super.leaveProjection(parent, child, proj, gc);
	}
	
	/*@Override
	public ScribNode leaveWFChoicePathCheck(ScribNode parent, ScribNode child, WFChoicePathChecker coll, ScribNode visited) throws ScribbleException
	//public ScribNode leavePathCollection(ScribNode parent, ScribNode child, PathCollectionVisitor<? extends PathEnv> coll, ScribNode visited) throws ScribbleException
	//public ScribNode leavePathCollection(ScribNode parent, ScribNode child, PathCollectionVisitor coll, ScribNode visited) throws ScribbleException
	{
		GContinue gc = (GContinue) visited;
		RecVar rv = gc.recvar.toName();
		coll.pushEnv(coll.popEnv().append(rv));
		return visited;
	}*/

	/*@Override
	public GContinue leaveModelBuilding(ScribNode parent, ScribNode child, GlobalModelBuilder graph, ScribNode visited) throws ScribbleException
	{
		/*GContinue gr = (GContinue) visited;
		RecVar rv = gr.recvar.toName();
		//graph.builder.setEntry(graph.builder.getRecursionEntry(rv));
		//if (graph.builder.getPredecessor() == null)  // unguarded choice case
		if (graph.builder.isUnguardedInChoice())
		{
			GModelAction a = graph.builder.getEnacting(rv);
			graph.builder.addEdge(graph.builder.getEntry(), a, graph.builder.getRecursionEntry(rv).accept(a));
		}
		else
		{
			graph.builder.addEdge(graph.builder.getPredecessors(), graph.builder.getPreviousActions(), graph.builder.getRecursionEntry(rv));
		}
		return (GContinue) super.leaveModelBuilding(parent, child, graph, gr);
	}*/
}
