package org.scribble.lang.global;

import org.scribble.job.Job;
import org.scribble.lang.SessType;
import org.scribble.type.kind.Global;

public interface GType
		extends SessType<Global>
{
	@Override
	GType getInlined(Job job);
}
