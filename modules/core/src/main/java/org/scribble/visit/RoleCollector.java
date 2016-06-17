package org.scribble.visit;

import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.context.ModuleContext;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.name.Role;

// Second pass for ProtocolDeclContext building
// Maybe generalise to integrate enabled message collection?
// NOTE: doesn't collect from choice subjects
public class RoleCollector extends NameCollector<Role>
{
	public RoleCollector(Job job)
	{
		super(job);
	}

	public RoleCollector(Job job, ModuleContext mcontext)
	{
		super(job, mcontext);
	}

	@Override
	protected final void offsetSubprotocolEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.offsetSubprotocolEnter(parent, child);
		if (child instanceof ProtocolDecl<?>)
		{
			super.clear();
		}
		child.del().enterRoleCollection(parent, child, this);
	}
	
	@Override
	protected ScribNode offsetSubprotocolLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveRoleCollection(parent, child, this, visited);
		return super.offsetSubprotocolLeave(parent, child, visited);
	}
}
