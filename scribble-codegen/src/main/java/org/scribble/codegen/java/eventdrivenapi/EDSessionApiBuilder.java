package org.scribble.codegen.java.eventdrivenapi;

import org.scribble.codegen.java.sessionapi.SessionApiGenerator;
import org.scribble.main.Job;
import org.scribble.main.ScribbleException;
import org.scribble.type.name.GProtocolName;

// FIXME: deprecate -- same as SessionApiGenerator, just use that directly
public class EDSessionApiBuilder extends SessionApiGenerator
{
	public EDSessionApiBuilder(Job job, GProtocolName fullname) throws ScribbleException
	{
		super(job, fullname);
	}
	
	/*public Map<String, String> build()  // FIXME: factor out
	{
		return Collections.emptyMap();
	}*/
}
