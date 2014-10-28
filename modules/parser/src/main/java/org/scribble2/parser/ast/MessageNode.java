package org.scribble2.parser.ast;


// A sig kind node: MessageSignatureNode or ParameterNode
// MessageSignatureOrParameterNode (sig counterpart to PayloadTypeOrParameterNode)
public interface MessageNode extends ArgumentInstantiation
{
	// OperatorOrParameter
	//Operator getOperator();  // If a parameter, then the parameter name
	
	//Message toMessage();
}
