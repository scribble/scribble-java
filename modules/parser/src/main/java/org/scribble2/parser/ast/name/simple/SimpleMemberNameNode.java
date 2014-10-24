package org.scribble2.parser.ast.name.simple;

import org.antlr.runtime.Token;

// Simple member names are used only in the member declarations; the (full) MemberNameNodes represent the actual members
public abstract class SimpleMemberNameNode extends SimpleNameNode
{
	public SimpleMemberNameNode(Token t, String name)
	{
		super(t, name);
	}
}
