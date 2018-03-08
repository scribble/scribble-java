package org.scribble.runtime.net.handlers;

import org.scribble.runtime.net.ScribMessage;

// FIXME: integrate with BranchSocket and Handlers
public interface ScribBranch<D>
{
	public void dispatch(D data, ScribMessage m);
}
