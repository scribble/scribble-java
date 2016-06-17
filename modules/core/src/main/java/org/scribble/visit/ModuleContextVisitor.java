package org.scribble.visit;

import org.scribble.ast.Module;
import org.scribble.ast.ScribNode;
import org.scribble.ast.context.ModuleContext;
import org.scribble.del.ModuleDel;
import org.scribble.main.ScribbleException;


// Maybe ModuleContextVisitor
public abstract class ModuleContextVisitor extends AstVisitor
{
	private ModuleContext mcontext;  // The "root" module context (different than the front-end "main" module)  // Factor up to ModelVisitor? (will be null before context building)

	public ModuleContextVisitor(Job job)
	{
		super(job);
	}

	@Override
	protected void enter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.enter(parent, child);
		if (child instanceof Module)  // Factor out?
		{
			setModuleContext(((ModuleDel) ((Module) child).del()).getModuleContext());
		}
	}

	public ModuleContext getModuleContext()
	{
		return this.mcontext;
	}
	
	// Factor out -- e.g. some Visitors want to root on ProtocolDecl, not Module
	protected void setModuleContext(ModuleContext mcontext)
	{
		this.mcontext = mcontext;
	}
}
