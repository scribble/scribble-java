package org.scribble.runtime.handlers;

import org.scribble.type.name.Op;
import org.scribble.type.name.Role;

// FIXME: make interface and add to bounds of output icallback -- cf. CBEndpointApiGenerator
// FIXME: not serializable due to RoleKind (in Role)
public interface ScribOutputEvent
{
	Role getPeer();
	Op getOp();
	Object[] getPayload();
}
