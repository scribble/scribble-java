package scribble2.ast;

import scribble2.sesstype.Message;


// A sig kind node: MessageSignatureNode or ParameterNode
// MessageSignatureOrParameterNode (sig counterpart to PayloadTypeOrParameterNode)
public interface MessageNode extends ArgumentNode
{
	// OperatorOrParameter
	//Operator getOperator();  // If a parameter, then the parameter name
	
	Message toMessage();
}
