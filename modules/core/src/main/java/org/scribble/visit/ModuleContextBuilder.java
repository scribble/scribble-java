package org.scribble.visit;

import org.scribble.ast.ScribNode;
import org.scribble.ast.context.ModuleContext;
import org.scribble.main.ScribbleException;

// Disambiguates ambiguous PayloadTypeOrParameter names and inserts implicit Scope names
public class ModuleContextBuilder extends AstVisitor
{
	private ModuleContext mcontext;  // The "root" Module context (not the "main" module)
	
	public ModuleContextBuilder(Job job)
	{
		super(job);
	}

	@Override
	protected void enter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		child.del().enterModuleContextBuilding(parent, child, this);
	}

	@Override
	protected ScribNode leave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		return visited.del().leaveModuleContextBuilding(parent, child, this, visited);
	}
	
	public void setModuleContext(ModuleContext mcontext)
	{
		this.mcontext = mcontext;
	}
	
	public ModuleContext getModuleContext()
	{
		return this.mcontext;
	}
}
