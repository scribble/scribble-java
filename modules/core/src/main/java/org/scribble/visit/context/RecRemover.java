package org.scribble.visit.context;

import java.util.HashSet;
import java.util.Set;

import org.scribble.ast.ScribNode;
import org.scribble.main.Job;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.name.RecVar;
import org.scribble.visit.NoEnvInlinedProtocolVisitor;

// FIXME: shadowed recvars
public class RecRemover extends NoEnvInlinedProtocolVisitor
{
	private final Set<RecVar> rvs = new HashSet<>();

	public RecRemover(Job job)
	{
		super(job);
	}
	
	public void setToRemove(Set<RecVar> rv)
	{
		this.rvs.clear();
		this.rvs.addAll(rv);
	}
	
	public boolean toRemove(RecVar rv)
	{
		return this.rvs.contains(rv);
	}
	
	public void remove(RecVar rv)
	{
		this.rvs.remove(rv);
	}

	@Override
	public void inlinedEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.inlinedEnter(parent, child);
		child.del().enterRecRemoval(parent, child, this);
	}
	
	@Override
	public ScribNode inlinedLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveRecRemoval(parent, child, this, visited);
		return super.inlinedLeave(parent, child, visited);
	}
}
