package org.scribble.ext.go.core.codegen.statetype;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.scribble.ast.Module;
import org.scribble.ast.ProtocolDecl;
import org.scribble.type.kind.Global;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.Role;

// Cf. STStateChanApiBuilder
public class ParamCoreSTSessionApiBuilder  // FIXME: make base STSessionApiBuilder
{
	//private ParamCoreSTStateChanApiBuilder api;
	private ParamCoreSTEndpointApiGenerator apigen;
	
	//public ParamCoreSTSessionApiBuilder(ParamCoreSTStateChanApiBuilder api)
	public ParamCoreSTSessionApiBuilder(ParamCoreSTEndpointApiGenerator apigen)
	{
		this.apigen = apigen;
	}

	//@Override
	public Map<String, String> build()  // FIXME: factor out
	{
		Module mod = this.apigen.job.getContext().getModule(this.apigen.proto.getPrefix());
		GProtocolName simpname = this.apigen.proto.getSimpleName();
		ProtocolDecl<Global> gpd = mod.getProtocolDecl(simpname);
		List<Role> roles = gpd.header.roledecls.getRoles();

		/*Set<EState> instates = new HashSet<>();
		Predicate<EState> f = s -> s.getStateKind() == EStateKind.UNARY_INPUT || s.getStateKind() == EStateKind.POLY_INPUT;
		if (f.test(this.apigen.graph.init))
		{
			instates.add(this.apigen.graph.init);
		}
		instates.addAll(MState.getReachableStates(this.apigen.graph.init).stream().filter(f).collect(Collectors.toSet()));*/

		// roles
		String sessPack =
					//"package " + this.apigen.getRootPackage() + "\n"  // FIXME: factor out
					this.apigen.generateRootPackageDecl() + "\n"
				+ "\n"
				//+ "import \"" + ParamCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_PACKAGE + "\"\n"
				+ this.apigen.generateScribbleRuntimeImports() + "\n";

		sessPack += "\n\n"
				+ "type " + simpname + " struct {\n"
				+ roles.stream().map(r -> r + " " + ParamCoreSTApiGenConstants.GO_ROLE_TYPE + "\n").collect(Collectors.joining(""))
						// Just need role name constants for now -- params not fixed until endpoint creation
				+ "}\n"
				+ "\n" 
				+ "func New" + simpname + "() " + simpname + " {\n"
				+ "return " + simpname + "{ " + roles.stream().map(r -> ParamCoreSTApiGenConstants.GO_ROLE_CONSTRUCTOR
						+ "(\"" + r + "\")").collect(Collectors.joining(", ")) + " }\n"
						 // Singleton types?
				+ "}\n";

		/*sessPack +=
				roles.stream().map(r -> 
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
				+ "\n";*/
		
		// Protocol and role specific endpoints
		/*Function<Role, String> epTypeName = r -> "_Endpoint" + simpname + "_" + r;
		sessPack +=
				roles.stream().map(r ->
						  "\n\ntype " + epTypeName.apply(r) + " struct {\n"  // FIXME: factor out
						+ r + " *" + ParamCoreSTApiGenConstants.GO_ENDPOINT_TYPE + "\n"
						+ "}\n"
						+ "\n"
						+ "func New" + epTypeName.apply(r) + "(P *" + simpname + ") *" + epTypeName.apply(r) + "{\n"
						+ "return &" + epTypeName.apply(r) + " { " + r + ": "
								+ ParamCoreSTApiGenConstants.GO_ENDPOINT_TYPE + "(P, P." + r + ") }\n"
						+ "}"
				).collect(Collectors.joining(""));*/
		sessPack += "\n" +
				roles.stream().map(r ->
					  "\n"
					+ "func New" + ParamCoreSTEndpointApiGenerator.getGeneratedEndpointType(simpname, r) + "(p " + simpname + ") (*"
							+ ParamCoreSTApiGenConstants.GO_ENDPOINT_TYPE + ", " + ParamCoreSTApiGenConstants.GO_FINALISER_TYPE + ") {\n"
					+ "ep := " + ParamCoreSTApiGenConstants.GO_ENDPOINT_CONSTRUCTOR + "(p, p." + r + ")\n"
					+ "return ep, ep." + ParamCoreSTApiGenConstants.GO_ENDPOINT_FINALISE + "\n"
					+ "}\n"
				).collect(Collectors.joining(""));
		
		String dir = this.apigen.proto.toString().replaceAll("\\.", "/") + "/";
		Map<String, String> res = new HashMap<>();
		res.put(dir + simpname + ".go", sessPack);
		return res;
	}
}
