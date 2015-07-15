package org.scribble.visit;

import org.scribble.ast.ScribNode;
import org.scribble.ast.context.ModuleContext;
import org.scribble.ast.context.global.GDependencyMap;
import org.scribble.ast.context.local.LDependencyMap;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.LProtocolName;
import org.scribble.sesstype.name.Role;

// Disambiguates ambiguous PayloadTypeOrParameter names and inserts implicit Scope names
public class ContextBuilder extends AstVisitor
{
	private GDependencyMap gdeps;
	private LDependencyMap ldeps;

	private ModuleContext mcontext;  // The "root" Module context (not the "main" module)
	
	public ContextBuilder(Job job)
	{
		super(job);
	}

	@Override
	protected void enter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		child.del().enterContextBuilding(parent, child, this);
	}

	@Override
	protected ScribNode leave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		return visited.del().leaveContextBuilding(parent, child, this, visited);
	}
	
	public void setModuleContext(ModuleContext mcontext)
	{
		this.mcontext = mcontext;
	}
	
	public ModuleContext getModuleContext()
	{
		return this.mcontext;
	}
	
	public void clearProtocolDependencies()
	{
		this.gdeps = new GDependencyMap();
		this.ldeps = new LDependencyMap();
	}

	public void addGlobalProtocolDependency(Role self, GProtocolName gpn, Role role)
	{
		this.gdeps.addProtocolDependency(self, gpn, role);
	}

	public void addLocalProtocolDependency(Role self, LProtocolName lpn, Role role)
	{
		this.ldeps.addProtocolDependency(self, lpn, role);
	}

	public GDependencyMap getGlobalProtocolDependencyMap()
	{
		return this.gdeps;
	}

	public LDependencyMap getLocalProtocolDependencyMap()
	{
		return this.ldeps;
	}
}
