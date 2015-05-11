package org.scribble2.model;

import org.scribble2.sesstype.Message;
import org.scribble2.sesstype.name.Scope;


// A sig kind node: MessageSignatureNode or ParameterNode
// MessageSignatureOrParameterNode (sig counterpart to PayloadTypeOrParameterNode)
public interface MessageNode extends ArgumentNode
{
	// OperatorOrParameter
	//Operator getOperator();  // If a parameter, then the parameter name
	
	Message toMessage(Scope scope);
}
