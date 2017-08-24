package org.scribble.ext.go.codegen.statetype.go;

import java.util.Map;

import org.scribble.codegen.java.endpointapi.StateChannelApiGenerator;
import org.scribble.main.Job;
import org.scribble.main.ScribbleException;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.Role;

public class GoEndpointApiGenerator
{
	public final Job job;
	
	public GoEndpointApiGenerator(Job job)
	{
		this.job = job;
	}

	
	public Map<String, String> generateGoApi(GProtocolName fullname, Role self) throws ScribbleException
	{
		this.job.debugPrintln("\n[Go API gen] Running " + StateChannelApiGenerator.class + " for " + fullname + "@" + self);
		GoSTStateChanAPIBuilder apigen = new GoSTStateChanAPIBuilder(this.job, fullname, self, this.job.getContext().getEGraph(fullname, self));
		Map<String, String> api = apigen.build();  // filepath -> source 
		api.putAll(apigen.buildSessionAPI());  // FIXME: factor better with STStateChanAPIBuilder
		return api;
	}


	/*public Map<String, String> generateSessionApi(GProtocolName fullname) throws ScribbleException
	{
		this.job.debugPrintln("\n[Java API gen] Running " + SessionApiGenerator.class + " for " + fullname);
		SessionApiGenerator sg = new SessionApiGenerator(this.job, fullname);  // FIXME: reuse?
		Map<String, String> map = sg.generateApi();  // filepath -> class source
		return map;
	}
	
	// FIXME: refactor an EndpointApiGenerator -- ?
	public Map<String, String> generateStateChannelApi(GProtocolName fullname, Role self, boolean subtypes) throws ScribbleException
	{
		/*JobContext jc = this.job.getContext();
		if (jc.getEndpointGraph(fullname, self) == null)
		{
			buildGraph(fullname, self);
		}* /
		job.debugPrintln("\n[Java API gen] Running " + StateChannelApiGenerator.class + " for " + fullname + "@" + self);
		StateChannelApiGenerator apigen = new StateChannelApiGenerator(this.job, fullname, self);
		IOInterfacesGenerator iogen = null;
		try
		{
			iogen = new IOInterfacesGenerator(apigen, subtypes);
		}
		catch (RuntimeScribbleException e)  // FIXME: use IOInterfacesGenerator.skipIOInterfacesGeneration
		{
			//System.err.println("[Warning] Skipping I/O Interface generation for protocol featuring: " + fullname);
			this.job.warningPrintln("Skipping I/O Interface generation for: " + fullname + "\n  Cause: " + e.getMessage());
		}
		// Construct the Generators first, to build all the types -- then call generate to "compile" all Builders to text (further building changes will not be output)
		Map<String, String> api = new HashMap<>(); // filepath -> class source  // Store results?
		api.putAll(apigen.generateApi());
		if (iogen != null)
		{
			api.putAll(iogen.generateApi());
		}
		return api;
	}*/
}
