package org.scribble2.parser.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.MessageSignatureNode;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.Payload;
import org.scribble2.model.name.simple.OperatorNode;
import org.scribble2.parser.AntlrModuleParser;
import org.scribble2.parser.ast.name.AntlrSimpleName;

public class AntlrMessageSignature
{
	public static final int OPERATOR_CHILD_INDEX = 0;
	public static final int PAYLOAD_CHILD_INDEX = 1;
	
	public static MessageSignatureNode parseMessageSignature(AntlrModuleParser parser, CommonTree ct)
	{
		OperatorNode op = AntlrSimpleName.toOperatorNode(getOperatorChild(ct));
		Payload payload = (Payload) parser.parse(getPayloadChild(ct));
		//return new MessageSignatureNode(op, payload);
		return ModelFactoryImpl.FACTORY.MessageSignatureNode(op, payload);
	}

	public static CommonTree getOperatorChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(OPERATOR_CHILD_INDEX);
	}
	
	public static CommonTree getPayloadChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(PAYLOAD_CHILD_INDEX);
	}
	
	/*private static boolean hasEmptyOperator(CommonTree ct)
	{
		return AntlrSimpleName.getName(getOperatorChild(ct)).equals(EMPTY_OPERATOR);
	}*/
}
