package org.scribble.parser.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.MessageSigNode;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.PayloadElemList;
import org.scribble.ast.name.simple.OpNode;
import org.scribble.parser.ScribParser;
import org.scribble.parser.ast.name.AntlrSimpleName;
import org.scribble.util.ScribParserException;

public class AntlrMessageSig
{
	public static final int OPERATOR_CHILD_INDEX = 0;
	public static final int PAYLOAD_CHILD_INDEX = 1;
	
	public static MessageSigNode parseMessageSig(ScribParser parser, CommonTree ct) throws ScribParserException
	{
		OpNode op = AntlrSimpleName.toOpNode(getOpChild(ct));
		PayloadElemList payload = (PayloadElemList) parser.parse(getPayloadElemListChild(ct));
		return AstFactoryImpl.FACTORY.MessageSigNode(op, payload);
	}

	public static CommonTree getOpChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(OPERATOR_CHILD_INDEX);
	}
	
	public static CommonTree getPayloadElemListChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(PAYLOAD_CHILD_INDEX);
	}
}
