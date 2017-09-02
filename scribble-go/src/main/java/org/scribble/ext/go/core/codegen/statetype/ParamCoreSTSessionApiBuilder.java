package org.scribble.ext.go.core.codegen.statetype;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.scribble.ast.Module;
import org.scribble.ast.ProtocolDecl;
import org.scribble.model.MState;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.EStateKind;
import org.scribble.type.kind.Global;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.Role;

public class ParamCoreSTSessionApiBuilder
{
	private ParamCoreSTStateChanApiBuilder api;
	
	public ParamCoreSTSessionApiBuilder(ParamCoreSTStateChanApiBuilder api)
	{
		this.api = api;
	}

	//@Override
	public Map<String, String> buildSessionAPI()  // FIXME: factor out
	{
		Module mod = api.job.getContext().getModule(api.gpn.getPrefix());
		GProtocolName simpname = api.gpn.getSimpleName();
		ProtocolDecl<Global> gpd = mod.getProtocolDecl(simpname);

		List<Role> roles = gpd.header.roledecls.getRoles();
		Set<EState> instates = new HashSet<>();
		Predicate<EState> f = (s) -> s.getStateKind() == EStateKind.UNARY_INPUT || s.getStateKind() == EStateKind.POLY_INPUT;
		if (f.test(api.graph.init))
		{
			instates.add(api.graph.init);
		}
		instates.addAll(MState.getReachableStates(api.graph.init).stream().filter(f).collect(Collectors.toSet()));
	
		Map<String, String> res = new HashMap<>();
		String dir = api.gpn.toString().replaceAll("\\.", "/") + "/";

		// roles
		String sessclass =
					"package " + api.getPackage() + "\n"  // FIXME: factor out
				+ "\n"
				+ "import \"" + ParamCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_PACKAGE + "\"\n"
				+ "\n"
				+ roles.stream().map(r -> 
				  	  "type _" + r + " struct { }\n"
				  	+ "\n"
				  	+ "func (*_" + r +") GetRoleName() string {\n"
				  	+ "return \"" + r + "\"\n"
				  	+ "}\n"
				  	+ "\n"
				  	+ "var __" + r + " *_" + r + "\n"
				  	+ "\n"
				  	+ "func new" + r + "() *_" + r + " {\n"  // FIXME: not concurrent
				  	+ "if __" + r + " == nil {\n"
				  	+ "__"+ r + " = &_" + r + "{}\n"
				  	+ "}\n"
				  	+ "return __" + r + "\n"
				  	+ "}"
				  ).collect(Collectors.joining("\n\n")) + "\n"
				+ "\n"
				+ "type " + simpname + " struct {\n"
				+ roles.stream().map(r -> r + " *_" + r).collect(Collectors.joining("\n")) + "\n"  // FIXME: role constants should be pointers?
				+ "}\n"
				+ "\n" 
				+ "func New" + simpname + "() *" + simpname + "{\n"
				+ "return &" + simpname + "{ " + roles.stream().map(r -> "new" + r + "()").collect(Collectors.joining(", ")) + " }\n"
				+ "}";
		
		// Protocol and role specific endpoints
		Function<Role, String> epTypeName = r -> "_Endpoint" + simpname + "_" + r;
		sessclass +=
				roles.stream().map(r ->
						  "\n\n"
						+ "type " + epTypeName.apply(r) + " struct {\n"  // FIXME: factor out
						+ r + " *" + ParamCoreSTApiGenConstants.GO_ENDPOINT_TYPE + "\n"
						+ "}\n"
						+ "\n"
						+ "func New" + epTypeName.apply(r) + "(P *" + simpname + ") *" + epTypeName.apply(r) + "{\n"
						+ "return &" + epTypeName.apply(r) + " { " + r + ": "
								+ ParamCoreSTApiGenConstants.GO_ENDPOINT_TYPE + "(P, P." + r + ") }\n"
						+ "}"
				).collect(Collectors.joining(""));
		
		res.put(dir + simpname + ".go", sessclass);
		
		return res;
	}
}
