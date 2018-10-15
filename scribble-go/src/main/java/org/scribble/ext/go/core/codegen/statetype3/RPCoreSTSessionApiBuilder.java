package org.scribble.ext.go.core.codegen.statetype3;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.ast.Module;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ext.go.core.codegen.statetype3.RPCoreSTApiGenerator.Mode;
import org.scribble.ext.go.core.model.endpoint.RPCoreEState;
import org.scribble.ext.go.core.type.RPInterval;
import org.scribble.ext.go.core.type.RPRoleVariant;
import org.scribble.ext.go.type.index.RPBinIndexExpr;
import org.scribble.ext.go.type.index.RPIndexExpr;
import org.scribble.ext.go.type.index.RPIndexIntPair;
import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.model.endpoint.EGraph;
import org.scribble.model.endpoint.EStateKind;
import org.scribble.type.kind.Global;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.Role;
import org.scribble.util.Pair;

// Build Session API for this.apigen.selfs
// RP Session API = Protocol API + Endpoint Kind APIs 
// Cf. STStateChanApiBuilder
public class RPCoreSTSessionApiBuilder
{
	private final RPCoreSTApiGenerator apigen;

	/*... move stateChanNames (and peers?) here
	... cache reachable states, use for state chan pre-creation (to include intermed states -- and case objects?)
	... do "global" linearity counter*/
	
	protected final Map<RPRoleVariant, Set<RPCoreEState>> reachable = new HashMap<>();
	protected final Map<RPRoleVariant, Map<Integer, String>> stateChanNames;  // State2
	//private Map<RPRoleVariant, Map<Integer, String>> foreachNames = new HashMap<>();    // State2_ intermediary name (N.B. RPCoreSTStateChanApiBuilder swaps them around for convenience)

	private static final Comparator<RPIndexVar> IVAR_COMP = new Comparator<RPIndexVar>()
			{
				@Override
				public int compare(RPIndexVar i1, RPIndexVar i2)
				{
					return i1.toString().compareTo(i2.toString());
				}
			};
			
	private static final Comparator<RPCoreEState> ESTATE_COMP = new Comparator<RPCoreEState>()
			{
				@Override
				public int compare(RPCoreEState o1, RPCoreEState o2)
				{
					return new Integer(o1.id).compareTo(o2.id);
				}
			};

	public RPCoreSTSessionApiBuilder(RPCoreSTApiGenerator apigen)
	{
		this.apigen = apigen;

		this.stateChanNames = Collections.unmodifiableMap(makeStateChanNames()
				.entrySet().stream().collect(Collectors.toMap(
						e -> e.getKey(), 
						e -> Collections.unmodifiableMap(e.getValue())))
		);
	}

	private Map<RPRoleVariant, Map<Integer, String>> makeStateChanNames()
	{
		Map<RPRoleVariant, Map<Integer, String>> names = new HashMap<>();
		for (Entry<RPRoleVariant, EGraph> e : (Iterable<Entry<RPRoleVariant, EGraph>>)
				this.apigen.selfs.stream()
						.map(r -> this.apigen.variants.get(r))
						.flatMap(v -> v.entrySet().stream())::iterator)
		{
			int[] counter = {2};
			RPRoleVariant v = e.getKey();
			EGraph g = e.getValue();
			
			Map<Integer, String> curr = new HashMap<>();
			//Map<Integer, String> inames = new HashMap<>();
			names.put(v, curr);
			//this.foreachNames.put(v, inames);
			Set<RPCoreEState> rs = RPCoreEState.getReachableStates((RPCoreEState) g.init);  // FIXME: cast needed to select correct static
			rs.add((RPCoreEState) g.init);  // Adding for following loop; removed again later
			
			this.reachable.put(v, Collections.unmodifiableSet(new HashSet<>(rs)));
			
			for (RPCoreEState s : new HashSet<>(rs))
			{
				if (s.hasNested())  // Nested inits
				{
					RPCoreEState nested = s.getNested();
					curr.put(nested.id, "Init_" + nested.id);
					rs.remove(nested);
				}
			}
			// Handling top-level init/end must come after handling nested
			curr.put(g.init.id, "Init");
			rs.remove(g.init);
			if (g.term != null && g.term.id != g.init.id)
			{
				rs.remove(g.term);  // Remove top-level term from rs
				curr.put(g.term.id, "End");
			}
			rs.forEach(s ->
			{
				String n = s.isTerminal()
						? (s.hasNested() ? "End_" + s.id : "End")  // Nested or "regular" term
						: "State" + counter[0]++;
				/*if (s.hasNested())
				{
					inames.put(s.id, n);
					n = n + "_";
				}*/
				curr.put(s.id, n);
			});
		}
		return names;
	}

	//@Override
	public Map<String, String> build()  // FIXME: factor out
	{
		String indexType; 
		switch (this.apigen.mode)
		{
			case Int:     indexType = "int";  break; // Factor into the mode?
			case IntPair: indexType = "session2.Pair";  break;
			default: throw new RuntimeException("Shouldn't get in here: " + this.apigen.mode);
		}

		Module mod = this.apigen.job.getContext().getModule(this.apigen.proto.getPrefix());
		ProtocolDecl<Global> gpd = mod.getProtocolDecl(this.apigen.proto.getSimpleName());
		Map<String, String> res = new HashMap<>();  // filepath -> source
		buildProtocolApi(gpd, res, indexType);
		buildEndpointKindApi(gpd, res, indexType);
		return res;
	}
	
	private void buildProtocolApi(ProtocolDecl<Global> gpd, Map<String, String> res, String indexType)
	{
		GProtocolName simpname = this.apigen.proto.getSimpleName();
		List<Role> rolenames = //gpd.header.roledecls.getRoles();
				this.apigen.selfs;

		// roles
		String protoFile =

				    "// Package " + this.apigen.getApiRootPackageName() + " is the generated API for the " + this.apigen.proto.getPrefix().getSimpleName().toString() + "." + this.apigen.getApiRootPackageName() + " protocol.\n"
				+ "// Use functions in this package to create instances of role variants.\n"
				+ "package " + this.apigen.getApiRootPackageName() + "\n"
				+ "\n"
				+ "import \"strconv\"\n"
				+ "import \"" + RPCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_UTIL_PACKAGE + "\"\n"
				+ "\n";

		if (this.apigen.mode == Mode.IntPair)
		{
			protoFile += "import \"" + RPCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_PAIR_SESSION_PACKAGE + "\"\n";
		}
							
		protoFile +=
				// Import Endpoint Kind APIs -- FIXME: CL args
				  (rolenames.stream().map(rname ->
						{
							Set<RPRoleVariant> variants = this.apigen.variants.get(rname).keySet();
							return
									variants.stream().map(v -> 
									{
										String epkindPackName = RPCoreSTApiGenerator.getEndpointKindPackageName(v);
										// Cf. getEndpointKindFilePath
										boolean isCommonEndpointKind = //this.apigen.families.keySet().stream().allMatch(f -> f.left.contains(v));  // No: doesn't consider dial/accept
												//this.apigen.families.keySet().stream().filter(f -> f.left.contains(v)).distinct().count() == 1;  // No: too conservative -- should be about required peer endpoint kinds (to dial/accept with)
												//this.apigen.peers.get(v).size() == 1;  // Cf.
												this.apigen.isCommonEndpointKind(v);

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
		
		protoFile += "\nvar _ = strconv.Itoa\n";
					
		protoFile += "\n"
				// Protocol type
				+ "// " + simpname + " is an instance of the " + this.apigen.proto.getPrefix().getSimpleName().toString() + "." + this.apigen.getApiRootPackageName() + " protocol.\n"
				+ "type " + simpname + " struct {\n"
				+ "}\n"
				
				// session.Protocol interface
				+ "\n" 
				+ "func (*" + simpname +") IsProtocol() {\n"
				+ "}\n"
				
				// Protocol type constructor
				+ "\n"
				+ "// New returns a new instance of the protocol.\n"
				+ "func New() *" + simpname + " {\n"
				+ "return &" + simpname + "{ }\n"
				+ "}\n";
		
		for (Role rname : rolenames)
		{
			for (RPRoleVariant variant : this.apigen.variants.get(rname).keySet())
			{
				boolean isCommonEndpointKind = this.apigen.isCommonEndpointKind(variant);
				
				// HACK: setting family to null for common endpoint kind
				Set<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>> families = isCommonEndpointKind
						? Stream.of((Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>) null).collect(Collectors.toSet())  // FIXME HACK: when isCommonEndpointKind, just loop once (to make one New)
						: this.apigen.families.keySet().stream().filter(f -> f.left.contains(variant)).collect(Collectors.toSet());

				for (Pair<Set<RPRoleVariant>, Set<RPRoleVariant>> family :
						families)  // FIXME: use family to make accept/dial
				{	
					
					// Factor out with below
					Pair<Set<RPRoleVariant>, Set<RPRoleVariant>> orig = (this.apigen.subsum.containsKey(family)) ? this.apigen.subsum.get(family) : family;
					RPRoleVariant subbdbyus = null;
					for (RPRoleVariant subbd : this.apigen.aliases.keySet())
					{
						Map<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>, RPRoleVariant> ais = this.apigen.aliases.get(subbd);
						if (ais.containsKey(orig) && ais.get(orig).equals(variant))
						{
							subbdbyus = subbd;  // We subsumed "subbd", so we need to inherit all their peers
							break;
						}
					}
					
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
					List<RPIndexVar> ivars = getParameters(variant);
					String epkindTypeName = RPCoreSTApiGenerator.getEndpointKindTypeName(simpname, variant);

					String fnName =  "New_" + (isCommonEndpointKind ? "" : this.apigen.getFamilyPackageName(family) + "_") + epkindTypeName;

					// Endpoint Kind constructor -- makes index var value maps
					String tmp = "// " + fnName + " returns a new instance of " + epkindTypeName + " role variant.\n"
							+ "func (p *" + simpname + ") " + fnName // FIXME: factor out common variants between families
							+ "(" + ivars.stream().filter(x -> !x.name.equals("self"))  // CHECKME: can check for RPIndexSelf instead?
							    .map(v -> v + " " + indexType + ", ").collect(Collectors.joining("")) + "self " + indexType + ")"
							+ " *"
							+ (isCommonEndpointKind ? "" : this.apigen.getFamilyPackageName(family) + "_")
							+ RPCoreSTApiGenerator.getGeneratedRoleVariantName(variant)  // FIXME: factor out (and separate type name from package name)
							+ "." + epkindTypeName
							+ " {\n";
							/*+ "params := make(map[string]int)\n"
							//+ decls.iterator().next().params
							+ ivars
									.stream().map(x -> "params[\"" + x + "\"] = " + x + "\n").collect(Collectors.joining(""))*/
					
					tmp += makeFamilyCheck((family == null) ? this.apigen.families.keySet().iterator().next() : family, ivars);  // FIXME: due to above family null hack
					tmp += makeSelfCheck(variant, ivars, subbdbyus);

					tmp += "return "
									+ (isCommonEndpointKind ? "" : this.apigen.getFamilyPackageName(family) + "_")
									+ RPCoreSTApiGenerator.getEndpointKindPackageName(variant)  // FIXME: factor out
									+ ".New" + "(p" //", params"
									+ ivars.stream().filter(x -> !x.name.equals("self"))  // CHECKME: can check for RPIndexSelf instead?
									    .map(x -> ", " + x).collect(Collectors.joining(""))
									+ ", self)\n"
							+ "}\n";
					
					protoFile += "\n" + tmp;
				}
			}
		}

		res.put(getProtocolFilePath() + simpname + ".go", protoFile);
	}

	private String makeFamilyCheck(Pair<Set<RPRoleVariant>, Set<RPRoleVariant>> fff, List<RPIndexVar> ivars)
	{
		String res = "";
		String ivalkind;
		switch (this.apigen.mode)
		{
			case Int:  ivalkind = "IntInterval";  break;
			case IntPair:  ivalkind = "IntPairInterval";  break;
			default:  throw new RuntimeException("Shouldn't get in here: " + this.apigen.mode);
		}

		res += "var tmp []util." + ivalkind + "\n";
		Function<RPRoleVariant, String> makeIvals = vvv ->
		{
			List<String> ivals = new LinkedList<>();
			vvv.intervals.forEach(x -> 
			{
				String start = (this.apigen.mode == Mode.Int) ? x.start.toGoString() : makeGoIndexExpr(x.start);
				String end = (this.apigen.mode == Mode.Int) ? x.end.toGoString() : makeGoIndexExpr(x.end);
				ivals.add("util." + ivalkind + "{" + start + ", " + end + "}");
			});
			return "tmp = []util." + ivalkind + "{" + ivals.stream().collect(Collectors.joining(", ")) + "}\n";
		};

		for (RPRoleVariant v : fff.left)
		{
			res += makeIvals.apply(v);
			res += "if util.Isect" + ivalkind + "s(tmp).IsEmpty() {\n";
			res += makeParamsSelfPanic(ivars);
			res += "}\n";
		}
		for (RPRoleVariant v : fff.right)
		{
			res += makeIvals.apply(v);
			res += "if !util.Isect" + ivalkind + "s(tmp).IsEmpty() {\n";
			res += makeParamsSelfPanic(ivars);
			res += "}\n";
		}
		return res;
	}

	private String makeSelfCheck(RPRoleVariant vvv, List<RPIndexVar> ivars, RPRoleVariant subbd)
	{
		String res = "if "
				+ vvv.intervals.stream().map(x ->
						((this.apigen.mode == Mode.Int)
							? "(self < " + x.start.toGoString() + ") || (self > " + x.end.toGoString() + ")"
							: "(self.Lt(" + makeGoIndexExpr(x.start) + ")) || (self.Gt(" + makeGoIndexExpr(x.end) + "))"
						)
				).collect(Collectors.joining(" || ")) + " {\n";
		
		if (subbd != null)
		{
			res += makeSelfCheck(subbd, ivars, null);
		}
		else
		{
			res +=	makeParamsSelfPanic(ivars);
		}

		res += "}\n";
		
		return res;
	}

	private String makeParamsSelfPanic(List<RPIndexVar> ivars)
	{
		return "panic(\"Invalid params/self: \" + "
				+ ivars.stream().filter(x -> !x.name.equals("self")).map(x ->
						((this.apigen.mode == Mode.Int)
								? "strconv.Itoa(" + x.toGoString() + ")"
								: x.toGoString() + ".String()"
						) + " + \", \" + ").collect(Collectors.joining(""))
				+ ((this.apigen.mode == Mode.Int) ? "strconv.Itoa(self)" : "self.String()")
		+ ")\n";
	}

	// Factor out with RPCoreSTStateChanApiGenerator#generateIndexExpr
	private String makeGoIndexExpr(RPIndexExpr e)
	{
		if (e instanceof RPIndexIntPair || e instanceof RPIndexVar)
		{
			return e.toGoString();
		}
		else if (e instanceof RPBinIndexExpr)
		{
			RPBinIndexExpr b = (RPBinIndexExpr) e;
			// TODO: factor out
					String op;
					switch (b.op)
					{
						case Add:  op = "Plus";  break;
						case Subt:  op = "Sub";  break;
						case Mult:
						default:  throw new RuntimeException("[rp-core] Shouldn't get in here: " + e);
					}
				 return "(" + makeGoIndexExpr(b.left) + "." + op + "(" + makeGoIndexExpr(b.right) + "))";
		}
		else
		{
			throw new RuntimeException("[rp-core] Shouldn't get in here: " + e);
		}
	}

	// FIXME: should be lpd
	private void buildEndpointKindApi(ProtocolDecl<Global> gpd, Map<String, String> res, String indexType)
	{
		GProtocolName simpname = this.apigen.proto.getSimpleName();
		List<Role> roles = //gpd.header.roledecls.getRoles();
				this.apigen.selfs;

		String epkindImports = "\n";  // Package decl done later (per variant)
		switch (this.apigen.mode)
		{
			case Int:  epkindImports += "import \"" + RPCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_SESSION_PACKAGE + "\"\n";  break;
			case IntPair:  epkindImports += "import \"" + RPCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_PAIR_SESSION_PACKAGE + "\"\n";  break;
			default:  throw new RuntimeException("Shouldn't get in here: " + this.apigen.mode);
		}
		epkindImports += "import \"" + RPCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_TRANSPORT_PACKAGE + "\"\n";
		
		// Endpoint Kind API per role variant
		for (Role rname : roles)
		{
			for (RPRoleVariant variant : this.apigen.variants.get(rname).keySet()) 
			{
				for (Pair<Set<RPRoleVariant>, Set<RPRoleVariant>> family :
						(Iterable<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>>) 
								this.apigen.families.keySet().stream().filter(f -> f.left.contains(variant))::iterator)  
				{	
					
					// Factor out with above
					Pair<Set<RPRoleVariant>, Set<RPRoleVariant>> orig = (this.apigen.subsum.containsKey(family)) ? this.apigen.subsum.get(family) : family;
					RPRoleVariant subbdbyus = null;
					for (RPRoleVariant subbd : this.apigen.aliases.keySet())
					{
						Map<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>, RPRoleVariant> ais = this.apigen.aliases.get(subbd);
						if (ais.containsKey(orig) && ais.get(orig).equals(variant))
						{
							subbdbyus = subbd;  // We subsumed "subbd", so we need to inherit all their peers
							break;
						}
					}

					//System.out.println("ffff: " + family + "\n" + variant + " ,, " + peers);
					
					
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

					List<RPIndexVar> ivars = getParameters(variant);

					String epkindTypeName = RPCoreSTApiGenerator.getEndpointKindTypeName(simpname, variant);
					
					String epkindFile = "//" + family.left.toString() + "\n\n";

					epkindFile +=
							epkindImports + "\n"
							
							// Endpoint Kind type
							+ "type " + epkindTypeName + " struct {\n"
							+ RPCoreSTApiGenConstants.GO_MPCHAN_PROTO + " " + RPCoreSTApiGenConstants.GO_PROTOCOL_TYPE + "\n";

					switch (this.apigen.mode)
					{
						case Int:  epkindFile += "Self int\n";  break;
						case IntPair:  epkindFile += "Self session2.Pair\n";  break;
						default:  throw new RuntimeException("Shouldn't get in here: " + this.apigen.mode);
					}

					epkindFile +=
							  "*" + RPCoreSTApiGenConstants.GO_LINEARRESOURCE_TYPE + "\n"  // This is for the Endpoint itself
							+ "lin uint64\n"

							+ RPCoreSTApiGenConstants.GO_MPCHAN_SESSCHAN + " *" + RPCoreSTApiGenConstants.GO_MPCHAN_TYPE + "\n"
							+ ivars.stream().map(x -> x + " " + indexType + "\n").collect(Collectors.joining(""))

							+ "Params map[string]" + indexType + "\n"  // FIXME: currently used to record foreach params (and provide access to user)
							
							/*+ this.apigen.stateChanNames.get(variant).entrySet().stream().sorted(
									new Comparator<Entry<Integer, String>>()
										{
											@Override
											public int compare(Entry<Integer, String> o1, Entry<Integer, String> o2)
											{
												return o1.getKey().compareTo(o2.getKey());
											}
										}
									)*/
								// FIXME TODO: case objects?
							+ this.reachable.get(variant).stream().sorted(ESTATE_COMP)
									//.map(s -> this.stateChanNames.get(s.id))
									.flatMap(s ->
										{
											String n = this.stateChanNames.get(variant).get(s.id);
											return s.hasNested()
												? Stream.of(n, s.isTerminal() ? "End" : n + "_")  // FIXME: factor out with RPCoreSTStateChanApiBuilder#getStateChanName
												: Stream.of(n);
										})
									.distinct()
									.map(n ->
									{
										return "_" + n + " *" + n + "\n";
									}).collect(Collectors.joining())

							+ "}\n"

							// Endpoint Kind type constructor -- makes connection maps
							+ "\n"
							+ "func New(p " + RPCoreSTApiGenConstants.GO_PROTOCOL_TYPE + ", " //+"params map[string]int," 
									+ ivars.stream().filter(x -> !x.name.equals("self")).map(x -> x + " " + indexType + ", ").collect(Collectors.joining(""))  // CHECKME: can check for RPIndexSelf instead?
									+ "self " + indexType + ") *" + epkindTypeName + " {\n"
							/*+ "conns := make(map[string]map[int]transport.Channel)\n"
							+ this.apigen.variants.entrySet().stream()
									.map(e -> "conns[\"" + e.getKey().getLastElement() + "\"] = " + "make(map[int]transport.Channel)\n")
									.collect(Collectors.joining(""))*/
							+ "ep := &" + epkindTypeName + "{\n"
									+ "p,\n"
									+ "self,\n"

									+ "&" + RPCoreSTApiGenConstants.GO_LINEARRESOURCE_TYPE + "{},\n"  // For Endpoint itself
									+ "1,\n"

									+ RPCoreSTApiGenConstants.GO_MPCHAN_CONSTRUCTOR + "(self, "//conns)" //"params
											+ "[]string{" + roles.stream().map(x -> "\"" + x + "\"").collect(Collectors.joining(", ")) + "}),\n"
									+ ivars.stream().map(x ->  x + ",\n").collect(Collectors.joining(""))
									+ "make(map[string]" + indexType + "),\n"  // Trailing comma needed

									//+ this.apigen.stateChanNames.get(variant).values().stream().distinct().map(k -> "nil,\n").collect(Collectors.joining())
									  // FIXME: factor out with above
									+ this.reachable.get(variant).stream().sorted(ESTATE_COMP)
											.flatMap(s -> 
													{
														String n = this.stateChanNames.get(variant).get(s.id);
														return s.hasNested()
															? Stream.of(n, s.isTerminal() ? "End" : n + "_")  // FIXME: factor out with RPCoreSTStateChanApiBuilder#getStateChanName
															: Stream.of(n);
													})
											.distinct()
											.map(k -> "nil,\n").collect(Collectors.joining())
									
									+ "}\n"

									  // FIXME: factor out with above
									+ this.reachable.get(variant).stream().sorted(ESTATE_COMP)
											.flatMap(s -> 
													{
														String n = this.stateChanNames.get(variant).get(s.id);
														return s.hasNested()
															? Stream.of(n, s.isTerminal() ? "End" : n + "_")  // FIXME: factor out with RPCoreSTStateChanApiBuilder#getStateChanName
															: Stream.of(n);
													})
											.distinct()
											.map(n ->
													{
														// FIXME TODO: case objects?
														return (n.equals("End"))  // Terminal foreach will be suffixed (and need linear check) // FIXME: factor out properly
																? "ep._End = &End{ nil, 0, ep }\n"  // Now same as below?
																: "ep._" + n + " = &" + n + "{ nil,"
																		//+ " new(RPCoreSTApiGenConstants.GO_LINEARRESOURCE_TYPE),"
																		+ (n.equals("Init") ? " 1," : " 0,")
																		+ " ep }\n"; 
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
							
					/*for (RPRoleVariant v : this.apigen.peers.get(variant))
					{
						if (//!v.equals(variant) &&  // No: e.g., pipe/ring middlemen
								family.left.contains(v))  // FIXME: endpoint families -- and id value checks
										// FIXME: should record peers according to families and lookup directly -- wouldn't need to re-check family inclusion here
						{*/
					
					Set<RPRoleVariant> peers = this.apigen.peers.get(variant).get(orig);
					
					for (RPRoleVariant v : peers)
					{
						RPRoleVariant pp = v;
						if (this.apigen.aliases.containsKey(v))
						{
							Map<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>, RPRoleVariant> ali = this.apigen.aliases.get(v);
							if (ali.containsKey(orig))
							{
								pp = ali.get(orig);  // Replace subsumed-peer by subsuming-peer
								if (peers.contains(pp))  // ...but skip if we're already peers with subsuming-peer
								{
									continue;
								}
							}
						}
						
						// Accept/Dial methods
						String r = pp.getLastElement();
						String vname = RPCoreSTApiGenerator.getGeneratedRoleVariantName(pp);
						epkindFile += "\n"
								+ "func (ini *" + epkindTypeName + ") " + vname + "_Accept(id " + indexType
										+ ", ss " + RPCoreSTApiGenConstants.GO_SCRIB_LISTENER_TYPE
										+ ", sfmt " + RPCoreSTApiGenConstants.GO_FORMATTER_TYPE + ") error {\n"
								+ "defer ini." + RPCoreSTApiGenConstants.GO_MPCHAN_SESSCHAN + "."
										+ RPCoreSTApiGenConstants.GO_MPCHAN_CONN_WG + ".Done()\n"
								+ "ini." + RPCoreSTApiGenConstants.GO_MPCHAN_SESSCHAN + "."
										+ RPCoreSTApiGenConstants.GO_MPCHAN_CONN_WG + ".Add(1)\n"
								+ "c, err := ss.Accept()\n"
								+ "if err != nil {\n"
								+ "return err\n"
								+ "}\n"
								+ "\n"
								+ "sfmt.Wrap(c)\n"
								+ "ini." + RPCoreSTApiGenConstants.GO_MPCHAN_SESSCHAN + "."
										+ RPCoreSTApiGenConstants.GO_MPCHAN_CONN_MAP + "[\"" + r + "\"][id] = c\n"  // CHECKME: connection map keys (cf. variant?)
								+ "ini." + RPCoreSTApiGenConstants.GO_MPCHAN_SESSCHAN + "."
										+ RPCoreSTApiGenConstants.GO_MPCHAN_FORMATTER_MAP + "[\"" + r + "\"][id] = sfmt\n"
								+ "return err\n"  // FIXME: runtime currently does log.Fatal on error
								+ "}\n"
								+ "\n"
								+ "func (ini *" + epkindTypeName + ") " + vname + "_Dial(id " + indexType
										+ ", host string, port int"
										+ ", dialler func (string, int) (" + RPCoreSTApiGenConstants.GO_SCRIB_BINARY_CHAN_TYPE + ", error)"
										+ ", sfmt " + RPCoreSTApiGenConstants.GO_FORMATTER_TYPE + ") error {\n"
								+ "defer ini." + RPCoreSTApiGenConstants.GO_MPCHAN_SESSCHAN + "."
										+ RPCoreSTApiGenConstants.GO_MPCHAN_CONN_WG + ".Done()\n"
								+ "ini." + RPCoreSTApiGenConstants.GO_MPCHAN_SESSCHAN + "."
										+ RPCoreSTApiGenConstants.GO_MPCHAN_CONN_WG + ".Add(1)\n"
								+ "c, err := dialler(host, port)\n"
								+ "if err != nil {\n"
								+ "return err\n"
								+ "}\n"
								+ "\n"
								+ "sfmt.Wrap(c)\n"
								+ "ini." + RPCoreSTApiGenConstants.GO_MPCHAN_SESSCHAN + "."
										+ RPCoreSTApiGenConstants.GO_MPCHAN_CONN_MAP + "[\"" + r + "\"][id] = c\n"  // CHECKME: connection map keys (cf. variant?)
								+ "ini." + RPCoreSTApiGenConstants.GO_MPCHAN_SESSCHAN + "."
										+ RPCoreSTApiGenConstants.GO_MPCHAN_FORMATTER_MAP + "[\"" + r + "\"][id] = sfmt\n"  // FIXME: factor out with Accept
								+ "return err\n"  // FIXME: runtime currently does log.Fatal on error
								+ "}\n";
						//}
					}

					if (subbdbyus != null)
					{
						// FIXME: factor out with above
						RPRoleVariant pp = subbdbyus;
						// Accept/Dial methods
						String r = pp.getLastElement();
						String vname = RPCoreSTApiGenerator.getGeneratedRoleVariantName(pp);
						epkindFile += "\n"
								+ "func (ini *" + epkindTypeName + ") " + vname + "_Accept(id " + indexType
										+ ", ss " + RPCoreSTApiGenConstants.GO_SCRIB_LISTENER_TYPE
										+ ", sfmt " + RPCoreSTApiGenConstants.GO_FORMATTER_TYPE + ") error {\n"
								+ "defer ini." + RPCoreSTApiGenConstants.GO_MPCHAN_SESSCHAN + "."
										+ RPCoreSTApiGenConstants.GO_MPCHAN_CONN_WG + ".Done()\n"
								+ "ini." + RPCoreSTApiGenConstants.GO_MPCHAN_SESSCHAN + "."
										+ RPCoreSTApiGenConstants.GO_MPCHAN_CONN_WG + ".Add(1)\n"
								+ "c, err := ss.Accept()\n"
								+ "if err != nil {\n"
								+ "return err\n"
								+ "}\n"
								+ "\n"
								+ "sfmt.Wrap(c)\n"
								+ "ini." + RPCoreSTApiGenConstants.GO_MPCHAN_SESSCHAN + "."
										+ RPCoreSTApiGenConstants.GO_MPCHAN_CONN_MAP + "[\"" + r + "\"][id] = c\n"  // CHECKME: connection map keys (cf. variant?)
								+ "ini." + RPCoreSTApiGenConstants.GO_MPCHAN_SESSCHAN + "."
										+ RPCoreSTApiGenConstants.GO_MPCHAN_FORMATTER_MAP + "[\"" + r + "\"][id] = sfmt\n"
								+ "return err\n"  // FIXME: runtime currently does log.Fatal on error
								+ "}\n"
								+ "\n"
								+ "func (ini *" + epkindTypeName + ") " + vname + "_Dial(id " + indexType
										+ ", host string, port int"
										+ ", dialler func (string, int) (" + RPCoreSTApiGenConstants.GO_SCRIB_BINARY_CHAN_TYPE + ", error)"
										+ ", sfmt " + RPCoreSTApiGenConstants.GO_FORMATTER_TYPE + ") error {\n"
								+ "defer ini." + RPCoreSTApiGenConstants.GO_MPCHAN_SESSCHAN + "."
										+ RPCoreSTApiGenConstants.GO_MPCHAN_CONN_WG + ".Done()\n"
								+ "ini." + RPCoreSTApiGenConstants.GO_MPCHAN_SESSCHAN + "."
										+ RPCoreSTApiGenConstants.GO_MPCHAN_CONN_WG + ".Add(1)\n"
								+ "c, err := dialler(host, port)\n"
								+ "if err != nil {\n"
								+ "return err\n"
								+ "}\n"
								+ "\n"
								+ "sfmt.Wrap(c)\n"
								+ "ini." + RPCoreSTApiGenConstants.GO_MPCHAN_SESSCHAN + "."
										+ RPCoreSTApiGenConstants.GO_MPCHAN_CONN_MAP + "[\"" + r + "\"][id] = c\n"  // CHECKME: connection map keys (cf. variant?)
								+ "ini." + RPCoreSTApiGenConstants.GO_MPCHAN_SESSCHAN + "."
										+ RPCoreSTApiGenConstants.GO_MPCHAN_FORMATTER_MAP + "[\"" + r + "\"][id] = sfmt\n"  // FIXME: factor out with Accept
								+ "return err\n"  // FIXME: runtime currently does log.Fatal on error
								+ "}\n";
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
							+ "func (ini *" + epkindTypeName + ") Run(f func(*" + init + ") " + endName + ") " + endName + " {\n"  // f specifies non-pointer End
							/*+ "defer ini." + RPCoreSTApiGenConstants.GO_MPCHAN_SESSCHAN + ".Close()\n"
							+ "ini.Use()\n"  // FIXME: int-counter linearity
							+ "ini." + RPCoreSTApiGenConstants.GO_MPCHAN_SESSCHAN + ".CheckConnection()\n"*/
							+ "defer ini.Close()\n"
							//+ "ini.Init()\n"
							
							// FIXME: factor out with RPCoreSTStateChanApiBuilder#buildActionReturn (i.e., returning initial state)
							// (FIXME: factor out with RPCoreSTSessionApiBuilder#getSuccStateChan and RPCoreSTSelectStateBuilder#getPreamble)
							/*+ ((this.apigen.job.selectApi && this.apigen.variants.get(rname).get(variant).init.getStateKind() == EStateKind.POLY_INPUT)
									? "return f(newBranch" + init + "(ini))\n"
									: //"end := f(&" + init + "{ nil, new(" + RPCoreSTApiGenConstants.GO_LINEARRESOURCE_TYPE + "), ini })\n")  
												// cf. state chan builder  // FIXME: chan struct reuse
										//"ini._" + init + ".id = 1\n" +
												"return f(ini._" + init + ")\n")*/
							+ "end := f(ini.Init())\n"
							+ "if end." + RPCoreSTApiGenConstants.GO_MPCHAN_ERR + " != nil {\n"
							+ "panic(end." + RPCoreSTApiGenConstants.GO_MPCHAN_ERR + ")\n"
							+ "}\n"
							+ "return end\n"

							+ "}";
					
					epkindFile += "\n\n"
							+ "func (ini *" + epkindTypeName + ") Init() *Init {\n"
							+ "ini.Use()\n"  // FIXME: int-counter linearity
							+ "ini." + RPCoreSTApiGenConstants.GO_MPCHAN_SESSCHAN + ".CheckConnection()\n"
							+ "return "
							+ ((this.apigen.job.selectApi && this.apigen.variants.get(rname).get(variant).init.getStateKind() == EStateKind.POLY_INPUT)
									? "newBranch" + init + "(ini)"
									: "ini._" + init) + "\n"
							+ "}";

					epkindFile += "\n\n"
							+ "func (ini *" + epkindTypeName + ") Close() {\n"
							+ "defer ini." + RPCoreSTApiGenConstants.GO_MPCHAN_SESSCHAN + ".Close()\n"
							+ "}";
				
					res.put(getEndpointKindFilePath(family, variant)
									+ "/" + RPCoreSTApiGenerator.getEndpointKindTypeName(simpname, variant) + ".go",
							"// Generated API for the " + variant.getName() + humanReadableName(variant) + " role variant.\n"
							+ "package " + RPCoreSTApiGenerator.getEndpointKindPackageName(variant) + "\n" + epkindFile);
				}
			}
		}
	}

	private String humanReadableName(RPRoleVariant variant) {
		StringBuilder sb = new StringBuilder();
		sb.append("["); // array index notation.
		for (RPInterval iv : variant.intervals) {
			sb.append("{");
		    if (iv.isSingleton()) {
		        sb.append(iv.start.toString());
			} else {
		        sb.append(iv.start.toString());
		        sb.append(",..,");
		        sb.append(iv.end.toString());
			}
			sb.append("}");
			sb.append("∩");
		}
        sb.deleteCharAt(sb.length()-1);
	    if (variant.cointervals.size() > 0) {
	        sb.append(" - ");
			for (RPInterval iv : variant.cointervals) {
				sb.append("{");
				if (iv.isSingleton()) {
					sb.append(iv.start.toString());
				} else {
					sb.append(iv.start.toString());
					sb.append(",..,");
					sb.append(iv.end.toString());
				}
				sb.append("}");
				sb.append("∪");
			}
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append("]"); // array index notation.
		return sb.toString();
	}

	private List<RPIndexVar> getParameters(RPRoleVariant variant)
	{
		List<RPIndexVar> ivars = this.apigen.projections.get(variant.getName()).get(variant)
				.getIndexVars().stream().collect(Collectors.toList());  // N.B., params only from action subjects (not self)
		ivars.addAll(variant.getIndexVars().stream().filter(x -> !ivars.contains(x))
				.collect(Collectors.toList()));  // Do variant params subsume projection params? -- nope: often projections [self] and variant [K]
		return ivars.stream().sorted(IVAR_COMP).collect(Collectors.toList());
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
				//this.apigen.peers.get(variant).size() == 1;
				this.apigen.isCommonEndpointKind(variant);
		String basedir = this.apigen.proto.toString().replaceAll("\\.", "/") + "/";  // Full name
		return basedir
				//+ "/" + this.apigen.getFamilyPackageName(family)
				+ (isCommonEndpointKind ? "" : "/" + this.apigen.getFamilyPackageName(family))
				+ "/" + RPCoreSTApiGenerator.getEndpointKindPackageName(variant);
				
				/*// "Syntactically" determining common endpoint kinds difficult because concrete peers depends on param (and foreachvar) values, e.g., M in PGet w.r.t. #F
				// Also, family factoring is more about dial/accept
				isCommonEndpointKind = true;
				Set<Role> peers = null;
				X: for (Pair<Set<RPRoleVariant>, Set<RPRoleVariant>> fam : this.apigen.families.keySet())
				{
					if (fam.left.contains(variant))
					{
						EGraph g = this.apigen.variants.get(rname).get(variant);
						Set<RPCoreEAction> as = RPCoreEState.getReachableActions((RPCoreEModelFactory) this.apigen.job.ef, (RPCoreEState) g.init);
						Set<Role> tmp = as.stream().map(a -> a.getPeer()).collect(Collectors.toSet());
						if (peers == null)
						{
							peers = tmp;
						}
						else if (!peers.equals(tmp))
						{
							isCommonEndpointKind = false;
							break X;
						}
					}
				}
				//*/
	}
}
