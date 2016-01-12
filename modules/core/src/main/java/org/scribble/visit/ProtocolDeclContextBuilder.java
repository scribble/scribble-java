package org.scribble.visit;

import org.scribble.ast.ScribNode;
import org.scribble.ast.context.global.GDependencyMap;
import org.scribble.ast.context.local.LDependencyMap;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.LProtocolName;
import org.scribble.sesstype.name.Role;

// Disambiguates ambiguous PayloadTypeOrParameter names and inserts implicit Scope names -- no: old?
public class ProtocolDeclContextBuilder extends NoEnvSubprotocolVisitor  // For transitive dependency collection (cf. NameCollector)
{
	private GDependencyMap gdeps;
	private LDependencyMap ldeps;
	
	public ProtocolDeclContextBuilder(Job job)
	{
		super(job);
	}

	@Override
	protected void subprotocolEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.subprotocolEnter(parent, child);
		child.del().enterProtocolDeclContextBuilding(parent, child, this);
	}

	@Override
	protected ScribNode subprotocolLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveProtocolDeclContextBuilding(parent, child, this, visited);
		return super.subprotocolLeave(parent, child, visited);
	}
	
	public void clearProtocolDependencies()
	{
		this.gdeps = new GDependencyMap();
		this.ldeps = new LDependencyMap();
	}

	public void addGlobalProtocolDependency(Role self, GProtocolName gpn, Role target)
	{
		this.gdeps.addProtocolDependency(self, gpn, target);
	}

	public void addLocalProtocolDependency(Role self, LProtocolName lpn, Role target)
	{
		this.ldeps.addProtocolDependency(self, lpn, target);
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
