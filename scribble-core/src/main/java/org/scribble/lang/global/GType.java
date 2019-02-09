package org.scribble.lang.global;

import java.util.Deque;

import org.scribble.job.Job;
import org.scribble.lang.SessType;
import org.scribble.type.SubprotoSig;
import org.scribble.type.kind.Global;

public interface GType extends SessType<Global>
{
	@Override
	GType getInlined(Job job, Deque<SubprotoSig> stack);
}
