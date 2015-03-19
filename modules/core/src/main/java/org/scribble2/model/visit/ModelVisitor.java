package org.scribble2.model.visit;

import org.scribble2.model.ModelNode;
import org.scribble2.util.ScribbleException;

public class ModelVisitor
{
	private final Job job;
	
	public ModelVisitor(Job job)
	{
		this.job = job;
		
		//...context visitChildren...

		//...  name disambiguation, name kind nodes, ... del context/env, def factory, visitors: subprotocol/context/env, ..ModelVisitor...
		
	}

	// Visit the child under the context of parent
	public ModelNode visit(ModelNode parent, ModelNode child) throws ScribbleException
	{
		ModelVisitor nv = enter(parent, child);
		ModelNode visited = child.visitChildren(nv);  // visited means "children visited so far"; we're about to visit "this" now via "leave"
		return leave(parent, child, nv, visited);
	}
	
	protected ModelVisitor enter(ModelNode parent, ModelNode child) throws ScribbleException
	{
		return this;
	}
	
	protected ModelNode leave(ModelNode parent, ModelNode child, ModelVisitor nv, ModelNode visited) throws ScribbleException
	{
		return visited;
	}
	
	public Job getJob()
	{
		return this.job;
	}

	public JobContext getJobContext()
	{
		return this.job.getContext();
	}
	
	/*// Requires visited ModelNode to be of the same class as the original ModelNode
	// Not suitable for general Visitor pattern: as well as the strict class check (so no substitutability), overriding is not convenient
	// However, this is convenient for visiting generic ModelNodes (cast would be unchecked) -- so those ModelNodes (e.g. ProtocolBlock -- done via visitAll for Choice/Parallel) must keep the same class
	// So this method is basically for the generic AST ModelNodes (ProtocolDecl/Def/Block, InteractionSequence)
	public <T extends ModelNode> T genericVisitWithClassCheck(T n) throws ScribbleException
	{
		ModelNode visited = visit(n);
		if (visited.getClass() != n.getClass())  // Visitor is not allowed to replace the ModelNode by a different ModelNode type
		{
			throw new RuntimeException("Visitor generic visit error: " + n + ", " + visited);
		}
		@SuppressWarnings("unchecked")
		T t = (T) visited;
		return t;
	}
	
	// Requires all visited ModelNodes to be of the same class as the original ModelNodes
	//public <T extends InteractionModelNode> List<T> visitAll(List<T> ModelNodes) throws ScribbleException
	public <T extends ModelNode> List<T> genericVisitAllWithClassCheck(List<T> ModelNodes) throws ScribbleException
	{
		List<T> visited = new LinkedList<>();
		for (T n : ModelNodes)
		{
			visited.add(genericVisitWithClassCheck(n));
		}
		return visited;
	}*/
	
	/*protected ModelVisitor copy()
	{
		return this;
	}*/
}
