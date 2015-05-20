package org.scribble2.model;

import org.scribble2.sesstype.Arg;
import org.scribble2.sesstype.kind.Kind;


// Payload argument nodes (doesn't include role instantation arguments)
//public interface ArgumentNode<K extends Kind> extends InstantiationNode  // FIXME: possible to take Kind parameter? with disambiguation?
// Not kinded: point of this interface is don't know which kind the node is -- so use the "is" methods -- cf. AmbigNameNode inherits both sig and data kind
public interface ArgNode extends DoArgNode
{
	boolean isMessageSigNode();
	boolean isMessageSigNameNode();
	boolean isDataTypeNameNode();
	boolean isParamNode();
	
	// FIXME: data type arguments aren't scoped
	//Argument<? extends Kind> toArgument(Scope scope);  // Argument shouldn't be a "type object"? (type objects should only be the name kinds? -- Argument is syntax category?) -- need an Argument syntax object for SubprotocolSignature
	Arg<? extends Kind> toArg();
	//Argument<K> toArgument();
}