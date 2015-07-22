package org.scribble.visit;

import org.scribble.ast.ScribNode;
import org.scribble.ast.context.global.GDependencyMap;
import org.scribble.ast.context.local.LDependencyMap;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.LProtocolName;
import org.scribble.sesstype.name.Role;

// Disambiguates ambiguous PayloadTypeOrParameter names and inserts implicit Scope names
public class ProtocolDeclContextBuilder extends ModuleContextVisitor
{
	private GDependencyMap gdeps;
	private LDependencyMap ldeps;
	
	public ProtocolDeclContextBuilder(Job job)
	{
		super(job);
	}

	@Override
	protected void enter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.enter(parent, child);
		child.del().enterProtocolDeclContextBuilding(parent, child, this);
	}

	@Override
	protected ScribNode leave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveProtocolDeclContextBuilding(parent, child, this, visited);
		return super.leave(parent, child, visited);
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
