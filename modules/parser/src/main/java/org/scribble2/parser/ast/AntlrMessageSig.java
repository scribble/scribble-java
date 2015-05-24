package org.scribble2.parser.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.MessageSigNode;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.PayloadElemList;
import org.scribble2.model.name.simple.OpNode;
import org.scribble2.parser.ScribbleParser;
import org.scribble2.parser.ast.name.AntlrSimpleName;

public class AntlrMessageSig
{
	public static final int OPERATOR_CHILD_INDEX = 0;
	public static final int PAYLOAD_CHILD_INDEX = 1;
	
	public static MessageSigNode parseMessageSig(ScribbleParser parser, CommonTree ct)
	{
		OpNode op = AntlrSimpleName.toOpNode(getOpChild(ct));
		PayloadElemList payload = (PayloadElemList) parser.parse(getPayloadElemListChild(ct));
		//return new MessageSignatureNode(op, payload);
		return ModelFactoryImpl.FACTORY.MessageSigNode(op, payload);
	}

	public static CommonTree getOpChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(OPERATOR_CHILD_INDEX);
	}
	
	public static CommonTree getPayloadElemListChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(PAYLOAD_CHILD_INDEX);
	}
	
	/*private static boolean hasEmptyOperator(CommonTree ct)
	{
		return AntlrSimpleName.getName(getOperatorChild(ct)).equals(EMPTY_OPERATOR);
	}*/
}
