package org.scribble.runtime.handlers;

import org.scribble.runtime.message.ScribMessage;

// FIXME: integrate with BranchSocket and Handler I/f's
public interface ScribBranch<D>
{
	public void dispatch(D data, ScribMessage m);
}
