package org.scribble.lang;

import org.scribble.job.Job;
import org.scribble.type.name.Role;

public class Projector
{
	public final Job job;
	public final Role self;

	public Projector(Job job, Role self)
	{
		this.job = job;
		this.self = self;
	}
}
