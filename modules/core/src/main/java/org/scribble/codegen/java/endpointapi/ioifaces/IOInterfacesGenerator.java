package org.scribble.codegen.java.endpointapi.ioifaces;

import org.scribble.codegen.java.endpointapi.ApiGenerator;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.visit.Job;

public abstract class IOInterfacesGenerator extends ApiGenerator
{
	public IOInterfacesGenerator(Job job, GProtocolName fullname)
	{
		super(job, fullname);
	}

}
