package org.scribble.ast;

import org.scribble.sesstype.Message;

// A sig kind node: MessageSignatureNode, MessageSignatureNameNode or NonRoleParamNode
public interface MessageNode extends NonRoleArgNode
{
	Message toMessage();
	
	MessageNode clone();

	//Arg<? extends SigKind> toArg();  // Not possible due to "diamond" with PayloadElemNameNode at AmbigNameNode
}
