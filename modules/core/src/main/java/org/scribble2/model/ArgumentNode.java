package org.scribble2.model;

import org.scribble2.sesstype.Argument;

// Payload argument nodes (doesn't include role instantation arguments)
public interface ArgumentNode extends InstantiationNode
{
	boolean isMessageSignatureNode();
	boolean isPayloadTypeNode();
	boolean isParameterNode();
	//boolean isMessageSignatureNameNode();
	
	Argument toArgument();  // Argument shouldn't be a "type object"? (type objects should only be the name kinds? -- Argument is syntax category?) -- need an Argument syntax object for SubprotocolSignature
}
