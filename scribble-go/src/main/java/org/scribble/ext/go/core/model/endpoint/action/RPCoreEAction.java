package org.scribble.ext.go.core.model.endpoint.action;

import org.scribble.ext.go.core.type.RPIndexedRole;

public interface RPCoreEAction 
{
	
	RPIndexedRole getPeer();  // N.B. *not* ParamActualRole
}
