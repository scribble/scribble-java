package org.scribble2.foo.ast.global;

import org.antlr.runtime.Token;
import org.scribble2.foo.ast.ParameterDeclList;
import org.scribble2.foo.ast.ProtocolHeader;
import org.scribble2.foo.ast.RoleDeclList;
import org.scribble2.foo.ast.name.simple.SimpleProtocolNameNode;

public class GlobalProtocolHeader extends ProtocolHeader implements GlobalNode
{
	public GlobalProtocolHeader(Token t)//, SimpleProtocolNameNode name, RoleDeclList roledecls, ParameterDeclList paramdecls)
	{
		super(t);//, name, roledecls, paramdecls);
	}
}
