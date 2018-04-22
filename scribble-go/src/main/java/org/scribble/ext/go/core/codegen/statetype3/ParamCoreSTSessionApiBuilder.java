package org.scribble.ext.go.core.codegen.statetype3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ast.Module;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ext.go.ast.ParamRoleDecl;
import org.scribble.ext.go.core.type.ParamActualRole;
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

		String epPack =
					//this.apigen.generateRootPackageDecl() + "\n"
					"\n"
				//+ "import \"" + ParamCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_PACKAGE + "\"\n"
				+ this.apigen.generateScribbleRuntimeImports()
				+ "import \"" + ParamCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_TRANSPORT_PACKAGE + "\"\n";
				//+ "import \"" + ParamCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_TRANSPORT_PACKAGE + "/tcp\"\n";

		// roles
		String sessPack =
					this.apigen.generateRootPackageDecl() + "\n"
				+ "\n"
				+ this.apigen.generateScribbleRuntimeImports() + "\n"
				//+ "import \"" + ParamCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_TRANSPORT_PACKAGE + "\"\n"

				+ (roles.stream().map(rfoo ->
						{
							Set<ParamActualRole> actuals = this.apigen.actuals.get(rfoo).keySet();
							return
							actuals.stream().map(actual -> 
							{
								return "import _" + ParamCoreSTEndpointApiGenerator.getGeneratedActualRoleName(actual) + " \"" + this.apigen.impath + "/" + this.apigen.getRootPackage()
										+ "/" + ParamCoreSTEndpointApiGenerator.getGeneratedActualRoleName(actual) + "\"\n";
							}).collect(Collectors.joining(""));
						}).collect(Collectors.joining(""))
				);
					
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
				+ "}\n"
				+ "\n"
				+ "func (*" + simpname +") IsProtocol() {\n"
				+ "\n"
				+ "}\n";


		String dir = this.apigen.proto.toString().replaceAll("\\.", "/") + "/";
		Map<String, String> res = new HashMap<>();
		
		// Protocol and role specific endpoints
		//Function<Role, String> epTypeName = r -> "_Endpoint" + simpname + "_" + r;
		sessPack +=
				roles.stream().map(rfoo ->
				{
					Set<ParamActualRole> actuals = this.apigen.actuals.get(rfoo).keySet();
					return
					actuals.stream().map(actual -> 
					{
						String epTypeName = ParamCoreSTEndpointApiGenerator.getGeneratedEndpointTypeName(simpname, actual);
						List<ParamRoleDecl> decls = 
								gpd.getHeader().roledecls.getDecls().stream().filter(rd -> rd.getDeclName().equals(actual.getName()))
										.map(rd -> (ParamRoleDecl) rd).collect(Collectors.toList());
						if (decls.size() > 1)
						{
							throw new RuntimeException("Shouldn't get in here: " + actual);
						}
						List<ParamIndexVar> vars = decls.stream().flatMap(d -> d.params.stream()).collect(Collectors.toList());
						
						
						String epPack1 =

								  "\nfunc (p *" + ParamCoreSTEndpointApiGenerator.getGeneratedEndpointTypeName(simpname, actual) + ") Ept() *session.Endpoint {\n"
								+ "return p.ept\n"
								+ "}\n"
								+ "\n"
								+ "\nfunc (p *" + ParamCoreSTEndpointApiGenerator.getGeneratedEndpointTypeName(simpname, actual) + ") Params() map[string]int {\n"
								+ "return p.params\n"
								+ "}\n"

								+ "\n\ntype " + epTypeName + " struct {\n"  // FIXME: factor out
								+ ParamCoreSTApiGenConstants.GO_ENDPOINT_PROTO + " session.Protocol\n"
								+ "*session.LinearResource\n"
								+ "ept *" + ParamCoreSTApiGenConstants.GO_ENDPOINT_TYPE + "\n"
								+ "params map[string]int\n"
								+ "}\n"
								+ "\n"

								+ "func New(p session.Protocol, params map[string]int, self int) *" + epTypeName + "{\n"
								+ "conns := make(map[string]map[int]transport.Channel)\n"
								+ this.apigen.actuals.entrySet().stream()
										.map(e -> "conns[\"" + e.getKey().getLastElement() + "\"] = "
												+ "make(map[int]transport.Channel)\n")
										.collect(Collectors.joining(""))
								+ "return &" + epTypeName + "{p, &session.LinearResource{}, session.NewEndpoint(self, conns), params}\n"
								+ "}\n"
								+ "\n"
								
								
								+ "func (ini *" + epTypeName + ") Accept(rolename session.Role, id int, acceptor transport.Transport) error {\n"
								+ "ini.ept.Conn[rolename.Name()][id] = acceptor.Accept()\n"
								+ "return nil\n"  // FIXME: runtime currently does log.Fatal on error
								+ "}\n"
								+ "\n"
								+ "func (ini *" + epTypeName + ") Request(rolename session.Role, id int, requestor transport.Transport) error {\n"
								+ "ini.ept.Conn[rolename.Name()][id] = requestor.Connect()\n"
								+ "return nil\n"  // FIXME: runtime currently does log.Fatal on error
								+ "}\n"
								+ "\n";
								
							String sessPack1 =
								"func (p *" + simpname + ") New" + epTypeName
										+ "(" + 
												vars.stream().map(v -> v + " int, ").collect(Collectors.joining("")) + 
												"self int" +
											")"
										+ "(*_" + ParamCoreSTEndpointApiGenerator.getGeneratedActualRoleName(actual) + "." + epTypeName + ") {\n"

								
								+ "params := make(map[string]int)\n"
								+ decls.iterator().next().params.stream().map(x -> "params[\"" + x + "\"] = " + x + "\n").collect(Collectors.joining(""))
								//+ "return &" + epTypeName + "{p, &session.LinearResource{}, session.NewEndpoint(self, " + "conns), params}\n"  // FIXME: numRoles
								+ "return _" + ParamCoreSTEndpointApiGenerator.getGeneratedActualRoleName(actual) + ".New" + "(p, params, self)\n"  // FIXME: numRoles
								+ "}\n";
								
							epPack1 += 
									 "\nfunc (ini *" + epTypeName + ") Run(f func(*"//_" + ParamCoreSTEndpointApiGenerator.getGeneratedActualRoleName(actual) + ".Init)" 
									 						+ "Init)"
															+ "End" + ") " 
													+ "End {\n"
									+ "ini.Use()\n"
									+ "ini.ept.CheckConnection()\n"
									+ 
											((this.apigen.actuals.get(rfoo).get(actual).init.getStateKind() == EStateKind.POLY_INPUT)
													? "return ini.New" + ParamCoreSTEndpointApiGenerator.getGeneratedActualRoleName(actual) + "_1()\n"
													: "return f(&Init{ new(session.LinearResource), ini })")
									+ "}";
						
							res.put(dir + ParamCoreSTEndpointApiGenerator.getGeneratedActualRoleName(actual) + "/" + ParamCoreSTEndpointApiGenerator.getGeneratedActualRoleName(actual) + ".go",
									"package " + ParamCoreSTEndpointApiGenerator.getGeneratedActualRoleName(actual) + "\n" + epPack + "\n" + epPack1);
							return sessPack1;
					}).collect(Collectors.joining(""));
				}).collect(Collectors.joining("\n"));
		
		res.put(dir + simpname + ".go", sessPack);
		return res;
	}
}
