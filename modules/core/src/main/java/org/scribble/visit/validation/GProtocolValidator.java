package org.scribble.visit.validation;

import org.scribble.ast.ScribNode;
import org.scribble.main.Job;
import org.scribble.main.ScribbleException;
import org.scribble.visit.context.ModuleContextVisitor;

public class GProtocolValidator extends ModuleContextVisitor
{
	public GProtocolValidator(Job job)
	{
		super(job);
	}

	@Override
	public void enter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.enter(parent, child);
		child.del().enterValidation(parent, child, this);
	}
	
	@Override
	public ScribNode leave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveValidation(parent, child, this, visited);
		return super.leave(parent, child, visited);
	}
}
