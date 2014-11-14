package org.scribble2.model.name.simple;


// Simple member names are used only in the member declarations; the (full) MemberNameNodes represent the actual members
@Deprecated
public abstract class SimpleMemberNameNode extends SimpleNameNode
{
	public SimpleMemberNameNode(String name)
	{
		super(name);
	}
}
