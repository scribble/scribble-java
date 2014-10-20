package scribble2.ast.name;

import scribble2.ast.ArgumentNode;
import scribble2.sesstype.name.PayloadTypeOrParameter;

// A type kind node: PayloadTypeNode or ParameterNode
// (type counterpart to MessageNode)
public interface PayloadTypeOrParameterNode extends ArgumentNode
{
	PayloadTypeOrParameter toPayloadTypeOrParameter();
}
