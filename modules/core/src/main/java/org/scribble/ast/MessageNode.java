package org.scribble.ast;

import org.scribble.sesstype.Message;


// A sig kind node: MessageSignatureNode or ParameterNode
// MessageSignatureOrParameterNode (sig counterpart to PayloadTypeOrParameterNode)
public interface MessageNode extends NonRoleArgNode
{
	// OperatorOrParameter
	//Operator getOperator();  // If a parameter, then the parameter name
	
	//Message toMessage(Scope scope);
	Message toMessage();
}
