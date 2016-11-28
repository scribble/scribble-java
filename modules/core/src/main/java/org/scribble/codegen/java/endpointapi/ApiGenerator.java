package org.scribble.codegen.java.endpointapi;

import java.util.Map;

import org.scribble.main.Job;
import org.scribble.sesstype.name.GProtocolName;

// Basic pattern: use TypeGenerators to create all necessary TypeBuilders and cache them, and generateApi should call build on all as a final step
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
	// FIXME: Path instead of String key?
	public abstract Map<String, String> generateApi();
	
	public Job getJob()
	{
		return this.job;
	}
}
