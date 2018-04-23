package org.scribble.ext.go.core.codegen.statetype3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ast.Module;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ext.go.ast.RPRoleDecl;
import org.scribble.ext.go.core.type.RPRoleVariant;
import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.model.endpoint.EStateKind;
import org.scribble.type.kind.Global;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.Role;

// RP Session API = Protocol API + Endpoint Kind APIs 
// Cf. STStateChanApiBuilder
public class RPCoreSTSessionApiBuilder
{
	private RPCoreSTApiGenerator apigen;

	public RPCoreSTSessionApiBuilder(RPCoreSTApiGenerator apigen)
	{
		this.apigen = apigen;
	}

	//@Override
	public Map<String, String> build()  // FIXME: factor out
	{
		Module mod = this.apigen.job.getContext().getModule(this.apigen.proto.getPrefix());
		ProtocolDecl<Global> gpd = mod.getProtocolDecl(this.apigen.proto.getSimpleName());
		String basedir = this.apigen.proto.toString().replaceAll("\\.", "/") + "/";  // Full name
		Map<String, String> res = new HashMap<>();  // filepath -> source
		buildProtocolApi(gpd, basedir, res);
		buildEndpointKindApi(gpd, basedir, res);
		return res;
	}
	
	private void buildProtocolApi(ProtocolDecl<Global> gpd, String basedir, Map<String, String> res)
	{
		GProtocolName simpname = this.apigen.proto.getSimpleName();
		List<Role> rolenames = gpd.header.roledecls.getRoles();

		// roles
		String protoFile =
					"package " + this.apigen.getApiRootPackageName() + "\n"
				+ "\n"
							
				// Import Endpoint Kind APIs -- FIXME: CL args
				+ (rolenames.stream().map(rname ->
						{
							Set<RPRoleVariant> variants = this.apigen.variants.get(rname).keySet();
							return
							variants.stream().map(v -> 
							{
								String epkindPackName = RPCoreSTApiGenerator.getEndpointKindPackageName(v);
								return "import _" + epkindPackName  // FIXME: alias now redundant
										+ " \"" + this.apigen.packpath + "/" + this.apigen.getApiRootPackageName() + "/" + epkindPackName + "\"\n";
							}).collect(Collectors.joining(""));
						}).collect(Collectors.joining(""))
				);
					
		protoFile += "\n"
				// Protocol type
				+ "type " + simpname + " struct {\n"
				+ "}\n"
				
				// session.Protocol interface
				+ "\n" 
				+ "func (*" + simpname +") IsProtocol() {\n"
				+ "}\n"
				
				// Protocol type constructor
				+ "\n"
				+ "func New() *" + simpname + " {\n"
				+ "return &" + simpname + "{ }\n"
				+ "}\n";

		protoFile += "\n" +
				rolenames.stream().map(rname ->
				{
					Set<RPRoleVariant> variants = this.apigen.variants.get(rname).keySet();
					return
						variants.stream().map(variant -> 
						{
							String epkindTypeName = RPCoreSTApiGenerator.getEndpointKindTypeName(simpname, variant);
							List<RPRoleDecl> decls = 
									gpd.getHeader().roledecls.getDecls().stream().filter(rd -> rd.getDeclName().equals(variant.getName()))
											.map(rd -> (RPRoleDecl) rd).collect(Collectors.toList());
							if (decls.size() > 1)
							{
								throw new RuntimeException("Shouldn't get in here: " + variant);
							}
							List<RPIndexVar> ivars = decls.stream().flatMap(d -> d.params.stream()).collect(Collectors.toList());
									// FIXME: get index vars from protocol body -- index var decls in sig now deprecated
									
							// Endpoint Kind constructor -- makes index var value maps
							return "func (p *" + simpname + ") New" + "_" + epkindTypeName
									+ "(" + ivars.stream().map(v -> v + " int, ").collect(Collectors.joining("")) + "self int" + ")"
									+ " *_" + RPCoreSTApiGenerator.getGeneratedRoleVariantName(variant) + "." + epkindTypeName + " {\n"
									+ "params := make(map[string]int)\n"
									+ decls.iterator().next().params.stream().map(x -> "params[\"" + x + "\"] = " + x + "\n").collect(Collectors.joining(""))
									+ "return _" + RPCoreSTApiGenerator.getEndpointKindPackageName(variant) + ".New" + "(p, params, self)\n"
									+ "}\n";
						}).collect(Collectors.joining(""));
				}).collect(Collectors.joining("\n"));

		res.put(basedir + simpname + ".go", protoFile);
	}

	private void buildEndpointKindApi(ProtocolDecl<Global> gpd, String basedir, Map<String, String> res)
	{
		GProtocolName simpname = this.apigen.proto.getSimpleName();
		List<Role> roles = gpd.header.roledecls.getRoles();

		String epkindImports = "\n"  // Package decl done later (per variant)
				+ "import \"" + RPCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_SESSION_PACKAGE + "\"\n"
				+ "import \"" + RPCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_TRANSPORT_PACKAGE + "\"\n";
		
		// Endpoint Kind API per role variant
		for (Role rname : roles)
		{
			Set<RPRoleVariant> variants = this.apigen.variants.get(rname).keySet();
			for (RPRoleVariant variant : variants) 
			{
				String epkindTypeName = RPCoreSTApiGenerator.getEndpointKindTypeName(simpname, variant);
				List<RPRoleDecl> decls = 
						gpd.getHeader().roledecls.getDecls().stream().filter(rd -> rd.getDeclName().equals(variant.getName()))
								.map(rd -> (RPRoleDecl) rd).collect(Collectors.toList());
				if (decls.size() > 1)
				{
					throw new RuntimeException("Shouldn't get in here: " + variant);
				}
						// FIXME: get index vars from protocol body -- index var decls in sig now deprecated
				
				String epkindFile = epkindImports + "\n"
						
						// Endpoint Kind type
						+ "type " + epkindTypeName + " struct {\n"
						+ RPCoreSTApiGenConstants.GO_ENDPOINT_PROTO + " session.Protocol\n"
						+ "Self int\n"
						+ "*session.LinearResource\n"
						+ RPCoreSTApiGenConstants.GO_ENDPOINT_ENDPOINT + " *" + RPCoreSTApiGenConstants.GO_ENDPOINT_TYPE + "\n"
						+ "Params map[string]int\n"
						+ "}\n"

						// Endpoint Kind type constructor -- makes connection maps
						+ "\n"
						+ "func New(p session.Protocol, params map[string]int, self int) *" + epkindTypeName + " {\n"
						+ "conns := make(map[string]map[int]transport.Channel)\n"
						+ this.apigen.variants.entrySet().stream()
								.map(e -> "conns[\"" + e.getKey().getLastElement() + "\"] = " + "make(map[int]transport.Channel)\n")
								.collect(Collectors.joining(""))
						+ "return &" + epkindTypeName + "{p, self, &session.LinearResource{}, session.NewEndpoint(self, conns), params}\n"
						+ "}\n"

						// Dial/Accept methdos -- FIXME: internalise peers
						+ "\n"
						+ "func (ini *" + epkindTypeName + ") Accept(rolename string, id int, acc transport.Transport) error {\n"
						+ "ini." + RPCoreSTApiGenConstants.GO_ENDPOINT_ENDPOINT + "." + RPCoreSTApiGenConstants.GO_CONNECTION_MAP + "[rolename][id] = acc.Accept()\n"
						+ "return nil\n"  // FIXME: runtime currently does log.Fatal on error
						+ "}\n"
						+ "\n"
						+ "func (ini *" + epkindTypeName + ") Dial(rolename string, id int, req transport.Transport) error {\n"
						+ "ini." + RPCoreSTApiGenConstants.GO_ENDPOINT_ENDPOINT + "." + RPCoreSTApiGenConstants.GO_CONNECTION_MAP + "[rolename][id] = req.Connect()\n"
						+ "return nil\n"  // FIXME: runtime currently does log.Fatal on error
						+ "}\n";
						
					epkindFile += "\n"
							+ "func (ini *" + epkindTypeName + ") Run(f func(*Init) End) *End {\n"  // f specifies non-pointer End
							+ "ini.Use()\n"  // FIXME: int-counter linearity
							+ "ini." + RPCoreSTApiGenConstants.GO_ENDPOINT_ENDPOINT + ".CheckConnection()\n"
							
							// FIXME: factor out with RPCoreSTStateChanApiBuilder#buildActionReturn (i.e., returning initial state)
							+ ((this.apigen.job.selectApi && this.apigen.variants.get(rname).get(variant).init.getStateKind() == EStateKind.POLY_INPUT)
									? "end := f(ini.NewBranchInit())\n"  // FIXME: factor out with RPCoreSTSessionApiBuilder#getSuccStateChan and RPCoreSTSelectStateBuilder#getPreamble
									: "end := f(&Init{ new(session.LinearResource), ini })\n")  // cf. state chan builder  // FIXME: chan struct reuse

							+ "return &end\n"
							+ "}";
				
					res.put(basedir + RPCoreSTApiGenerator.getEndpointKindPackageName(variant) + "/" + RPCoreSTApiGenerator.getEndpointKindTypeName(simpname, variant) + ".go",
							"package " + RPCoreSTApiGenerator.getEndpointKindPackageName(variant) + "\n" + epkindFile);
			}
		}
	}
}
