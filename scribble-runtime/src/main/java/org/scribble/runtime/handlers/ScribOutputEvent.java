package org.scribble.runtime.handlers;

import org.scribble.runtime.message.ScribMessage;
import org.scribble.type.name.Op;
import org.scribble.type.name.Role;

// FIXME: make interface and add to bounds of output icallback -- cf. CBEndpointApiGenerator
// FIXME: not serializable due to RoleKind (in Role)
public abstract class ScribOutputEvent extends ScribMessage
{
	private static final long serialVersionUID = 1L;
	
	public final Role peer;

	public ScribOutputEvent(Role peer, Op op, Object... payload)
	{
		super(op, payload);
		this.peer = peer;
	}

	/*Role getPeer();
	Op getOp();
	List<Object> getPayload();*/
}
