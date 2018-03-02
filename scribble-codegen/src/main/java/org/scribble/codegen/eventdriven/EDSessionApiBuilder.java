package org.scribble.codegen.eventdriven;

import org.scribble.codegen.java.endpointapi.SessionApiGenerator;
import org.scribble.main.Job;
import org.scribble.main.ScribbleException;
import org.scribble.type.name.GProtocolName;

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
