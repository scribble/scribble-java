package org.scribble2.foo.ast.name.simple;

import org.antlr.runtime.Token;

public class RoleNode extends SimpleNameNode
{
	public RoleNode(Token ct, String name)
	{
		super(ct, name);
	}
	
	/*@Override
	public RoleNode substitute(Substitutor subs)
	{
		return subs.getRoleSubstitution(toName());
	}
	
	@Override
	public Role toName()
	{
		return new Role(this.identifier);
	}*/
}
