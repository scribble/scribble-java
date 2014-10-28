package org.scribble2.parser.ast.global;

import org.antlr.runtime.Token;
import org.scribble2.parser.ast.ParameterDeclList;
import org.scribble2.parser.ast.ProtocolHeader;
import org.scribble2.parser.ast.RoleDeclList;
import org.scribble2.parser.ast.name.simple.SimpleProtocolNameNode;

public class GlobalProtocolHeader extends ProtocolHeader implements GlobalNode
{
	public GlobalProtocolHeader(Token t, SimpleProtocolNameNode name, RoleDeclList roledecls, ParameterDeclList paramdecls)
	{
		super(t, name, roledecls, paramdecls);
	}
}
