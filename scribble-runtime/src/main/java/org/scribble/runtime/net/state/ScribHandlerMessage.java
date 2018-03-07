package org.scribble.runtime.net.state;

import org.scribble.runtime.net.ScribMessage;
import org.scribble.type.name.Op;
import org.scribble.type.name.Role;

// FIXME: integrate with ScribMessage (maybe rename latter to ScribRuntimeMessage)
public abstract class ScribHandlerMessage extends ScribMessage
{
	private static final long serialVersionUID = 1L;
	
	public final Role peer;

	public ScribHandlerMessage(Role peer, Op op, Object... payload)
	{
		super(op, payload);
		this.peer = peer;
	}

	/*Role getPeer();
	Op getOp();
	List<Object> getPayload();*/
}
