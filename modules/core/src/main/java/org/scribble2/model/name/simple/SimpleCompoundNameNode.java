package org.scribble2.model.name.simple;

import org.scribble2.sesstype.name.Name;
import org.scribble2.sesstype.name.SimpleName;


// FIXME: refactor better, and make uniform with type names
// Simple member names are used only in the member declarations; the (full) MemberNameNodes represent the actual members
@Deprecated
public abstract class SimpleCompoundNameNode<T extends Name> extends SimpleNameNode<SimpleName>
{
	protected SimpleCompoundNameNode(String name)
	{
		super(name);
	}
	
	public abstract T toCompoundName();
}
