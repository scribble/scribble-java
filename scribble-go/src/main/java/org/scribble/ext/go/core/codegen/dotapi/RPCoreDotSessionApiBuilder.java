package org.scribble.ext.go.core.codegen.dotapi;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.ast.ProtocolDecl;
import org.scribble.ext.go.core.codegen.dotapi.RPCoreDotApiGen.Mode;
import org.scribble.ext.go.core.codegen.statetype.RPCoreSTApiGenConstants;
import org.scribble.ext.go.core.codegen.statetype.RPCoreSTApiGenerator;
import org.scribble.ext.go.core.model.endpoint.RPCoreEState;
import org.scribble.ext.go.core.type.RPFamily;
import org.scribble.ext.go.core.type.RPInterval;
import org.scribble.ext.go.core.type.RPRoleVariant;
import org.scribble.ext.go.type.index.RPBinIndexExpr;
import org.scribble.ext.go.type.index.RPIndexExpr;
import org.scribble.ext.go.type.index.RPIndexInt;
import org.scribble.ext.go.type.index.RPIndexIntPair;
import org.scribble.ext.go.type.index.RPIndexSelf;
import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.ext.go.util.Smt2Translator;
import org.scribble.model.endpoint.EGraph;
import org.scribble.model.endpoint.EStateKind;
import org.scribble.type.kind.Global;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.Role;

// Build Session API for this.apigen.selfs
// rp-core Session API = Protocol API + Endpoint Kind APIs 
// CHECKME: can be factored out between standard and -dotapi State Chan APIs?
public class RPCoreDotSessionApiBuilder
{
	private final RPCoreDotApiGen apigen;

	protected final Map<RPRoleVariant, Set<RPCoreEState>> reachable;
	protected final Map<RPRoleVariant, Map<Integer, String>> stateChanNames;  // State2

	private static final Comparator<RPCoreEState> ESTATE_COMP = new Comparator<RPCoreEState>()
			{
				@Override
				public int compare(RPCoreEState o1, RPCoreEState o2)
				{
					return new Integer(o1.id).compareTo(o2.id);
				}
			};

	public RPCoreDotSessionApiBuilder(RPCoreDotApiGen apigen)
	{
		this.apigen = apigen;
		this.reachable = Collections.unmodifiableMap(getReachable());
		this.stateChanNames = Collections.unmodifiableMap(makeStateChanNames()
				.entrySet().stream().collect(Collectors.toMap(
						e -> e.getKey(), 
						e -> Collections.unmodifiableMap(e.getValue())))
		);
	}

	private Map<RPRoleVariant, Set<RPCoreEState>> getReachable()
	{
		Map<RPRoleVariant, Set<RPCoreEState>> res = new HashMap<>();

		// For each variant/EFSM
		for (Entry<RPRoleVariant, EGraph> e : (Iterable<Entry<RPRoleVariant, EGraph>>)
				this.apigen.selfs.stream()
						.map(r -> this.apigen.variants.get(r))
						.flatMap(x -> x.entrySet().stream())::iterator)
		{
			RPRoleVariant v = e.getKey();
			EGraph g = e.getValue();
			
			Set<RPCoreEState> rs = new HashSet<>();
			rs.add((RPCoreEState) g.init);
			rs.addAll(RPCoreEState.getReachableStates((RPCoreEState) g.init));  // FIXME: cast needed to select correct static
			res.put(v, Collections.unmodifiableSet(new HashSet<>(rs)));
		}	
		
		return res;
	}

	private Map<RPRoleVariant, Map<Integer, String>> makeStateChanNames()
	{
		Map<RPRoleVariant, Map<Integer, String>> res = new HashMap<>();

		for (RPRoleVariant v : this.reachable.keySet())
		{
			EGraph g = this.apigen.variants.get(v.getName()).get(v);
			Set<RPCoreEState> todo = this.reachable.get(v);
			Map<Integer, String> curr = new HashMap<>();
			res.put(v, curr);
			
			for (RPCoreEState s : new HashSet<>(todo))
			{
				if (s.hasNested())  // Nested inits
				{
					RPCoreEState nested = s.getNested();
					curr.put(nested.id, "Init_" + nested.id);
					todo.remove(nested);
				}
			}

			// Handling top-level init/end must come after handling nested
			curr.put(g.init.id, "Init");
			todo.remove(g.init);
			if (g.term != null && g.term.id != g.init.id)  // Latter is for single top-level foreach states
			{
				todo.remove(g.term);  // Remove top-level term from rs
				curr.put(g.term.id, "End");
			}

			int[] counter = {2};  // 1 is Init
			todo.forEach(s ->
			{
				String n = s.isTerminal()  // Includes nested terminals
						? (s.hasNested() ? "End_" + s.id : "End")  // "Nesting" or "plain" terminal (all "plain" terminals can be treated the same)
						: "State" + counter[0]++;
				curr.put(s.id, n);
			});
		}

		return res;
	}

	//@Override
	public Map<String, String> build()  // FIXME: factor out
	{
		//Module mod = this.apigen.job.getContext().getModule(this.apigen.proto.getPrefix());
		//ProtocolDecl<Global> gpd = mod.getProtocolDecl(this.apigen.proto.getSimpleName());
		Map<String, String> res = new HashMap<>();  // filepath -> source
		res.putAll(buildProtocolApi());
		res.putAll(buildEndpointKindApi());
		return res;
	}
	
	private Map<String, String> buildProtocolApi()//ProtocolDecl<Global> gpd, Map<String, String> res, String indexType)
	{
		//List<Role> rolenames = this.apigen.selfs;
		
		String packageDecl;
		String imports;
		String protoTypeAndConstr;
		Map<RPRoleVariant, LinkedHashMap<RPFamily, String>> epKindConstrFns = new HashMap<>();
		
		String rootPackName = this.apigen.namegen.getApiRootPackageName();
		packageDecl = "// Package " + rootPackName + " is the generated API for the " + this.apigen.proto + " protocol.\n"
				+ "// Use functions in this package to create instances of role variants.\n"
				+ "package " + rootPackName + "\n";
		
		imports = getProtocolApiImports();
		
		String protoTypeName = this.apigen.namegen.getProtoTypeName();
		protoTypeAndConstr = makeProtoTypeAndConstr(protoTypeName);

		//for (Role rname : rolenames)
		{
			for (RPRoleVariant variant : (Iterable<RPRoleVariant>) this.apigen.variants.values().stream()
					.flatMap(x -> x.keySet().stream()).sorted(RPRoleVariant.COMPARATOR)::iterator)
			{
				boolean isCommonEndpointKind = false;//this.apigen.isCommonEndpointKind(variant);
				
				// HACK: setting family to null for common endpoint kind
				Set<RPFamily> families = /*isCommonEndpointKind
						? Stream.of((RPFamily) null).collect(Collectors.toSet())  // FIXME HACK: when isCommonEndpointKind, just loop once (to make one New)
						:*/ this.apigen.families.keySet().stream().filter(f -> f.variants.contains(variant)).collect(Collectors.toSet());

				for (RPFamily family : (Iterable<RPFamily>) families.stream().sorted(RPFamily.COMPARATOR)::iterator)  
						// FIXME: use family to make accept/dial
				{	
					// Factor out with below
					RPFamily orig = this.apigen.subsum.containsKey(family) ? this.apigen.subsum.get(family) : family;
					RPRoleVariant subbdbyus = null;
					for (RPRoleVariant subbd : this.apigen.aliases.keySet())
					{
						Map<RPFamily, RPRoleVariant> ais = this.apigen.aliases.get(subbd);
						if (ais.containsKey(orig) && ais.get(orig).equals(variant))
						{
							if (subbdbyus != null)
							{
								throw new RuntimeException("[rp-core] [-dotapi] TODO: " + variant + " is subsuming multiple variants ("
										+ subbdbyus + ", " + subbd + ") in one family (" + orig + ")");
										// CHECKME: just generalise subbdbyus to a Collection?
							}
							subbdbyus = subbd;  // We subsumed "subbd", so we need to inherit all their peers
						}
					}
					
					String cnstrFn = makeEndpointKindConstr(protoTypeName, variant, family, subbdbyus, isCommonEndpointKind);
					
					LinkedHashMap<RPFamily, String> tmp = epKindConstrFns.get(variant);
					if (tmp == null)
					{
						tmp = new LinkedHashMap<>();
						epKindConstrFns.put(variant, tmp);
					}
					tmp.put(family, cnstrFn);
				}
			}
		}

		String protoFile = packageDecl
				+ "\n" + imports
				+ "\n" + protoTypeAndConstr;
		for (RPRoleVariant v : epKindConstrFns.keySet())
		{
			for (Entry<RPFamily, String> e : epKindConstrFns.get(v).entrySet()) 
			{
				protoFile += "\n" + e.getValue();
			}
		}
		Map<String, String> res = new HashMap<>();
		res.put(getProtocolFilePath() + protoTypeName + ".go", protoFile);
		return res;
	}
	
	private String getProtocolApiImports()
	{
		String res = "import \"strconv\"\n"
				+ "import \"" + RPCoreDotApiGenConstants.RUNTIME_UTIL_PACKAGE + "\"\n";
		if (this.apigen.mode == Mode.IntPair)
		{
			res += "import \"" + RPCoreDotApiGenConstants.PAIR_RUNTIME_SESSION_PACKAGE + "\"\n";
		}
		
		for (Role rname : this.apigen.selfs)
		{
			for (RPRoleVariant v : this.apigen.variants.get(rname).keySet())
			{
				String epkindPackName = this.apigen.namegen.getEndpointKindPackageName(v);
				/* // Cf. getEndpointKindFilePath
				boolean isCommonEndpointKind = //this.apigen.families.keySet().stream().allMatch(f -> f.left.contains(v));  // No: doesn't consider dial/accept
						//this.apigen.families.keySet().stream().filter(f -> f.left.contains(v)).distinct().count() == 1;  // No: too conservative -- should be about required peer endpoint kinds (to dial/accept with)
						//this.apigen.peers.get(v).size() == 1;  // Cf.
						this.apigen.isCommonEndpointKind(v);
				/*if (isCommonEndpointKind)
				{
					res += "import " + epkindPackName
							+ " \"" + this.apigen.packpath + "/" + this.apigen.getApiRootPackageName()  // "Absolute" -- cf. getProtocol/EndpointKindFilePath, "relative"
							+ "/" + epkindPackName + "\"\n";
				}
				else*/
				{
					for (RPFamily f : this.apigen.families.keySet())
					{
						if (f.variants.contains(v))
						{
							String fampack = this.apigen.namegen.getFamilyPackageName(f);
							String alias = fampack + "_" + epkindPackName;
							res += "import " + alias + " \""
									+ this.apigen.namegen.getApiRootPackageFullPath()  // "Absolute" -- cf. getProtocol/EndpointKindFilePath, "relative"
									+ "/" + fampack
									+ "/" + epkindPackName 
									+ "\"\n";
						}
					}
				}
			}
		}
		
		res += "\nvar _ = strconv.Itoa\n";
		res += "var _ = util.IsectIntIntervals\n";
		
		return res;
	}

	private String makeProtoTypeAndConstr(String protoTypeName)
	{
		String res =
				// Protocol type
				  "// " + protoTypeName + " is an instance of the " + this.apigen.proto + " protocol.\n"
				+ "type " + protoTypeName + " struct {\n"
				+ "}\n"
				
				// session.Protocol interface
				+ "\n" 
				+ "func (*" + protoTypeName +") IsProtocol() {\n"
				+ "}\n"
				
				// Protocol type constructor
				+ "\n"
				+ "// New returns a new instance of the protocol.\n"
				+ "func New() *" + protoTypeName + " {\n"
				+ "return &" + protoTypeName + "{ }\n"
				+ "}\n";
		return res;
	}

	public String makeEndpointKindConstr(String protoTypeName, RPRoleVariant variant, RPFamily family,
			RPRoleVariant subbdbyus, boolean isCommonEndpointKind)
	{
		String epkindPackName = this.apigen.namegen.getEndpointKindPackageName(variant);
		String famPackName = (isCommonEndpointKind ? "" : this.apigen.namegen.getFamilyPackageName(family) + "_");
		String importAlias = famPackName + epkindPackName;  // Cf. getProtocolApiImports
		String cnstrFnName =  "New_" + importAlias;
		String epkindTypeName = this.apigen.namegen.getEndpointKindTypeName(variant);
		List<RPIndexVar> ivars = getParameters(variant);

		// Endpoint Kind constructor -- makes index var value maps
		String sig = "func (p *" + protoTypeName + ") " + cnstrFnName
				+ "(" + ivars.stream().filter(x -> x instanceof RPIndexSelf)
								.map(v -> v + " " + this.apigen.mode.indexType + ", ").collect(Collectors.joining(""))
						+ RPIndexSelf.SELF.name + " " + this.apigen.mode.indexType + ")"
						
				// Endpoint Kind Type
				+ " *" + importAlias + "." + epkindTypeName;
		
		String body = 
				  makeFamilyCheck(isCommonEndpointKind ? this.apigen.families.keySet().iterator().next() : family, ivars)
				+ makeSelfIvalsCheck(variant, ivars, subbdbyus)
				+ makeSelfCoivalsCheck(variant, ivars, subbdbyus)
				
				// Endpoint Kind API constructor params, cf. buildEndpointKindApi
				+ "return " + importAlias + ".New" + "(p"
						+ ivars.stream().filter(x -> x instanceof RPIndexSelf).map(x -> ", " + x).collect(Collectors.joining(""))
						+ ", " + RPIndexSelf.SELF.name + ")\n";

		return 
				"// " + cnstrFnName + " returns a new instance of " + epkindTypeName + " role variant.\n"
				+ sig + " {\n" + body + "}\n";
	}

	private String makeFamilyCheck(RPFamily fam, List<RPIndexVar> ivars)
	{
		// Refactor ival kind sensitive procedures into Mode?
		switch (this.apigen.mode)
		{
			case Int:      return makeIntIvalsFamilyCheck(fam, ivars);
			case IntPair:  return makeIntPairIvalsFamilyCheck(fam, ivars);
			default:       throw new RuntimeException("[rp-core] [-dotapi] Shouldn't get in here: " + this.apigen.mode);
		}
	}

	private String makeIntPairIvalsFamilyCheck(RPFamily fam, List<RPIndexVar> ivars)
	{
		return "util.CheckSat(\"" + fam.makeXiSmt2(this.apigen.smt2t, new HashSet<>(ivars)) + "\")\n";
	}

	private String makeIntIvalsFamilyCheck(RPFamily fam, List<RPIndexVar> ivars)
	{
		if (this.apigen.mode != Mode.Int)
		{
			throw new RuntimeException("Shouldn't get in here: " + this.apigen.mode);
		}
		String ivalkind = "IntInterval";

		if (ivars.isEmpty())
		{
			return "";
		}

		String res = "";
		res += "var tmp []util." + ivalkind + "\n";
		res += "var tmp2 []util." + ivalkind + "\n";
		for (RPRoleVariant v : fam.variants)
		{
			String tmp = makeIntIvals(v, ivars);
			if (tmp != null)
			{
				res += tmp;
				res += makeIntCoIvals(v);
				res += "if util.Isect" + ivalkind + "s(tmp).SubIntIntervals(tmp2)"  // FIXME: non int intervals
						+ ".IsEmpty() {\n";
				res += makeParamsSelfPanic(ivars);
				res += "}\n";
			}
		}
		for (RPRoleVariant v : fam.covariants)
		{
			res += makeIntIvals(v, ivars);
			res += makeIntCoIvals(v);
			res += "if !util.Isect" + ivalkind + "s(tmp).SubIntIntervals(tmp2)"
					+ ".IsEmpty() {\n";
			res += makeParamsSelfPanic(ivars);
			res += "}\n";
		}
		return res;
	}

	private static String makeIntIvals(RPRoleVariant variant, List<RPIndexVar> ivars)
	{
		String ivalkind = "IntInterval";
		List<String> ivals = new LinkedList<>();
		for (RPInterval x : variant.intervals) 
		{
			if (ivars.containsAll(x.getIndexVars()))
			{
				String start = x.start.toGoString();
				String end = x.end.toGoString();
				ivals.add("util." + ivalkind + "{" + start + ", " + end + "}");
			}
		}
		return ivals.isEmpty() ? null : "tmp = []util." + ivalkind
				+ "{" + ivals.stream().collect(Collectors.joining(", ")) + "}\n";
	}

	private static String makeIntCoIvals(RPRoleVariant variant)
	{
		String ivalkind = "IntInterval";
		List<String> coivals = new LinkedList<>();
		variant.cointervals.forEach(x -> 
		{
			String start = x.start.toGoString();
			String end = x.end.toGoString();
			coivals.add("util." + ivalkind + "{" + start + ", " + end + "}");
		});
		return "tmp2 = []util." + ivalkind + "{" + coivals.stream().collect(Collectors.joining(", ")) + "}\n";
	}

	private String makeSelfIvalsCheck(RPRoleVariant vvv, List<RPIndexVar> ivars, RPRoleVariant subbd)
	{
		String res = "if "
				+ vvv.intervals.stream().map(x ->
						((this.apigen.mode == Mode.Int)
							? "(self < " + x.start.toGoString() + ") || (self > " + x.end.toGoString() + ")"
							: "(self.Lt(" + makeGoIntPairIndexExpr(x.start, "") + ")) || (self.Gt(" + makeGoIntPairIndexExpr(x.end, "") + "))"
						)
				).collect(Collectors.joining(" || ")) + " {\n";
		if (subbd != null)
		{
			res += makeSelfIvalsCheck(subbd, ivars, null);
		}
		else
		{
			res += makeParamsSelfPanic(ivars);
		}
		res += "}\n";
		return res;
	}

	private String makeSelfCoivalsCheck(RPRoleVariant vvv, List<RPIndexVar> ivars, RPRoleVariant subbd)
	{
		String res = "";
		if (!vvv.cointervals.isEmpty()) {
		res += "if "
				+ vvv.cointervals.stream().map(x ->
						((this.apigen.mode == Mode.Int)
							? "(self >= " + x.start.toGoString() + ") && (self <= " + x.end.toGoString() + ")"
							: "(self.Gte(" + makeGoIntPairIndexExpr(x.start, "") + ")) && (self.Lt(" + makeGoIntPairIndexExpr(x.end, "") + "))"
						)
				).collect(Collectors.joining(" || ")) + " {\n";
		}
		if (subbd != null)
		{
			res += makeSelfCoivalsCheck(subbd, ivars, null);
		}
		else
		{
			if (!vvv.cointervals.isEmpty())
			{
				res += makeParamsSelfPanic(ivars);
			}
		}
		if (!vvv.cointervals.isEmpty())
		{	
			res += "}\n";
		}
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

	private Map<String, String> buildEndpointKindApi()
	{
		Map<String, String> res = new HashMap<>();

		//GProtocolName simpname = this.apigen.proto.getSimpleName();
		List<Role> roles = this.apigen.selfs;
		
		..HERE

		String epkindImports = "\n";  // Package decl done later (per variant)
		switch (this.apigen.mode)
		{
			case Int:  epkindImports += "import \"" + RPCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_SESSION_PACKAGE + "\"\n";  break;
			case IntPair:  epkindImports += "import \"" + RPCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_PAIR_SESSION_PACKAGE + "\"\n";  break;
			default:  throw new RuntimeException("Shouldn't get in here: " + this.apigen.mode);
		}
		epkindImports += "import \"" + RPCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_TRANSPORT_PACKAGE + "\"\n";
		epkindImports += "import \"strconv\"\n";

		epkindImports += "\nvar _ = strconv.Itoa\n";
		
		// Endpoint Kind API per role variant
		for (Role rname : roles)
		{
			for (RPRoleVariant variant : this.apigen.variants.get(rname).keySet()) 
			{
				for (RPFamily family :
						(Iterable<RPFamily>) 
								this.apigen.families.keySet().stream().filter(f -> f.variants.contains(variant))::iterator)  
				{	
					
					// Factor out with above
					RPFamily orig = (this.apigen.subsum.containsKey(family)) ? this.apigen.subsum.get(family) : family;
					RPRoleVariant subbdbyus = null;
					for (RPRoleVariant subbd : this.apigen.aliases.keySet())
					{
						Map<RPFamily, RPRoleVariant> ais = this.apigen.aliases.get(subbd);
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
					
					String epkindFile = "//" + family.variants.toString() + "\n\n";

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
							+ ivars.stream().map(x -> x + " " + indexType + "\n").collect(Collectors.joining(""));

					if (this.apigen.job.parForeach)
					{
						epkindFile += "Params map[int]map[string]" + indexType + "\n";  // FIXME: currently used to record foreach params (and provide access to user)
					}
					else
					{
						epkindFile += "Params map[string]" + indexType + "\n";  // FIXME: currently used to record foreach params (and provide access to user)
					}
							
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

					epkindFile +=
								// FIXME TODO: case objects?
							 this.reachable.get(variant).stream().sorted(ESTATE_COMP)
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
									}).collect(Collectors.joining());
					
					if (this.apigen.job.parForeach)
					{
						epkindFile += "Thread int\n";  // FIXME: deprecate -- recorded in state chan instead
					}

					epkindFile += "}\n"

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
									+ ivars.stream().map(x ->  x + ",\n").collect(Collectors.joining(""));

					if (this.apigen.job.parForeach)
					{
						epkindFile += "make(map[int]map[string]" + indexType + "),\n";
					}
					else
					{
						epkindFile += "make(map[string]" + indexType + "),\n";  // Trailing comma needed
					}

					epkindFile +=
									//+ this.apigen.stateChanNames.get(variant).values().stream().distinct().map(k -> "nil,\n").collect(Collectors.joining())
									  // FIXME: factor out with above
									 this.reachable.get(variant).stream().sorted(ESTATE_COMP)
											.flatMap(s -> 
													{
														String n = this.stateChanNames.get(variant).get(s.id);
														return s.hasNested()
															? Stream.of(n, s.isTerminal() ? "End" : n + "_")  // FIXME: factor out with RPCoreSTStateChanApiBuilder#getStateChanName
															: Stream.of(n);
													})
											.distinct()
											.map(k -> "nil,\n").collect(Collectors.joining());
					
					if (this.apigen.job.parForeach)
					{
						epkindFile += "1,\n";
					}
									
					epkindFile += "}\n"

									  // FIXME: factor out with above
									+ this.reachable.get(variant).stream().sorted(ESTATE_COMP)
											.flatMap(s -> 
													{
														String n = this.stateChanNames.get(variant).get(s.id);
														return s.hasNested()
															? Stream.of(n, s.isTerminal() ? "End" : n + "_")  // FIXME: factor out with RPCoreSTStateChanApiBuilder#getStateChanName
															: Stream.of(n);
													})
											.distinct()  // CHECKME: for End's?
											.map(n ->
													{
														// FIXME TODO: case objects?
														return "ep._" + n + " = " + makeStateChanInstance(this.apigen, n, "ep", "1");
																// cf. state chan builder  // CHECKME: reusing pre-created chan structs OK for Err handling?
													}
												)
												/*.map(s -> 
												{
													String n = this.stateChanNames.get(variant).get(s.id);
													String tmp = makeStateChanInstance(this.apigen, n);
													if (s.hasNested())
													{
														tmp = tmp + (s.isTerminal() 
																? makeStateChanInstance(this.apigen, n + "_")
																: makeStateChanInstance(this.apigen, "End"));
													}
													return tmp;
												})*/
												.collect(Collectors.joining());
										
							if (this.apigen.job.parForeach)
							{
								epkindFile += "ep.Params[ep.Thread] = make(map[string]" + indexType + ")\n";
							}

							epkindFile += "return ep\n";

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
					
					epkindFile = makeDialsAccepts(indexType, variant, family, orig, ivars, epkindTypeName, epkindFile, peers);

					if (subbdbyus != null)
					{
						Set<RPRoleVariant> peers2 = this.apigen.peers.get(subbdbyus).get(orig);
						peers2.removeAll(peers);
						epkindFile = makeDialsAccepts(indexType, variant, family, orig, ivars, epkindTypeName, epkindFile, peers2);
					}

					/*if (subbdbyus != null)
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
					}*/
					
					
							
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

							// TODO: Factor out with RPCoreSTStateChanApiBuilder#makeReturnSuccStateChan
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
							"// Generated API for the " + variant.getName() + getHumanReadableName(variant) + " role variant.\n"
							+ "package " + RPCoreSTApiGenerator.getEndpointKindPackageName(variant) + "\n" + epkindFile);
				}
			}
		}
		
		return res;
	}

	// TODO: factor out with above
	private String makePeerIdCheck1(RPRoleVariant vvv, List<RPIndexVar> ivars, RPRoleVariant subbd)
	{
		String res = "if "
				+ vvv.intervals.stream().map(x ->
						((this.apigen.mode == Mode.Int)
							? "(id < " + makeGoIntIndexExpr(x.start, "ini.") + ") || (id > " + makeGoIntIndexExpr(x.end, "ini.") + ")"
							: "(id.Lt(" + makeGoIntPairIndexExpr(x.start, "ini.") + ")) || (id.Gt(" + makeGoIntPairIndexExpr(x.end, "ini.") + "))"
						)
				).collect(Collectors.joining(" || ")) + " {\n";
		if (subbd != null)
		{
			res += makePeerIdCheck1(subbd, ivars, null);
		}
		else
		{
			res +=	makeParamsPeerIdPanic(ivars);
		}
		res += "}\n";
		return res;
	}

	private String makePeerIdCheck2(RPRoleVariant vvv, List<RPIndexVar> ivars, RPRoleVariant subbd)
	{

		String res = "";
		if (!vvv.cointervals.isEmpty()) {
		res += "if "
				+ vvv.cointervals.stream().map(x ->
						((this.apigen.mode == Mode.Int)
							? "(id >= " + makeGoIntIndexExpr(x.start, "ini.") + ") && (id <= " + makeGoIntIndexExpr(x.end, "ini.") + ")"
							: "(id.Gte(" + makeGoIntPairIndexExpr(x.start, "ini.") + ")) && (id.Lt(" + makeGoIntPairIndexExpr(x.end, "ini.") + "))"
						)
				).collect(Collectors.joining(" || ")) + " {\n";
		}
		if (subbd != null)
		{
			res += makePeerIdCheck2(subbd, ivars, null);
		}
		else
		{
			if (!vvv.cointervals.isEmpty())
			{
				res +=	makeParamsPeerIdPanic(ivars);
			}
		}
		if (!vvv.cointervals.isEmpty())
		{	
			res += "}\n";
		}
		return res;
	}

	private String makeParamsPeerIdPanic(List<RPIndexVar> ivars)
	{
		return "panic(\"Invalid params/id: \" + "
				+ ivars.stream().filter(x -> !x.name.equals("self")).map(x ->
						((this.apigen.mode == Mode.Int)
								? "strconv.Itoa(ini." + x.toGoString() + ")"
								: "ini." + x.toGoString() + ".String()"
						) + " + \", \" + ").collect(Collectors.joining(""))
				+ ((this.apigen.mode == Mode.Int) ? "strconv.Itoa(id)" : "id.String()")
		+ ")\n";
	}

	/*public static String makeStateChanInstance(RPCoreSTApiGenerator apigen, EState s)
	{
		return makeStateChanInstance(apigen, this.stateChanNames.get(variant).get(s.id));
	}*/

	public static String makeStateChanInstance(RPCoreSTApiGenerator apigen, String n, String ep, String id)
	{
		return ((n.equals("End"))  // Terminal foreach will be suffixed (and need linear check) // FIXME: factor out properly
				? "&End{ nil, 0, "  // Now same as below?
				: "&" + n + "{ nil, "
						+ (apigen.job.parForeach
							? " new(" + RPCoreSTApiGenConstants.GO_LINEARRESOURCE_TYPE + "),"
							: (n.equals("Init") ? " 1," : " 0,"))
						)
		
				+ ep + (apigen.job.parForeach ? ", " + id : "") + " }\n";
	}

	private String makeDialsAccepts(String indexType, RPRoleVariant variant,
			RPFamily family, RPFamily orig,
			List<RPIndexVar> ivars, String epkindTypeName, String epkindFile, Set<RPRoleVariant> peers)
	{
		for (RPRoleVariant v : peers)
		{
			RPRoleVariant pp = v;
			
			// TODO: factor out with above
			RPFamily peerorig = (this.apigen.subsum.containsKey(family)) ? this.apigen.subsum.get(family) : family;
			RPRoleVariant subbdbypeer = null;
			for (RPRoleVariant subbd : this.apigen.aliases.keySet())
			{
				Map<RPFamily, RPRoleVariant> ais = this.apigen.aliases.get(subbd);
				if (ais.containsKey(peerorig) && ais.get(peerorig).equals(variant))
				{
					subbdbypeer = subbd;  // We can connect to peer or who they subbd
					break;
				}
			}
			
			if (this.apigen.aliases.containsKey(v))
			{
				Map<RPFamily, RPRoleVariant> ali = this.apigen.aliases.get(v);
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
							
					+ makePeerIdCheck1(v, ivars, subbdbypeer)		
					+ makePeerIdCheck2(v, ivars, subbdbypeer)		
							
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

					+ makePeerIdCheck1(v, ivars, subbdbypeer)		
					+ makePeerIdCheck2(v, ivars, subbdbypeer)		

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
		return epkindFile;
	}

	private String getHumanReadableName(RPRoleVariant variant)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("["); // array index notation.
		for (RPInterval iv : variant.intervals)
		{
			sb.append("{");
			if (iv.isSingleton())
			{
				sb.append(iv.start.toString());
			}
			else
			{
				sb.append(iv.start.toString());
				sb.append(",..,");
				sb.append(iv.end.toString());
			}
			sb.append("}");
			sb.append("∩");
		}
		sb.deleteCharAt(sb.length() - 1);
		if (variant.cointervals.size() > 0)
		{
			sb.append(" - ");
			for (RPInterval iv : variant.cointervals)
			{
				sb.append("{");
				if (iv.isSingleton())
				{
					sb.append(iv.start.toString());
				}
				else
				{
					sb.append(iv.start.toString());
					sb.append(",..,");
					sb.append(iv.end.toString());
				}
				sb.append("}");
				sb.append("∪");
			}
			sb.deleteCharAt(sb.length() - 1);
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
		return ivars.stream().sorted(RPIndexVar.COMPARATOR).collect(Collectors.toList());
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
	public String getEndpointKindFilePath(RPFamily family, RPRoleVariant variant)
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

	// Factor out with RPCoreSTStateChanApiGenerator#generateIndexExpr
	private String makeGoIntIndexExpr(RPIndexExpr e, String ctxt)
	{
		if (e instanceof RPIndexInt)
		{
			return e.toGoString();
		}
		else if (e instanceof RPIndexVar)
		{
			return ctxt + e.toGoString();
		}
		else if (e instanceof RPBinIndexExpr)
		{
			RPBinIndexExpr b = (RPBinIndexExpr) e;
			// TODO: factor out
				String op;
				switch (b.op)
				{
					case Add:  op = " + ";  break;
					case Subt:  op = " - ";  break;
					case Mult:
					default:  throw new RuntimeException("[rp-core] Shouldn't get in here: " + e);
				}
			 return "(" + makeGoIntIndexExpr(b.left, ctxt) + op + "(" + makeGoIntIndexExpr(b.right, ctxt) + "))";
		}
		else
		{
			throw new RuntimeException("[rp-core] Shouldn't get in here: " + e);
		}
	}

	private String makeGoIntPairIndexExpr(RPIndexExpr e, String ctxt)
	{
		if (e instanceof RPIndexIntPair)
		{
			return e.toGoString();
		}
		else if (e instanceof RPIndexVar)
		{
			return ctxt + e.toGoString();
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
			 return "(" + makeGoIntPairIndexExpr(b.left, ctxt) + "." + op + "(" + makeGoIntPairIndexExpr(b.right, ctxt) + "))";
		}
		else
		{
			throw new RuntimeException("[rp-core] Shouldn't get in here: " + e);
		}
	}
}
