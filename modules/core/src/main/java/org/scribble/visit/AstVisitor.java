package org.scribble.visit;

import org.scribble.ast.ScribNode;
import org.scribble.main.Job;
import org.scribble.main.ScribbleException;

// Pattern: node accepts visitor and calls visitor back (standard visitor pattern -- adding a new operation doesn't affect the Ast classes), but then visitor delegates back to node delegate (so routines for handling each node type not centralised in visitor, but decentralised to delegates)
public abstract class AstVisitor
{
	public final Job job;  // Immutable except for JobContext internals

	//private ModuleContext mcontext;  // Factor up to ModelVisitor? (will be null before context building) -- maybe make a ModuleVisitor
	
	protected AstVisitor(Job job)
	{
		this.job = job;
	}

	public ScribNode visit(ScribNode parent, ScribNode child) throws ScribbleException
	{
		enter(parent, child);
		ScribNode visited = child.visitChildren(this);   // visited means "children visited so far"; we're about to visit "this" now via "leave"
		return leave(parent, child, visited);
	}
	
	protected void enter(ScribNode parent, ScribNode child) throws ScribbleException
	{

	}
	
	protected ScribNode leave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		return visited;
	}
}
