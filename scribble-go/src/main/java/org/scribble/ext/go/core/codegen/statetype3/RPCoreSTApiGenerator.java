package org.scribble.ext.go.core.codegen.statetype3;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.scribble.ext.go.core.type.RPRoleVariant;
import org.scribble.main.Job;
import org.scribble.main.ScribbleException;
import org.scribble.model.endpoint.EGraph;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.Role;

// Duplicated from org.scribble.ext.go.codegen.statetype.go.GoSTEndpointApiGenerator
public class RPCoreSTApiGenerator
{
	public final Job job;
	public final GProtocolName proto;
	public final Map<Role, Map<RPRoleVariant, EGraph>> actuals;
	
	public final String packpath;  // Prefix for absolute imports in generated APIs (e.g., "github.com/rhu1/scribble-go-runtime/test2/bar/bar02/Bar2") -- not supplied by Scribble module
	public final Role self;  
			// FIXME: just a role name -- cf. CL arg
			// FIXME: any way to separate Session API (Protocol) from Endpoint/StateChan APIs?
	
	public RPCoreSTApiGenerator(Job job, GProtocolName fullname, Map<Role, Map<RPRoleVariant, EGraph>> actuals, String packpath, Role self)
	{
		this.job = job;
		this.proto = fullname;
		this.actuals = Collections.unmodifiableMap(actuals);
		this.packpath = packpath;
		this.self = self;
	}

	// N.B. the base EGraph class will probably be replaced by a more specific (and more helpful) param-core class later
	public Map<String, String> build() throws ScribbleException
	{
		Map<String, String> res = new HashMap<>();  // filepath -> source 
		res.putAll(buildSessionApi());
		for (Entry<RPRoleVariant, EGraph> actual : this.actuals.get(this.self).entrySet())
		{
			res.putAll(buildStateChannelApi(actual.getKey(), actual.getValue()));
		}
		return res;
	}

	//@Override
	public Map<String, String> buildSessionApi()  // FIXME: factor out
	{
		this.job.debugPrintln("\n[param-core] Running " + RPCoreSTSessionApiBuilder.class + " for " + this.proto + "@" + this.self);
		return new RPCoreSTSessionApiBuilder(this).build();
	}
	
	public Map<String, String> buildStateChannelApi(RPRoleVariant actual, EGraph graph)  // FIXME: factor out
	{
		this.job.debugPrintln("\n[param-core] Running " + RPCoreSTStateChanApiBuilder.class + " for " + this.proto + "@" + this.self);
		return new RPCoreSTStateChanApiBuilder(this, actual, graph).build();
	}
	
	//@Override
	public String getRootPackage()  // Derives only from proto name
	{
		return this.proto.getSimpleName().toString();
	}

	public String generateRootPackageDecl()
	{
		return "package " + getRootPackage();
	}

	/*//@Override
	public List<String> getScribbleRuntimeImports()  // FIXME: factor up
	{
		return Stream.of(
					ParamCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_SESSION_PACKAGE
					////ParamCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_SESSIONPARAM_PACKAGE
					//ParamCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_TRANSPORT_PACKAGE

					//ParamCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_BYTES_PACKAGE,
					//ParamCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_GOB_PACKAGE
				).collect(Collectors.toList());
	}

	public String generateScribbleRuntimeImports()
	{
		return getScribbleRuntimeImports().stream().map(x -> "import \"" + x + "\"\n").collect(Collectors.joining());
	}*/
	

	// Endpoint kind -- use actual-role name, e.g., S_1To1, W_1Ton
	public static String getGeneratedEndpointTypeName(GProtocolName simpname, RPRoleVariant r)
	{
		//return simpname + "_" + getGeneratedActualRoleName(r);
		return getGeneratedActualRoleName(r);
	}
	
	// Endpoint, e.g., S_1To1, W_1Ton
	public static String getGeneratedActualRoleName(RPRoleVariant actual)
	{
		/*return actual.getName()
				+ actual.ranges.toString().replaceAll("\\[", "_").replaceAll("\\]", "_").replaceAll("\\.", "_");*/
		/*if (actual.ranges.size() > 1 || actual.coranges.size() > 0)
		{
			throw new RuntimeException("[param-core] TODO: " + actual);
		}
		ParamRange g = actual.ranges.iterator().next();
		return actual.getName() + "_" + g.start + "To" + g.end;*/
		return actual.getName() + "_"
				+ actual.ranges.stream().map(g -> g.start + "To" + g.end).sorted().collect(Collectors.joining("and"))
				+ (actual.coranges.isEmpty() ? "" : "_not_")
				+ actual.coranges.stream().map(g -> g.start + "To" + g.end).sorted().collect(Collectors.joining("and"));
	}
}
