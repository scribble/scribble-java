package org.scribble.ast;

import org.scribble.sesstype.Arg;
import org.scribble.sesstype.kind.Kind;


// sig or payloadtype kinds that can be used as do arg vals, cf. RoleNode
// "Value nodes" (sigs or names) that can be used as non-role subprotocol arguments (doesn't include role instantation arguments)
// N.B. not the actual argument node itself (that is NonRoleArg, element of NonRoleArgList, which wraps these nodes)
//public interface ArgumentNode<K extends Kind> extends InstantiationNode  // FIXME: possible to take Kind parameter? with disambiguation?
public interface NonRoleArgNode extends DoArgNode
{
	// Not kinded: point of this interface is don't know which kind the node is -- so use the "is" methods -- cf. AmbigNameNode inherits both sig and data kind
	// And not all values are names, e.g. message sigs
	boolean isMessageSigNode();
	boolean isMessageSigNameNode();
	boolean isDataTypeNameNode();
	boolean isParamNode();
	
	// FIXME: data type arguments aren't scoped
	//Argument<? extends Kind> toArgument(Scope scope);  // Argument shouldn't be a "type object"? (type objects should only be the name kinds? -- Argument is syntax category?) -- need an Argument syntax object for SubprotocolSignature
	Arg<? extends Kind> toArg();
	//Argument<K> toArgument();
}
