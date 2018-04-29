package org.scribble.ext.go.core.codegen.statetype3;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ast.Module;
import org.scribble.ast.ProtocolDecl;
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

	public static final Comparator<RPIndexVar> IVAR_COMP = new Comparator<RPIndexVar>()
			{
				@Override
				public int compare(RPIndexVar i1, RPIndexVar i2)
				{
					return i1.toString().compareTo(i2.toString());
				}
			};

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

		for (Role rname : rolenames)
		{
			for (RPRoleVariant variant : this.apigen.variants.get(rname).keySet())
			{
				/*RPCoreIndexVarCollector coll = new RPCoreIndexVarCollector(this.apigen.job);
				try
				{
					gpd.accept(coll);  // FIXME: should be lpd -- not currently used due to using RPCoreType
				}
				catch (ScribbleException e)
				{
					throw new RuntimeException("[rp-core] Shouldn't get in here: ", e);
				}
				List<RPIndexVar> ivars = coll.getIndexVars().stream().sorted().collect(Collectors.toList());*/
				
				List<RPIndexVar> ivars = this.apigen.projections.get(rname).get(variant)
						.getIndexVars().stream().sorted(IVAR_COMP)
						.collect(Collectors.toList());  // N.B., params only from action subjects (not self)
				ivars.addAll(variant.getIndexVars());  // Do variant params subsume projection params?  (vice versa not true -- e.g., param needed to check self)
				String epkindTypeName = RPCoreSTApiGenerator.getEndpointKindTypeName(simpname, variant);
						
				// Endpoint Kind constructor -- makes index var value maps
				String tmp = "func (p *" + simpname + ") New" + "_" + epkindTypeName
						+ "(" + ivars.stream().map(v -> v + " int, ").collect(Collectors.joining("")) + "self int" + ")"
						+ " *_" + RPCoreSTApiGenerator.getGeneratedRoleVariantName(variant) + "." + epkindTypeName + " {\n"
						/*+ "params := make(map[string]int)\n"
						//+ decls.iterator().next().params
						+ ivars
								.stream().map(x -> "params[\"" + x + "\"] = " + x + "\n").collect(Collectors.joining(""))*/
						+ "return _" + RPCoreSTApiGenerator.getEndpointKindPackageName(variant) + ".New" + "(p" //", params"
								+ ivars.stream().map(x -> ", " + x).collect(Collectors.joining(""))
								+ ", self)\n"
						+ "}\n";
				
				protoFile += "\n" + tmp;
			}
		}

		res.put(basedir + simpname + ".go", protoFile);
	}

	// FIXME: should be lpd
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
			for (RPRoleVariant variant : this.apigen.variants.get(rname).keySet()) 
			{
				/*RPCoreIndexVarCollector coll = new RPCoreIndexVarCollector(this.apigen.job);
				try
				{
					gpd.accept(coll);  // FIXME: should be lpd -- not currently used due to using RPCoreType
				}
				catch (ScribbleException e)
				{
					throw new RuntimeException("[rp-core] Shouldn't get in here: ", e);
				}
				List<RPIndexVar> ivars = coll.getIndexVars().stream().sorted().collect(Collectors.toList());*/

				List<RPIndexVar> ivars = this.apigen.projections.get(rname).get(variant)
						.getIndexVars().stream().sorted(IVAR_COMP).collect(Collectors.toList());  // N.B., params only from action subjects (not self)
				ivars.addAll(variant.getIndexVars());  // Do variant params subsume projection params?  (vice versa not true -- e.g., param needed to check self)
				String epkindTypeName = RPCoreSTApiGenerator.getEndpointKindTypeName(simpname, variant);
				
				String epkindFile = epkindImports + "\n"
						
						// Endpoint Kind type
						+ "type " + epkindTypeName + " struct {\n"
						+ RPCoreSTApiGenConstants.GO_MPCHAN_PROTO + " " + RPCoreSTApiGenConstants.GO_PROTOCOL_TYPE + "\n"
						+ "Self int\n"
						+ "*" + RPCoreSTApiGenConstants.GO_LINEARRESOURCE_TYPE + "\n"
						+ RPCoreSTApiGenConstants.GO_MPCHAN_SESSCHAN + " *" + RPCoreSTApiGenConstants.GO_MPCHAN_TYPE + "\n"
						//+ "Params map[string]int\n"
						+ ivars.stream().map(x -> x + " int\n").collect(Collectors.joining(""))
						+ "}\n"

						// Endpoint Kind type constructor -- makes connection maps
						+ "\n"
						+ "func New(p " + RPCoreSTApiGenConstants.GO_PROTOCOL_TYPE + ", " //+"params map[string]int," 
								+ ivars.stream().map(x -> x + " int, ").collect(Collectors.joining(""))
								+ "self int) *" + epkindTypeName + " {\n"
						/*+ "conns := make(map[string]map[int]transport.Channel)\n"
						+ this.apigen.variants.entrySet().stream()
								.map(e -> "conns[\"" + e.getKey().getLastElement() + "\"] = " + "make(map[int]transport.Channel)\n")
								.collect(Collectors.joining(""))*/
						+ "return &" + epkindTypeName + "{p, self, &" + RPCoreSTApiGenConstants.GO_LINEARRESOURCE_TYPE
								+ "{}, " + RPCoreSTApiGenConstants.GO_MPCHAN_CONSTRUCTOR + "(self, "//conns)" //"params
								+ "[]string{" + roles.stream().map(x -> "\"" + x + "\"").collect(Collectors.joining(", ")) + "})"
								+ ivars.stream().map(x -> ", " + x).collect(Collectors.joining(""))
								+ "}\n"
						+ "}\n";

						// Dial/Accept methdos -- FIXME: internalise peers
						/*+ "\n"
						+ "func (ini *" + epkindTypeName + ") Accept(rolename string, id int, acc transport.Transport) error {\n"
						+ "ini." + RPCoreSTApiGenConstants.GO_ENDPOINT_ENDPOINT + "."
								+ RPCoreSTApiGenConstants.GO_CONNECTION_MAP + "[rolename][id] = acc.Accept()\n"
						+ "return nil\n"  // FIXME: runtime currently does log.Fatal on error
						+ "}\n"
						+ "\n"
						+ "func (ini *" + epkindTypeName + ") Dial(rolename string, id int, req transport.Transport) error {\n"
						+ "ini." + RPCoreSTApiGenConstants.GO_ENDPOINT_ENDPOINT + "."
								+ RPCoreSTApiGenConstants.GO_CONNECTION_MAP + "[rolename][id] = req.Connect()\n"
						+ "return nil\n"  // FIXME: runtime currently does log.Fatal on error
						+ "}\n";*/
				for (RPRoleVariant v : (Iterable<RPRoleVariant>)
						this.apigen.variants.values().stream()
								.flatMap(m -> m.keySet().stream())::iterator)
				{
					if (!v.equals(variant))  // FIXME: endpoint families -- and id value checks
					{
						String r = v.getLastElement();
						String vname = RPCoreSTApiGenerator.getGeneratedRoleVariantName(v);
						epkindFile += "\n"
								+ "func (ini *" + epkindTypeName + ") " + vname + "_Accept(id int"
										+ ", ss " + RPCoreSTApiGenConstants.GO_SCRIB_LISTENER_TYPE
										+ ", sfmt " + RPCoreSTApiGenConstants.GO_FORMATTER_TYPE + ") error {\n"
								+ "c, err := ss.Accept()\n"
								+ "sfmt.Wrap(c)\n"
								+ "ini." + RPCoreSTApiGenConstants.GO_MPCHAN_SESSCHAN + "."
										+ RPCoreSTApiGenConstants.GO_MPCHAN_CONN_MAP + "[\"" + r + "\"][id] = c\n"  // CHECKME: connection map keys (cf. variant?)
								+ "ini." + RPCoreSTApiGenConstants.GO_MPCHAN_SESSCHAN + "."
										+ RPCoreSTApiGenConstants.GO_MPCHAN_FORMATTER_MAP + "[\"" + r + "\"][id] = sfmt\n"
								+ "return err\n"  // FIXME: runtime currently does log.Fatal on error
								+ "}\n"
								+ "\n"
								+ "func (ini *" + epkindTypeName + ") " + vname + "_Dial(id int"
										+ ", host string, port int"
										+ ", dialler func (string, int) (" + RPCoreSTApiGenConstants.GO_SCRIB_BINARY_CHAN_TYPE + ", error)"
										+ ", sfmt " + RPCoreSTApiGenConstants.GO_FORMATTER_TYPE + ") error {\n"
								+ "c, err := dialler(host, port)\n"
								+ "sfmt.Wrap(c)\n"
								+ "ini." + RPCoreSTApiGenConstants.GO_MPCHAN_SESSCHAN + "."
										+ RPCoreSTApiGenConstants.GO_MPCHAN_CONN_MAP + "[\"" + r + "\"][id] = c\n"  // CHECKME: connection map keys (cf. variant?)
								+ "ini." + RPCoreSTApiGenConstants.GO_MPCHAN_SESSCHAN + "."
										+ RPCoreSTApiGenConstants.GO_MPCHAN_FORMATTER_MAP + "[\"" + r + "\"][id] = sfmt\n"  // FIXME: factor out with Accept
								+ "return err\n"  // FIXME: runtime currently does log.Fatal on error
								+ "}\n";
					}
				}
						
				// Top-level Run method  // FIXME: add session completion check
				/*EGraph g = this.apigen.variants.get(variant.getName()).get(variant);
				String endName = g.init.isTerminal() ? "Init" : "End";  // FIXME: factor out -- cf. RPCoreSTStateChanApiBuilder#makeSTStateName(*/
				String init = "Init_" + this.apigen.variants.get(variant.getName()).get(variant).init;
				epkindFile += "\n"
						+ "func (ini *" + epkindTypeName + ") Run(f func(*" + init + ") End) *End {\n"  // f specifies non-pointer End
						+ "defer ini." + RPCoreSTApiGenConstants.GO_MPCHAN_SESSCHAN + ".Close()\n"
						+ "ini.Use()\n"  // FIXME: int-counter linearity
						+ "ini." + RPCoreSTApiGenConstants.GO_MPCHAN_SESSCHAN + ".CheckConnection()\n"
						
						// FIXME: factor out with RPCoreSTStateChanApiBuilder#buildActionReturn (i.e., returning initial state)
						// (FIXME: factor out with RPCoreSTSessionApiBuilder#getSuccStateChan and RPCoreSTSelectStateBuilder#getPreamble)
						+ ((this.apigen.job.selectApi && this.apigen.variants.get(rname).get(variant).init.getStateKind() == EStateKind.POLY_INPUT)
								? "end := f(newBranch" + init + "(ini))\n"
								: "end := f(&" + init + "{ new(" + RPCoreSTApiGenConstants.GO_LINEARRESOURCE_TYPE + "), ini })\n")  // cf. state chan builder  // FIXME: chan struct reuse

						+ "return &end\n"
						+ "}";
			
				res.put(basedir + RPCoreSTApiGenerator.getEndpointKindPackageName(variant) + "/" + RPCoreSTApiGenerator.getEndpointKindTypeName(simpname, variant) + ".go",
						"package " + RPCoreSTApiGenerator.getEndpointKindPackageName(variant) + "\n" + epkindFile);
			}
		}
	}
}
