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
import java.util.stream.Stream.Builder;

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
import org.scribble.model.endpoint.EState;
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

	// FIXME: refactor into RPCoreDotApiGen
	protected final Map<RPRoleVariant, Set<RPCoreEState>> reachable;
	protected final Map<RPRoleVariant, Map<Integer, String>> stateChanNames;  // State2

	private static final Comparator<RPCoreEState> RPCOREESTATE_COMP = new Comparator<RPCoreEState>()
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
		String packDecl;
		String protoImports;
		String protoTypeAndConstr;
		Map<RPRoleVariant, LinkedHashMap<RPFamily, String>> epKindFctryFns = new HashMap<>();
		
		// Make packDecl
		String rootPackName = this.apigen.namegen.getApiRootPackageName();
		packDecl = "// Package " + rootPackName + " is the generated API for the " + this.apigen.proto + " protocol.\n"
				+ "// Use functions in this package to create instances of role variants.\n"
				+ "package " + rootPackName + "\n";
		
		// Make protoImports
		protoImports = getProtocolImports();
		
		// Make protoTypeAndConstr
		String protoTypeName = this.apigen.namegen.getProtoTypeName();
		protoTypeAndConstr = makeProtoTypeAndConstr(protoTypeName);

		// Populate epKindConstrFns
		for (Role rname : (Iterable<Role>) this.apigen.selfs.stream().sorted(Role.COMPARATOR)::iterator)
				// Only do the variants of the specified role names
		{
			for (RPRoleVariant originalVariant : (Iterable<RPRoleVariant>) 
					this.apigen.variants.get(rname).keySet().stream().sorted(RPRoleVariant.COMPARATOR)::iterator)
			{
				boolean isCommonEndpointKind = false;//this.apigen.isCommonEndpointKind(variant);
				
				// HACK: setting family to null for common endpoint kind
				Set<RPFamily> compactedFamilies = /*isCommonEndpointKind
						? Stream.of((RPFamily) null).collect(Collectors.toSet())  // FIXME HACK: when isCommonEndpointKind, just loop once (to make one New)
						:*/ this.apigen.families.keySet().stream().filter(f -> f.variants.contains(originalVariant)).collect(Collectors.toSet());

				for (RPFamily compactedFamily : (Iterable<RPFamily>) compactedFamilies.stream().sorted(RPFamily.COMPARATOR)::iterator)  
						// FIXME: use family to make accept/dial
				{	
					RPRoleVariant subbdbyus = getSubbedBy(originalVariant, compactedFamily);
					String fctryFn = makeEndpointKindFactoryFn(protoTypeName, originalVariant, compactedFamily, subbdbyus, isCommonEndpointKind);
					
					LinkedHashMap<RPFamily, String> tmp = epKindFctryFns.get(originalVariant);
					if (tmp == null)
					{
						tmp = new LinkedHashMap<>();
						epKindFctryFns.put(originalVariant, tmp);
					}
					tmp.put(compactedFamily, fctryFn);
				}
			}
		}

		// Assemble protoFile
		String protoFile = packDecl
				+ "\n" + protoImports
				+ "\n" + protoTypeAndConstr;
		for (RPRoleVariant v : epKindFctryFns.keySet())
		{
			for (Entry<RPFamily, String> e : epKindFctryFns.get(v).entrySet()) 
			{
				protoFile += "\n" + e.getValue();
			}
		}
		Map<String, String> res = new HashMap<>();
		res.put(getProtocolFilePath() + protoTypeName + ".go", protoFile);
		return res;
	}
	
	private String getProtocolImports()
	{
		String res = "import \"strconv\"\n"
				+ "import \"" + RPCoreDotApiGenConstants.RUNTIME_UTIL_PACKAGE + "\"\n";
		if (this.apigen.mode == Mode.IntPair)
		{
			res += "import \"" + RPCoreDotApiGenConstants.INTPAIR_RUNTIME_SESSION_PACKAGE + "\"\n";
		}
		
		for (Role rname : (Iterable<Role>) this.apigen.selfs.stream().sorted(Role.COMPARATOR)::iterator)
		{
			for (RPRoleVariant v : (Iterable<RPRoleVariant>) this.apigen.variants.get(rname).keySet()
					.stream().sorted(RPRoleVariant.COMPARATOR)::iterator)
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
					for (RPFamily f : (Iterable<RPFamily>) this.apigen.families.keySet().stream()
							.filter(x -> x.variants.contains(v)).sorted(RPFamily.COMPARATOR)::iterator)
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
				
				// Implement session.Protocol interface
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

	public String makeEndpointKindFactoryFn(String protoTypeName, RPRoleVariant variant, RPFamily family,
			RPRoleVariant subbdbyus, boolean isCommonEndpointKind)
	{
		String epkindPackName = this.apigen.namegen.getEndpointKindPackageName(variant);
		String famPackName = (isCommonEndpointKind ? "" : this.apigen.namegen.getFamilyPackageName(family) + "_");
		String importAlias = famPackName + epkindPackName;  // Cf. getProtocolApiImports
		String cnstrFnName =  "New_" + importAlias;
		String epkindTypeName = this.apigen.namegen.getEndpointKindTypeName(variant);
		List<RPIndexVar> ivars = getSortedParams(variant);

		// Endpoint Kind factory function -- makes index/foreach var value maps
		String sig = "func (p *" + protoTypeName + ") " + cnstrFnName
				
				// Params
				+ "(" + ivars.stream().filter(x -> !(x instanceof RPIndexSelf))
								.map(v -> v + " " + this.apigen.mode.indexType + ", ").collect(Collectors.joining(""))
						+ RPIndexSelf.SELF.name + " " + this.apigen.mode.indexType + ")"
						
				// Return: Endpoint Kind Type
				+ " *" + importAlias + "." + epkindTypeName;
		
		String body = 
				  makeFamilyCheck(isCommonEndpointKind ? this.apigen.families.keySet().iterator().next() : family, ivars)
				+ makeSelfIvalsCheck(variant, ivars, subbdbyus)
				+ makeSelfCoivalsCheck(variant, ivars, subbdbyus)
				
				// Call Endpoint Kind constructor, cf. makeEndpointKindConstructor
				+ "return " + importAlias + ".New" + "(p"
						+ ivars.stream().filter(x -> !(x instanceof RPIndexSelf)).map(x -> ", " + x).collect(Collectors.joining(""))
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
		
		String epkindImports;
		
		epkindImports = getEndpointKindImports();
		
		// Endpoint Kind API per role variant
		for (Role rname : (Iterable<Role>) this.apigen.selfs.stream().sorted(Role.COMPARATOR)::iterator)
				// Only do the variants of the specified role names
		{
			for (RPRoleVariant origVariant : (Iterable<RPRoleVariant>) 
					this.apigen.variants.get(rname).keySet().stream().sorted(RPRoleVariant.COMPARATOR)::iterator) 
			{
				// Some of the above original variants won't be in any compated family
				for (RPFamily compactedFamily : (Iterable<RPFamily>) this.apigen.families.keySet().stream()
								.filter(f -> f.variants.contains(origVariant)).sorted(RPFamily.COMPARATOR)::iterator)  
				{	
					List<RPIndexVar> ivars = getSortedParams(origVariant);
					RPRoleVariant subbdbyus = getSubbedBy(origVariant, compactedFamily);
					String epkindTypeName = this.apigen.namegen.getEndpointKindTypeName(origVariant);
					RPFamily orig = this.apigen.subsum.containsKey(compactedFamily) ? this.apigen.subsum.get(compactedFamily) : compactedFamily;
							
					String epkindType;
					String epkindConstr;

					// Endpoint Kind type
					epkindType = makeEndpointKindType(origVariant, ivars, epkindTypeName);
					epkindConstr = makeEndpointKindConstructor(origVariant, ivars, epkindTypeName);
					
					epkindFile = makeDialsAccepts(origVariant, orig, ivars, epkindTypeName, epkindFile, peers);

					// Connect with subbedbyus
					// FIXME: refactor into makeDialsAccepts
					if (subbdbyus != null)
					{
						Set<RPRoleVariant> peers2 = this.apigen.peers.get(subbdbyus).get(orig);
						peers2.removeAll(peers);
						epkindFile = makeDialsAccepts(origVariant, orig, ivars, epkindTypeName, epkindFile, peers2);
					}

							
					// Top-level Run method  // FIXME: add session completion check
					/*EGraph g = this.apigen.variants.get(variant.getName()).get(variant);
					//String endName = g.init.isTerminal() ? "Init" : "End";  // FIXME: factor out -- cf. RPCoreSTStateChanApiBuilder#makeSTStateName(
					*/
					String endName = "End";
					String init = //"Init_" + this.apigen.variants.get(variant.getName()).get(variant).init;
							"Init";
					epkindFile += "\n"
							+ "func (ini *" + epkindTypeName + ") Run(f func(*" + init + ") " + endName + ") " + endName + " {\n"  // f specifies non-pointer End
							+ "defer ini.Close()\n"
							
							// FIXME: factor out with RPCoreSTStateChanApiBuilder#buildActionReturn (i.e., returning initial state)
							// (FIXME: factor out with RPCoreSTSessionApiBuilder#getSuccStateChan and RPCoreSTSelectStateBuilder#getPreamble)
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
							+ ((this.apigen.job.selectApi && this.apigen.variants.get(rname).get(origVariant).init.getStateKind() == EStateKind.POLY_INPUT)
									? "newBranch" + init + "(ini)"
									: "ini._" + init) + "\n"

							+ "}";

					epkindFile += "\n\n"
							+ "func (ini *" + epkindTypeName + ") Close() {\n"
							+ "defer ini." + RPCoreSTApiGenConstants.GO_MPCHAN_SESSCHAN + ".Close()\n"
							+ "}";
				
					res.put(getEndpointKindFilePath(compactedFamily, origVariant)
									+ "/" + RPCoreSTApiGenerator.getEndpointKindTypeName(simpname, origVariant) + ".go",
							"// Generated API for the " + origVariant.getName() + getHumanReadableName(origVariant) + " role variant.\n"
							+ "package " + RPCoreSTApiGenerator.getEndpointKindPackageName(origVariant) + "\n"
							+ "//" + compactedFamily.variants.toString() + "\n\n"
							+ epkindFile);
				}
			}
		}
		
		return res;
	}

	private String makeDialsAccepts(RPRoleVariant self, RPFamily origFamily,
			List<RPIndexVar> ivars, String epkindTypeName)
	{
		 String epkindFile;

		Set<RPRoleVariant> origPeers = this.apigen.peers.get(self).get(origFamily);

		for (RPRoleVariant origPeer : origPeers)
		{
			
			... HERE factor out makeDialAndAccept for single candidate original peer
			... maybe redo getSubbedBy to take both original variant and family?
			
			RPRoleVariant subbsPeer = null;
			RPRoleVariant subbdbypeer = null;
			
			// If peer is subsumed, use subsumer instead for error feedback -- but peer ID params check still uses original peer variant
			if (this.apigen.aliases.containsKey(origPeer))
			{
				Map<RPFamily, RPRoleVariant> tmp = this.apigen.aliases.get(origPeer);
				if (tmp.containsKey(origFamily))
				{
					if (origPeers.contains(subbsPeer))  // Skip if we're already peers with subsuming-peer
					{
						break;
					}
					subbsPeer = tmp.get(origFamily);
				}
			}
			
			// If (original) peer is subsumer, allow connect (i.e., pass params/self check) with their subsumed (though still connect via peer)
			// Similar to getSubbedBy, but here family is the original (not the compacted) -- TODO: factor out with getSubbedBy ?
			for (RPRoleVariant subbd : this.apigen.aliases.keySet())
			{
				Map<RPFamily, RPRoleVariant> ais = this.apigen.aliases.get(subbd);
				if (ais.containsKey(origFamily) && ais.get(origFamily).equals(origPeer))
				{
					subbdbypeer = subbd;  // We can connect to peer or who they subbd
					break;
				}
			}
			
			if (subbsPeer != null && subbdbypeer != null)
			{
				throw new RuntimeException("[rp-core] Shouldn't get in here: " + origPeer + ", " + subbsPeer + ", " + subbdbypeer);
						// Should be restricted out earlier in the toolchain
			}
			
			// Accept/Dial methods
			String accept = makeAccept(ivars, epkindTypeName, origPeer, subbdbypeer, subbsPeer);
			String dial = makeDial(ivars, epkindTypeName, origPeer, subbdbypeer, subbsPeer);

			epkindFile += accept + "\n" + dial;
			//}
		}
		return epkindFile;
	}

	// FIXME: factor out with makeDial
	// subbedPeer is (possibly) subsumer; o/w origPeer == subbedPeer
	public String makeAccept(List<RPIndexVar> ivars, String epkindTypeName,
			RPRoleVariant origPeer, RPRoleVariant subbdbypeer, RPRoleVariant subsPeer)
	{
		String r;
		String vname;
		if (subsPeer == null)
		{
			r = origPeer.getLastElement();
			vname = RPCoreSTApiGenerator.getGeneratedRoleVariantName(origPeer);
		}
		else
		{
			r = subsPeer.getLastElement();
			vname = RPCoreSTApiGenerator.getGeneratedRoleVariantName(subsPeer);
		}

		String dial = "\n"
				+ "func (ini *" + epkindTypeName + ") " + vname + "_Accept(id " + this.apigen.mode.indexType
						+ ", ss " + RPCoreSTApiGenConstants.GO_SCRIB_LISTENER_TYPE
						+ ", sfmt " + RPCoreSTApiGenConstants.GO_FORMATTER_TYPE + ") error {\n"
						
				+ makePeerIdCheck1(origPeer, ivars, subbdbypeer)		
				+ makePeerIdCheck2(origPeer, ivars, subbdbypeer)		
						
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
				+ "}\n";
		return dial;
	}

	// subbedPeer is (possibly) subsumer; o/w origPeer == subbedPeer
	public String makeDial(List<RPIndexVar> ivars, String epkindTypeName, RPRoleVariant origPeer,
			RPRoleVariant subbdbypeer, RPRoleVariant subsPeer)
	{
		String r;
		String vname;
		if (subsPeer == null)
		{
			r = origPeer.getLastElement();
			vname = RPCoreSTApiGenerator.getGeneratedRoleVariantName(origPeer);
		}
		else
		{
			r = subsPeer.getLastElement();
			vname = RPCoreSTApiGenerator.getGeneratedRoleVariantName(subsPeer);
		}

		String dial = "func (ini *" + epkindTypeName + ") " + vname + "_Dial(id " + this.apigen.mode.indexType
					+ ", host string, port int"
					+ ", dialler func (string, int) (" + RPCoreSTApiGenConstants.GO_SCRIB_BINARY_CHAN_TYPE + ", error)"
					+ ", sfmt " + RPCoreSTApiGenConstants.GO_FORMATTER_TYPE + ") error {\n"

			+ makePeerIdCheck1(origPeer, ivars, subbdbypeer)		
			+ makePeerIdCheck2(origPeer, ivars, subbdbypeer)		

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
		return dial;
	}

	// Sorted by state ID
	// TODO CHECKME: case objects?
	private List<String> getSortedStateChanTypeNames(RPRoleVariant variant)
	{
		return this.reachable.get(variant).stream().sorted(RPCOREESTATE_COMP)
				.flatMap(s ->
					{
						String n = this.stateChanNames.get(variant).get(s.id);
						return (s.hasNested() && !s.isTerminal())
							? Stream.of(n, n + "_")  // FIXME: factor out with RPCoreSTStateChanApiBuilder#getStateChanName
							: Stream.of(n);
					})
				.distinct()
				.collect(Collectors.toList());
	}

	private String makeEndpointKindType(RPRoleVariant variant, List<RPIndexVar> ivars, String epkindTypeName)
	{
		String protoField;
		String selfField;
		String epLinObject;  // For the Endpoint itself
		String sChanLinCounter;  // For the state channels
		String mpChanField;
		String paramFields;
		String paramsMap;  // CHECKME: foreach vars only?  // CHECKME: foreach var shadowing
		String stateChans;
		
		protoField = RPCoreDotApiGenConstants.ENDPOINT_PROTO_FIELD + " " + RPCoreDotApiGenConstants.ENDPOINT_PROTOCOL_TYPE + "\n";
		selfField = RPCoreDotApiGenConstants.ENDPOINT_SELF_FIELD + this.apigen.mode.indexType + "\n";
		epLinObject = "*" + RPCoreDotApiGenConstants.RUNTIME_LINEARRESOURCE_TYPE + "\n";  // For the Endpoint itself (e.g., Run)
		sChanLinCounter = RPCoreDotApiGenConstants.ENDPOINT_LIN_FIELD + " uint64\n";
		mpChanField = RPCoreDotApiGenConstants.ENDPOINT_MPCHAN_FIELD + " *" + RPCoreDotApiGenConstants.ENDPOINT_MPCHAN_TYPE + "\n";
		paramFields = ivars.stream().map(x -> x + " " + this.apigen.mode.indexType + "\n").collect(Collectors.joining(""));

		// CHECKME: currently used to record foreach params only (and provide access to user)
		paramsMap = " " + (this.apigen.job.parForeach ? "map[int]map[string]" : "map[string]") + this.apigen.mode.indexType + "\n";
				
		// TODO CHECKME: case objects?
		stateChans = getSortedStateChanTypeNames(variant).stream()
				.map(n -> this.apigen.getEndpointKindStateChanField(n) + " *" + n + "\n").collect(Collectors.joining());

		String epkindType = "type " + epkindTypeName + " struct {\n"
				+ protoField
				+ selfField
				+ epLinObject
				+ sChanLinCounter
				+ mpChanField
				+ paramFields
				+ paramsMap
				+ stateChans;

		if (this.apigen.job.parForeach)
		{
			epkindType += RPCoreDotApiGenConstants.ENDPOINT_THREAD_FIELD + " int\n";  // CHECKME: deprecate -- recorded in state chan instead
		}

		epkindType += "}\n";
		return epkindType;
	}

	public static final int LIN_COUNTER_START = 1;
	
	public String makeEndpointKindConstructor(RPRoleVariant variant, List<RPIndexVar> ivars, String epkindTypeName)
	{
		String sig;
		String instance;
		String sChans;

		sig = "func New("

				// Params
				+ "p " + RPCoreDotApiGenConstants.ENDPOINT_PROTOCOL_TYPE + ", "
				+ ivars.stream().filter(x -> !(x instanceof RPIndexSelf))
						.map(x -> x + " " + this.apigen.mode.indexType + ", ").collect(Collectors.joining(""))  
				+ "self " + this.apigen.mode.indexType + ") "

				// Return 
				+ "*" + epkindTypeName;

		instance = "ep := &" + epkindTypeName + "{\n"

				// Args -- cf. makeEndpointKindType
				+ "p,\n"  // protoField
				+ "self,\n"  // selfField
				+ this.apigen.makeLinearResourceInstance() + ",\n"  // epLinObject -- for the Endpoint itself
				+ LIN_COUNTER_START + ",\n"  // sChanLinCounter
				+ RPCoreDotApiGenConstants.RUNTIME_MPCHAN_CONSTRUCTOR + "(self, "  // mpChanField
						+ "[]string{" + this.apigen.selfs.stream().map(x -> "\"" + x + "\"").collect(Collectors.joining(", ")) + "}),\n"
				+ ivars.stream().map(x ->  x + ",\n").collect(Collectors.joining(""))  // paramFields
				+ "make(" + (this.apigen.job.parForeach ? "map[int]map[string]" : "map[string]")  // paramsMap
						+ this.apigen.mode.indexType + "),\n"  // Trailing comma needed
				+ getSortedStateChanTypeNames(variant).stream().map(x -> "nil,\n");  // stateChans
					
		if (this.apigen.job.parForeach)
		{
			instance += "1,\n";
		}
							
		instance += "}\n";

		// TODO CHECKME: case objects?
		sChans = getSortedStateChanTypeNames(variant).stream()
				.map(n -> "ep." + this.apigen.getEndpointKindStateChanField(n) + " = "
							+ this.apigen.makeStateChanInstance(n, "ep", "1") + "\n")
				.collect(Collectors.joining());
						// CHECKME: reusing pre-created chan structs OK for Err handling?
										
		String epkindConstr = 
				  sig + " {\n"
				+ instance
				+ sChans;
		if (this.apigen.job.parForeach)
		{
			epkindConstr += "ep." + RPCoreDotApiGenConstants.ENDPOINT_FVARS_FIELD
					+ "[ep." + RPCoreDotApiGenConstants.ENDPOINT_THREAD_FIELD + "] = make(map[string]"
					+ this.apigen.mode.indexType + ")\n";
		}
		epkindConstr += "return ep\n"
				+ "}\n";
		return epkindConstr;
	}

	public String getEndpointKindImports()
	{
		String epkindImports = "import \"";
		switch (this.apigen.mode)
		{
			case Int:  
			{
				epkindImports += RPCoreDotApiGenConstants.INT_RUNTIME_SESSION_PACKAGE;  
				break;
			}
			case IntPair:  
			{
				epkindImports += "import \"" + RPCoreDotApiGenConstants.INTPAIR_RUNTIME_SESSION_PACKAGE + "\"\n";  
				break;
			}
			default:  throw new RuntimeException("Shouldn't get in here: " + this.apigen.mode);
		}
		epkindImports += "\"\n";
		epkindImports += "import \"" + RPCoreDotApiGenConstants.RUNTIME_TRANSPORT_PACKAGE + "\"\n";
		epkindImports += "import \"strconv\"\n";
		epkindImports += "\nvar _ = strconv.Itoa\n";
		return epkindImports;
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

	private List<RPIndexVar> getSortedParams(RPRoleVariant variant)
	{
		Stream<RPIndexVar> ivars = this.apigen.projections.get(variant.getName()).get(variant)
				.getIndexVars().stream();  // N.B., params only from action subjects (not self)
		ivars = Stream.concat(ivars, variant.getIndexVars().stream());  // Do variant params subsume projection params? -- nope: often projections [self] and variant [K]
		return ivars.distinct().sorted(RPIndexVar.COMPARATOR).collect(Collectors.toList());
	}

	// subber is an original-family variant, but family is the compacted family -- CHECKME: make more uniform?
	// Returns: original-family variant subsumed by subber in the original family
	public RPRoleVariant getSubbedBy(RPRoleVariant subber, RPFamily family)
	{
		RPFamily orig = this.apigen.subsum.containsKey(family) ? this.apigen.subsum.get(family) : family;
		RPRoleVariant subbed = null;
		for (RPRoleVariant v : this.apigen.aliases.keySet())
		{
			Map<RPFamily, RPRoleVariant> subbers = this.apigen.aliases.get(v);
			if (subbers.containsKey(orig) && subbers.get(orig).equals(subber))
			{
				if (subbed != null)
				{
					throw new RuntimeException("[rp-core] [-dotapi] TODO: " + subber + " is subsuming multiple variants ("
							+ subbed + ", " + v + ") in one family (" + orig + ")");
							// CHECKME: just generalise subbdbyus to a Collection?
							// TODO: subsumer also subsumed? (transitivity)
				}
				subbed = v;  // We subsumed "subbed", so we need to inherit all their peers for dial/accepts
			}
		}
		return subbed;
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
}
