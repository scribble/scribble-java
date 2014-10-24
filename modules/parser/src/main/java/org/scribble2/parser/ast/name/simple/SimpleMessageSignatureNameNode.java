package org.scribble2.parser.ast.name.simple;

import org.antlr.runtime.Token;

public class SimpleMessageSignatureNameNode extends SimpleNameNode//SimpleMemberNameNode
{
	//public final String extType;  // Not current considered for equals/hashCode

	//public SimplePayloadTypeNode(CommonTree ct, String name, String extType)
	public SimpleMessageSignatureNameNode(Token t, String name)
	{
		super(t, name);
		//this.extType = extType;
	}
	
	/*@Override
	public MessageSignatureName toName()
	{
		return new MessageSignatureName(this.identifier);
	}*/
}
