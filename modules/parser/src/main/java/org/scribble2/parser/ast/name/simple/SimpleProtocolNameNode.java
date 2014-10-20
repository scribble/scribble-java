package org.scribble2.parser.ast.name.simple;

import org.antlr.runtime.Token;

public class SimpleProtocolNameNode extends SimpleNameNode //SimpleMemberNameNode
{
	public SimpleProtocolNameNode(Token t, String name)
	{
		super(t, name);
	}

	/*@Override
	public ProtocolName toName()
	{
		return new ProtocolName(this.identifier);
	}*/

	/*@Override
	public PrimitiveNameNode toPrimitiveNameNode()
	{
		return (PrimitiveNameNode) this;
	}*/
}
