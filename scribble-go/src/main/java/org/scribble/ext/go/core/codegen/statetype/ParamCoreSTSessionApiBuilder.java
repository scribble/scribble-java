package org.scribble.ext.go.core.codegen.statetype;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.scribble.ast.Module;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ext.go.ast.ParamRoleDecl;
import org.scribble.ext.go.type.index.ParamIndexVar;
import org.scribble.type.kind.Global;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.Role;

// Cf. STStateChanApiBuilder
public class ParamCoreSTSessionApiBuilder  // FIXME: make base STSessionApiBuilder
{
	private ParamCoreSTEndpointApiGenerator apigen;

	//private final Map<Role, Map<ParamActualRole, EGraph>> actuals;
	
	public ParamCoreSTSessionApiBuilder(ParamCoreSTEndpointApiGenerator apigen)//, Map<Role, Map<ParamActualRole, EGraph>> actuals)
	{
		this.apigen = apigen;
		//this.actuals = Collections.unmodifiableMap(actuals);
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

		sessPack += "\n"
				+ "type " + simpname + " struct {\n"
				+ roles.stream().map(r -> r + " " + ParamCoreSTApiGenConstants.GO_ROLE_TYPE + "\n").collect(Collectors.joining(""))
						// Just need role name constants for now -- params not fixed until endpoint creation
				+ "}\n"
				+ "\n" 
				+ "func New" + simpname + "() *" + simpname + " {\n"
				+ "return &" + simpname + "{ " + roles.stream().map(r -> ParamCoreSTApiGenConstants.GO_ROLE_CONSTRUCTOR
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
		//Function<Role, String> epTypeName = r -> "_Endpoint" + simpname + "_" + r;
		sessPack +=
				roles.stream().map(r ->
				{
					String epTypeName = ParamCoreSTEndpointApiGenerator.getGeneratedEndpointType(simpname, r);
					List<ParamIndexVar> vars = 
					gpd.getHeader().roledecls.getDecls().stream().filter(rd -> rd.getDeclName().equals(r))
							.flatMap(rd -> ((ParamRoleDecl) rd).params.stream()).collect(Collectors.toList());
					return
							  "\n\ntype " + epTypeName + " struct {\n"  // FIXME: factor out
							+ ParamCoreSTApiGenConstants.GO_ENDPOINT_PROTO + " *" + simpname + "\n"
							+ this.apigen.actuals.get(r).keySet().stream()
									//.filter(a -> a.getName().equals(r))
									.map(a -> 
							//+ this.apigen.actuals.entrySet().stream().flatMap(e -> e.getValue().keySet().stream()).map(a -> 
							  {
									String actualName = ParamCoreSTEndpointApiGenerator.getGeneratedActualRoleName(a);
									/*return "Sub_" + actualName + " func(*" + simpname + "_" + actualName + "_1) *"  // FIXME: init statechan, factor out with makeSTStateName
											//+ ParamCoreSTApiGenConstants.GO_SCHAN_END_TYPE + "\n";
											+ ParamCoreSTStateChanApiBuilder.makeEndStateName(simpname, a) + "\n";*/
									return actualName + "s map[int] func(*" + simpname + "_" + actualName + "_1) *"  // FIXME: init statechan, factor out with makeSTStateName
											+ ParamCoreSTStateChanApiBuilder.makeEndStateName(simpname, a) + "\n";
							  }).collect(Collectors.joining(""))
							+ "params map[string]int\n"
							+ "ept *" + ParamCoreSTApiGenConstants.GO_ENDPOINT_TYPE + "\n"
							+ "}\n"
							+ "\n"
							+ "func (p *" + simpname + ") New" + epTypeName
									//+ "(params map[string]int)
									+ "(" + 
											vars.stream().map(v -> v + " int").collect(Collectors.joining(", ")) + ")"
									+ "(*" + epTypeName + ") {\n"
							+ "return &" + epTypeName + "{ " + ParamCoreSTApiGenConstants.GO_ENDPOINT_PROTO + ": p, "
							
									+ this.apigen.actuals.get(r).keySet().stream()
											.map(a -> 
													{
														String actualName = ParamCoreSTEndpointApiGenerator.getGeneratedActualRoleName(a);
														return actualName + "s: make(map[int] func(*" + simpname + "_" + actualName + "_1) *"  // FIXME: init statechan, factor out with makeSTStateName
																+ ParamCoreSTStateChanApiBuilder.makeEndStateName(simpname, a) + ")";
													}).collect(Collectors.joining(", ")) + ", " 
									
									+ "params: "
											//+ "params,"
											+ "map[string]int {" + vars.stream().map(v -> "\"" + v + "\": " + v).collect(Collectors.joining()) + "}, "
											+ "ept: "
											+ ParamCoreSTApiGenConstants.GO_ENDPOINT_CONSTRUCTOR + "(p, p." + r + ")}\n"
							+ "}\n"
							+ this.apigen.actuals.get(r).keySet().stream()
									//.filter(a -> a.getName().equals(r))
									.map(a -> 
							//+ this.apigen.actuals.entrySet().stream().flatMap(e -> e.getValue().keySet().stream()).map(a -> 
							  {
									String actualName = ParamCoreSTEndpointApiGenerator.getGeneratedActualRoleName(a);
							  	return 
										"\nfunc (ep *" + epTypeName + ") "
												+ "Register_" + actualName
												+ "(i int, impl func(*" + simpname + "_" + actualName + "_1) *"  // FIXME: factor out with above
														//+ ParamCoreSTApiGenConstants.GO_SCHAN_END_TYPE + ") {\n"
														+ ParamCoreSTStateChanApiBuilder.makeEndStateName(simpname, a) + ") {\n"
												//+ "ep.Sub_" + actualName + " = impl\n"
												+ "ep." + actualName + "s[i] = impl\n"
												+ "}\n";
							  }).collect(Collectors.joining(""));
				}).collect(Collectors.joining(""));
		
		String dir = this.apigen.proto.toString().replaceAll("\\.", "/") + "/";
		Map<String, String> res = new HashMap<>();
		res.put(dir + simpname + ".go", sessPack);
		return res;
	}
}
