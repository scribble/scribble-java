package org.scribble.runtime.net.handlers;

import org.scribble.runtime.net.ScribMessage;
import org.scribble.type.name.Op;
import org.scribble.type.name.Role;

// FIXME: integrate with ScribMessage (maybe rename latter to ScribRuntimeMessage -- or rename this to ScribDirectedMessage)
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
