package org.scribble2.model;

import org.scribble2.sesstype.Argument;
import org.scribble2.sesstype.kind.Kind;


// Payload argument nodes (doesn't include role instantation arguments)
public interface ArgumentNode extends InstantiationNode  // FIXME: possible to take Kind parameter? with disambiguation?
{
	// FIXME: replace by kinds?
	boolean isMessageSignatureNode();
	boolean isPayloadTypeNode();
	boolean isParameterNode();
	//boolean isMessageSignatureNameNode();
	
	// FIXME: data type arguments aren't scoped
	//Argument<? extends Kind> toArgument(Scope scope);  // Argument shouldn't be a "type object"? (type objects should only be the name kinds? -- Argument is syntax category?) -- need an Argument syntax object for SubprotocolSignature
	Argument<? extends Kind> toArgument();
}
