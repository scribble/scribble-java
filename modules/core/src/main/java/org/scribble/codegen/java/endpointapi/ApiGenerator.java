package org.scribble.codegen.java.endpointapi;

import java.util.Map;

import org.scribble.sesstype.name.GProtocolName;
import org.scribble.visit.Job;

public abstract class ApiGenerator
{
	protected final Job job;
	protected final GProtocolName gpn;  // full name

	public ApiGenerator(Job job, GProtocolName fullname)
	{
		this.job = job;
		this.gpn = fullname;
	}
	
	// Return: key (package and Java class file path) -> val (Java class source) 
	public abstract Map<String, String> generate();
}
