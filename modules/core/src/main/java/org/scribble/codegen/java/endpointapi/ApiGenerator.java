package org.scribble.codegen.java.endpointapi;

import java.io.File;
import java.util.Map;

import org.scribble.main.ScribbleException;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.util.ScribUtil;
import org.scribble.visit.Job;

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

	// Duplicated from CommandLine.runDot
	// Minimises the FSM up to bisimulation
	// N.B. ltsconvert will typically re-number the states
	protected static String runAut(String fsm, String aut) throws ScribbleException
	{
		String tmpName = aut + ".tmp";
		File tmp = new File(tmpName);
		if (tmp.exists())  // Factor out with CommandLine.runDot (file exists check)
		{
			throw new RuntimeException("Cannot overwrite: " + tmpName);
		}
		try
		{
			ScribUtil.writeToFile(tmpName, fsm);
			String[] res = ScribUtil.runProcess("ltsconvert", "-ebisim", "-iaut", "-oaut", tmpName);
			if (!res[1].isEmpty())
			{
				throw new RuntimeException(res[1]);
			}
			return res[0];
		}
		finally
		{
			tmp.delete();
		}
	}
}
