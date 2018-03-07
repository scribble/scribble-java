package org.scribble.runtime.net.state;

import org.scribble.runtime.net.ScribMessage;

// FIXME: integrate with BranchSocket and Handlers
public interface ScribBranch
{
	public void dispatch(Object data, ScribMessage m);
}
