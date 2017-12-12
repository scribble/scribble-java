package org.scribble.ext.go.core.codegen.statetype2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ast.Module;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ext.go.ast.ParamRoleDecl;
import org.scribble.ext.go.core.type.ParamActualRole;
import org.scribble.ext.go.core.type.ParamRange;
import org.scribble.ext.go.type.index.ParamIndexVar;
import org.scribble.model.endpoint.EStateKind;
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
				+ this.apigen.generateScribbleRuntimeImports()
				+ "import \"" + ParamCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_TRANSPORT_PACKAGE + "\"\n"
				+ "import \"" + ParamCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_TRANSPORT_PACKAGE + "/tcp\"\n";
					

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
				roles.stream().map(rfoo ->
				{
					Set<ParamActualRole> actuals = this.apigen.actuals.get(rfoo).keySet();
					return
					actuals.stream().map(actual -> 
					{
						String epTypeName = ParamCoreSTEndpointApiGenerator.getGeneratedEndpointType(simpname, actual);
						List<ParamRoleDecl> decls = 
								gpd.getHeader().roledecls.getDecls().stream().filter(rd -> rd.getDeclName().equals(actual.getName()))
										.map(rd -> (ParamRoleDecl) rd).collect(Collectors.toList());
						if (decls.size() > 1)
						{
							throw new RuntimeException("Shouldn't get in here: " + actual);
						}
						List<ParamIndexVar> vars = decls.stream().flatMap(d -> d.params.stream()).collect(Collectors.toList());
										//.flatMap(rd -> ((ParamRoleDecl) rd).params.stream()).collect(Collectors.toList());
						
						if (actual.ranges.size() > 1)
						{
							throw new RuntimeException("TODO: " + actual);
						}
						//ParamRange g = actual.ranges.iterator().next();
						
						return

								  "\nfunc (p *" + ParamCoreSTEndpointApiGenerator.getGeneratedEndpointType(simpname, actual) + ") Ept() *session.Endpoint {\n"
								+ "return p.ept\n"
								+ "}\n"

								+ "\n\ntype " + epTypeName + " struct {\n"  // FIXME: factor out
								+ ParamCoreSTApiGenConstants.GO_ENDPOINT_PROTO + " *" + simpname + "\n"
								/*+ this.apigen.actuals.get(r).keySet().stream()
										.map(a -> 
									{
										String actualName = ParamCoreSTEndpointApiGenerator.getGeneratedActualRoleName(a);
										return actualName + "s map[int] func(*" + simpname + "_" + actualName + "_1) *"  // FIXME: init statechan, factor out with makeSTStateName
												+ ParamCoreSTStateChanApiBuilder.makeEndStateName(simpname, a) + "\n";
									}).collect(Collectors.joining(""))*/
								+ "*session.LinearResource\n"
								+ "ept *" + ParamCoreSTApiGenConstants.GO_ENDPOINT_TYPE + "\n"
								+ "Params map[string]int\n"
								+ "}\n"
								+ "\n"
								
								
								+ "func (ini *" + epTypeName + ") Accept(rolename session.Role, id int, addr, port string) error {\n"
								+ "ini.ept.Conn[rolename.Name()][id-1] = tcp.NewConnection(addr, port).Accept().(*tcp.Conn)\n"
								+ "return nil\n"
								+ "}\n"
								+ "\n"
								+ "func (ini *" + epTypeName + ") Connect(rolename session.Role, id int, addr, port string) error {\n"
								+ "ini.ept.Conn[rolename.Name()][id-1] = tcp.NewConnection(addr, port).Connect()\n"
								+ "return nil\n"
								+ "}\n"
								+ "\n"
								
								
								
								+ "func (p *" + simpname + ") New" + epTypeName
										+ "(" + 
												vars.stream().map(v -> v + " int, ").collect(Collectors.joining("")) + 
												"self int" +
											")"
										+ "(*" + epTypeName + ") {\n"

								/*+ "ep := &" + epTypeName + "{ " + ParamCoreSTApiGenConstants.GO_ENDPOINT_PROTO + ": p,\n"
								
										+ this.apigen.actuals.get(r).keySet().stream()
												.map(a -> 
														{
															String actualName = ParamCoreSTEndpointApiGenerator.getGeneratedActualRoleName(a);
															return actualName + "s: make(map[int] func(*" + simpname + "_" + actualName + "_1) *"  // FIXME: init statechan, factor out with makeSTStateName
																	+ ParamCoreSTStateChanApiBuilder.makeEndStateName(simpname, a) + ")";
														}).collect(Collectors.joining(", ")) + ",\n" 
										+ ParamCoreSTApiGenConstants.GO_ENDPOINT_ENDPOINT + ": "
												+ ParamCoreSTApiGenConstants.GO_ENDPOINT_CONSTRUCTOR + "(p, p." + r + ")}\n"
								
								+ this.apigen.actuals.entrySet().stream().filter(e -> !e.getKey().equals(r))			
										.map(e -> 
										{
											Map<ParamActualRole, EGraph> tmp = e.getValue();
											if (tmp.size() > 1)
											{
												throw new RuntimeException("[param-core] TODO: " + tmp);
											}
											ParamActualRole peer = tmp.keySet().iterator().next();
											ParamRange g = peer.ranges.iterator().next();
											Function<ParamIndexExpr, String> foo = ee ->
											{
												if (ee instanceof ParamIndexInt)
												{
													return ee.toString();
												}
												else if (ee instanceof ParamIndexVar)
												{
													return "ep.Params[\"" + ee + "\"]";
												}
												else
												{
													throw new RuntimeException("[param-core] TODO: " + ee);
												}
											};
											return
														"ep.Params = map[string]int {" + vars.stream().map(v -> "\"" + v + "\": " + v).collect(Collectors.joining(", ")) + "}\n"
													+ "ep.Peers[p." + peer.getName() + "] = struct{Start int; End int}{" + foo.apply(g.start) + ", " + foo.apply(g.end) + "}\n"
													+ "for i := ep." + ParamCoreSTApiGenConstants.GO_ENDPOINT_ENDPOINT + ".Peers[p." + peer.getName() + "].Start; i <= ep.Peers[p." + peer.getName() + "].End; i++ {\n"
													+ "\tp." + peer.getName() + ".(session.ParamRole).Register(i)\n"
													+ "}\n";
										}).collect(Collectors.joining(""))
												
								+ "return ep\n"*/
								+ "conns := make(map[string][]transport.Channel)\n"
								
								
								+ this.apigen.actuals.entrySet().stream().filter(e -> !e.getKey().equals(actual.getName()))
										.map(e -> "conns[p." + e.getKey() + ".Name()] = make([]transport.Channel, " 
												+ e.getValue().keySet().iterator().next().ranges.iterator().next().end + "-"  // FIXME: e.getValues().size() > 1
												+ e.getValue().keySet().iterator().next().ranges.iterator().next().start + "+1)\n")  // FIXME: start index 1
										.collect(Collectors.joining(""))

								
								+ "params := make(map[string]int)\n"
								+ decls.iterator().next().params.stream().map(x -> "params[\"" + x + "\"] = " + x + "\n").collect(Collectors.joining(""))
								+ "return &" + epTypeName + "{p, &session.LinearResource{}, session.NewEndpoint(self, -1, conns), params}\n"  // FIXME: numRoles
								+ "}\n"
								
								/*+ this.apigen.actuals.get(r).keySet().stream()
										.map(a -> 
									{
										String actualName = ParamCoreSTEndpointApiGenerator.getGeneratedActualRoleName(a);
										return 
											"\nfunc (ep *" + epTypeName + ") "
													+ "Register_" + actualName
													+ "(i int, impl func(*" + simpname + "_" + actualName + "_1) *"  // FIXME: factor out with above
															+ ParamCoreSTStateChanApiBuilder.makeEndStateName(simpname, a) + ") {\n"
													+ "ep." + actualName + "s[i] = impl\n"
													+ "ep.Proto."+ r +".(session.ParamRole).Register(i)\n"
													+ "}\n";
									}).collect(Collectors.joining(""));*/
						
						
									+ "\nfunc (ini *" + epTypeName + ") Init() (*" + epTypeName + "_1) {\n"
									+ "ini.Use()\n"
									+ "ini.ept.CheckConnection()\n"
									+ 
											((this.apigen.actuals.get(rfoo).get(actual).init.getStateKind() == EStateKind.POLY_INPUT)
													? "return ini.New" + ParamCoreSTEndpointApiGenerator.getGeneratedActualRoleName(actual) + "_1()\n"
													: "return &" + epTypeName + "_1{&session.LinearResource{}, ini}\n")
									+ "}";
					}).collect(Collectors.joining(""));
				}).collect(Collectors.joining("\n"));
		
		String dir = this.apigen.proto.toString().replaceAll("\\.", "/") + "/";
		Map<String, String> res = new HashMap<>();
		res.put(dir + simpname + ".go", sessPack);
		return res;
	}
}
