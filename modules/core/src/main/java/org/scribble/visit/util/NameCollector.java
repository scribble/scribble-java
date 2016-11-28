package org.scribble.visit.util;

import java.util.HashSet;
import java.util.Set;

import org.scribble.ast.context.ModuleContext;
import org.scribble.main.Job;
import org.scribble.sesstype.name.Name;
import org.scribble.visit.NoEnvOffsetSubprotocolVisitor;

// Make SubprotocolVisitor? -- should be at least SubprotocolVisitor for get all occurrences up to recursive subprotocols (e.g. role occurrences for protocoldeclcontext)
public abstract class NameCollector<N extends Name<?>> extends NoEnvOffsetSubprotocolVisitor
{
	private Set<N> names = new HashSet<>();
	
	public NameCollector(Job job)
	{
		super(job);
	}

	// Can takes ModuleContext here, so no need to visit from Module as root -- maybe factor this facility out
	// though has to enter at least at protocoldecl for subprotocol visitor root sig pushing
	public NameCollector(Job job, ModuleContext mcontext)
	{
		super(job);
		setModuleContext(mcontext);
	}
	
	public void addName(N name)
	{
		this.names.add(name);
	}
	
	public Set<N> getNames()
	{
		return this.names;
	}
	
	public void clear()
	{
		this.names.clear();
	}
}
