package org.scribble.visit;

import java.util.HashSet;
import java.util.Set;

import org.scribble.ast.context.ModuleContext;
import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.name.Name;

public abstract class NameCollector<N extends Name<? extends Kind>> extends NoEnvOffsetSubprotocolVisitor
{
	private Set<N> names = new HashSet<>();
	
	// Takes ModuleContext, so no need to visit from Module as root
	public NameCollector(Job job, ModuleContext mcontext)
	{
		super(job);
		setModuleContext(mcontext);
	}
	
	public void addName(N mid)
	{
		this.names.add(mid);
	}
	
	public Set<N> getNames()
	{
		return this.names;
	}
}
