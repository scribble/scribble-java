package org.scribble2.model.name;

import org.scribble2.sesstype.name.PayloadTypeOrParameter;


// A type kind node: PayloadTypeNode or ParameterNode -- not necessarily simple nor qualified
// (type counterpart to MessageNode)
public interface PayloadElementNameNode //extends ArgumentNode
{
	PayloadTypeOrParameter toPayloadTypeOrParameter();
}