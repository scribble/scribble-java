package org.scribble.runtime.net.state;

import java.util.List;

import org.scribble.type.name.Op;
import org.scribble.type.name.Role;

// FIXME: integrate with ScribMessage (maybe rename latter to ScribRuntimeMessage)
public interface ScribHandlerMessage
{
	Role getPeer();
	Op getOp();
	List<Object> getPayload();
}
