package org.scribble.ext.go.core.codegen.statetype3;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.ast.Module;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ext.go.core.type.RPRoleVariant;
import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.model.endpoint.EStateKind;
import org.scribble.type.kind.Global;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.Role;
import org.scribble.util.Pair;

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
		Map<String, String> res = new HashMap<>();  // filepath -> source
		buildProtocolApi(gpd, res);
		buildEndpointKindApi(gpd, res);
		return res;
	}
	
	private void buildProtocolApi(ProtocolDecl<Global> gpd, Map<String, String> res)
	{
		GProtocolName simpname = this.apigen.proto.getSimpleName();
		List<Role> rolenames = //gpd.header.roledecls.getRoles();
				this.apigen.selfs;

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
										// Cf. getEndpointKindFilePath
										boolean isCommonEndpointKind = //this.apigen.families.keySet().stream().allMatch(f -> f.left.contains(v));  // No: doesn't consider dial/accept
												//this.apigen.families.keySet().stream().filter(f -> f.left.contains(v)).distinct().count() == 1;  // No: too conservative -- should be about required peer endpoint kinds (to dial/accept with)
												this.apigen.peers.get(v).size() == 1;

										return isCommonEndpointKind
												? "import " + epkindPackName
														+ " \"" + this.apigen.packpath + "/" + this.apigen.getApiRootPackageName()  // "Absolute" -- cf. getProtocol/EndpointKindFilePath, "relative"
														+ "/" + epkindPackName + "\"\n"
												: this.apigen.families.keySet().stream().filter(f -> f.left.contains(v)).map(f ->
														{	
															return "import " + this.apigen.getFamilyPackageName(f) + "_" + epkindPackName
																	+ " \"" + this.apigen.packpath + "/" + this.apigen.getApiRootPackageName()  // "Absolute" -- cf. getProtocol/EndpointKindFilePath, "relative"
																	+ "/" + this.apigen.getFamilyPackageName(f)
																	+ "/" + epkindPackName + "\"\n";
														}).collect(Collectors.joining(""));
									}).collect(Collectors.joining(""));
						}).collect(Collectors.joining("")));
					
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
				boolean isCommonEndpointKind = this.apigen.peers.get(variant).size() == 1;
				Set<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>> families = isCommonEndpointKind
						? Stream.of((Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>) null).collect(Collectors.toSet())  // FIXME HACK: when isCommonEndpointKind, just loop once (to make one New)
						: this.apigen.families.keySet().stream().filter(f -> f.left.contains(variant)).collect(Collectors.toSet());
				for (Pair<Set<RPRoleVariant>, Set<RPRoleVariant>> family :
						families)  // FIXME: use family to make accept/dial
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
					String tmp = "func (p *" + simpname + ") New" + "_"
							+ (isCommonEndpointKind ? "" : this.apigen.getFamilyPackageName(family) + "_") 
							+ epkindTypeName  // FIXME: factor out common variants between families
							+ "(" + ivars.stream().map(v -> v + " int, ").collect(Collectors.joining("")) + "self int" + ")"
							+ " *"
							+ (isCommonEndpointKind ? "" : this.apigen.getFamilyPackageName(family) + "_")
							+ RPCoreSTApiGenerator.getGeneratedRoleVariantName(variant)  // FIXME: factor out (and separate type name from package name)
							+ "." + epkindTypeName
							+ " {\n"
							/*+ "params := make(map[string]int)\n"
							//+ decls.iterator().next().params
							+ ivars
									.stream().map(x -> "params[\"" + x + "\"] = " + x + "\n").collect(Collectors.joining(""))*/
							+ "return "
									+ (isCommonEndpointKind ? "" : this.apigen.getFamilyPackageName(family) + "_")
									+ RPCoreSTApiGenerator.getEndpointKindPackageName(variant)  // FIXME: factor out
									+ ".New" + "(p" //", params"
									+ ivars.stream().map(x -> ", " + x).collect(Collectors.joining(""))
									+ ", self)\n"
							+ "}\n";
					
					protoFile += "\n" + tmp;
				}
			}
		}

		res.put(getProtocolFilePath() + simpname + ".go", protoFile);
	}

	// FIXME: should be lpd
	private void buildEndpointKindApi(ProtocolDecl<Global> gpd, Map<String, String> res)
	{
		GProtocolName simpname = this.apigen.proto.getSimpleName();
		List<Role> roles = //gpd.header.roledecls.getRoles();
				this.apigen.selfs;

		String epkindImports = "\n"  // Package decl done later (per variant)
				+ "import \"" + RPCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_SESSION_PACKAGE + "\"\n"
				+ "import \"" + RPCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_TRANSPORT_PACKAGE + "\"\n";
		
		// Endpoint Kind API per role variant
		for (Role rname : roles)
		{
			for (RPRoleVariant variant : this.apigen.variants.get(rname).keySet()) 
			{
				for (Pair<Set<RPRoleVariant>, Set<RPRoleVariant>> family :
						(Iterable<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>>) 
								this.apigen.families.keySet().stream().filter(f -> f.left.contains(variant))::iterator)  // FIXME: use family to make accept/dial
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
							+ ivars.stream().map(x -> x + " int\n").collect(Collectors.joining(""))

							+ "Params map[string]int\n"  // FIXME: currently used to record foreach params (and provide access to user)
							
								// FIXME TODO: case objects?
							+ this.apigen.stateChanNames.get(variant).entrySet().stream().sorted(
									new Comparator<Entry<Integer, String>>()
										{
											@Override
											public int compare(Entry<Integer, String> o1, Entry<Integer, String> o2)
											{
												return o1.getKey().compareTo(o2.getKey());
											}
										}
									)
									.map(e -> e.getValue())
									.distinct()
									.map(n ->
									{
										return "_" + n + " *" + n + "\n";
									}).collect(Collectors.joining())

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
							+ "ep := &" + epkindTypeName + "{\n"
									+ "p,\n"
									+ "self,\n"
									+ "&" + RPCoreSTApiGenConstants.GO_LINEARRESOURCE_TYPE + "{},\n"
									+ RPCoreSTApiGenConstants.GO_MPCHAN_CONSTRUCTOR + "(self, "//conns)" //"params
											+ "[]string{" + roles.stream().map(x -> "\"" + x + "\"").collect(Collectors.joining(", ")) + "}),\n"
									+ ivars.stream().map(x ->  x + ",\n").collect(Collectors.joining(""))
									+ "make(map[string]int),\n"  // Trailing comma needed
									+ this.apigen.stateChanNames.get(variant).values().stream().distinct().map(k -> "nil,\n").collect(Collectors.joining())
									+ "}\n"
									+ this.apigen.stateChanNames.get(variant).entrySet().stream().sorted(
											new Comparator<Entry<Integer, String>>()
												{
													@Override
													public int compare(Entry<Integer, String> o1, Entry<Integer, String> o2)
													{
														return o1.getKey().compareTo(o2.getKey());
													}
												}
											)
											.map(e -> e.getValue())
											.distinct()
											.map(n ->
													{
														// FIXME TODO: case objects?
														return (n.equals("End"))  // Terminal foreach will be suffixed (and need linear check) // FIXME: factor out properly
																? "ep._End = &End{ nil, ep }\n"
																: "ep._" + n + " = &" + n + "{ nil, new(" + RPCoreSTApiGenConstants.GO_LINEARRESOURCE_TYPE + "), ep }\n"; 
																// cf. state chan builder  // CHECKME: reusing pre-created chan structs OK for Err handling?
													}
												).collect(Collectors.joining())
									+ "return ep\n";

							epkindFile += "}\n";

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

					//Map<RPRoleVariant, EGraph> map = this.apigen.variants.get(rname);

					/*for (RPRoleVariant v : (Iterable<RPRoleVariant>)
							this.apigen.variants.values().stream()
									.flatMap(m -> m.keySet().stream())::iterator)*/
					for (RPRoleVariant v : this.apigen.peers.get(variant))
					{
						if (!v.equals(variant) && family.left.contains(v))  // FIXME: endpoint families -- and id value checks
						{
							// Accept/Dial methods
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
					//String endName = g.init.isTerminal() ? "Init" : "End";  // FIXME: factor out -- cf. RPCoreSTStateChanApiBuilder#makeSTStateName(
					EState term = MState.getTerminal(g.init);
					String endName = (g.init.id == term.id ? "Init" : "End") + ((term != null && ((RPCoreEState) term).hasNested()) ? "_" + term.id : "");*/
					String endName = "End";
					String init = //"Init_" + this.apigen.variants.get(variant.getName()).get(variant).init;
							"Init";
					epkindFile += "\n"
							+ "func (ini *" + epkindTypeName + ") Run(f func(*" + init + ") " + endName + ") *" + endName + " {\n"  // f specifies non-pointer End
							+ "defer ini." + RPCoreSTApiGenConstants.GO_MPCHAN_SESSCHAN + ".Close()\n"
							+ "ini.Use()\n"  // FIXME: int-counter linearity
							+ "ini." + RPCoreSTApiGenConstants.GO_MPCHAN_SESSCHAN + ".CheckConnection()\n"
							
							// FIXME: factor out with RPCoreSTStateChanApiBuilder#buildActionReturn (i.e., returning initial state)
							// (FIXME: factor out with RPCoreSTSessionApiBuilder#getSuccStateChan and RPCoreSTSelectStateBuilder#getPreamble)
							+ ((this.apigen.job.selectApi && this.apigen.variants.get(rname).get(variant).init.getStateKind() == EStateKind.POLY_INPUT)
									? "end := f(newBranch" + init + "(ini))\n"
									: //"end := f(&" + init + "{ nil, new(" + RPCoreSTApiGenConstants.GO_LINEARRESOURCE_TYPE + "), ini })\n")  
												// cf. state chan builder  // FIXME: chan struct reuse
										"end := f(ini._" + init + ")\n")

							+ "return &end\n"
							+ "}";
				
					res.put(getEndpointKindFilePath(family, variant)
									+ "/" + RPCoreSTApiGenerator.getEndpointKindTypeName(simpname, variant) + ".go",
							"package " + RPCoreSTApiGenerator.getEndpointKindPackageName(variant) + "\n" + epkindFile);
				}
			}
		}
	}
	
	// Returns path to use as offset to -d
	// -- cf. packpath, "absolute" Go import path (github.com/...) -- would coincide if protocol full name (i.e., module) used "github.com/..."
	// FIXME: factor up to super -- cf. STStateChanApiBuilder#getStateChannelFilePath
	public String getProtocolFilePath()
	{
		String basedir = this.apigen.proto.toString().replaceAll("\\.", "/") + "/";  // Full name
		return basedir;
	}

	// Returns path to use as offset to -d
	// -- cf. packpath, "absolute" Go import path (github.com/...) -- would coincide if protocol full name (i.e., module) used "github.com/..."
	// FIXME: factor up to super -- cf. STStateChanApiBuilder#getStateChannelFilePath
	public String getEndpointKindFilePath(Pair<Set<RPRoleVariant>, Set<RPRoleVariant>> family, RPRoleVariant variant)
	{
		boolean isCommonEndpointKind = //this.apigen.families.keySet().stream().allMatch(f -> f.left.contains(variant));  // No: doesn't consider dial/accept
				//this.apigen.families.keySet().stream().filter(f -> f.left.contains(variant)).distinct().count() == 1;
				this.apigen.peers.get(variant).size() == 1;
		String basedir = this.apigen.proto.toString().replaceAll("\\.", "/") + "/";  // Full name
		return basedir
				//+ "/" + this.apigen.getFamilyPackageName(family)
				+ (isCommonEndpointKind ? "" : "/" + this.apigen.getFamilyPackageName(family))
				+ "/" + RPCoreSTApiGenerator.getEndpointKindPackageName(variant);
	}
}
