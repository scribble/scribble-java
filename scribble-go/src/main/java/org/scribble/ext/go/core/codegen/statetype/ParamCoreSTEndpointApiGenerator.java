package org.scribble.ext.go.core.codegen.statetype;

import java.util.Map;

import org.scribble.codegen.java.endpointapi.StateChannelApiGenerator;
import org.scribble.ext.go.core.type.ParamActualRole;
import org.scribble.main.Job;
import org.scribble.main.ScribbleException;
import org.scribble.model.endpoint.EGraph;
import org.scribble.type.name.GProtocolName;

// Duplicated from org.scribble.ext.go.codegen.statetype.go.GoStEndpointApiGenerator
public class ParamCoreSTEndpointApiGenerator
{
	public final Job job;
	
	public ParamCoreSTEndpointApiGenerator(Job job)
	{
		this.job = job;
	}

	// N.B. the base EGraph class will probably be replaced by a more specific (and more helpful) param-core class later
	public Map<String, String> generateGoApi(GProtocolName fullname, ParamActualRole self, EGraph egraph) throws ScribbleException
	{
		this.job.debugPrintln("\n[param-core] Running " + StateChannelApiGenerator.class + " for " + fullname + "@" + self);
		ParamCoreSTStateChanAPIBuilder apigen
				= new ParamCoreSTStateChanAPIBuilder(this.job, fullname, self, egraph);
		Map<String, String> api = apigen.build();  // filepath -> source 
		api.putAll(apigen.buildSessionAPI()); 
		return api;
	}

}
