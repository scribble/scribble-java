package org.scribble.ext.go.core.codegen.statetype;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.scribble.ast.Module;
import org.scribble.ast.ProtocolDecl;
import org.scribble.codegen.statetype.STBranchStateBuilder;
import org.scribble.codegen.statetype.STCaseBuilder;
import org.scribble.codegen.statetype.STEndStateBuilder;
import org.scribble.codegen.statetype.STOutputStateBuilder;
import org.scribble.codegen.statetype.STReceiveStateBuilder;
import org.scribble.del.ModuleDel;
import org.scribble.ext.go.core.ast.local.RPCoreLType;
import org.scribble.ext.go.core.cli.RPCoreCLArgParser;
import org.scribble.ext.go.core.codegen.statetype.flat.RPCoreSTBranchActionBuilder;
import org.scribble.ext.go.core.codegen.statetype.flat.RPCoreSTBranchStateBuilder;
import org.scribble.ext.go.core.codegen.statetype.flat.RPCoreSTCaseActionBuilder;
import org.scribble.ext.go.core.codegen.statetype.flat.RPCoreSTCaseBuilder;
import org.scribble.ext.go.core.codegen.statetype.flat.RPCoreSTEndStateBuilder;
import org.scribble.ext.go.core.codegen.statetype.flat.RPCoreSTOutputStateBuilder;
import org.scribble.ext.go.core.codegen.statetype.flat.RPCoreSTReceiveActionBuilder;
import org.scribble.ext.go.core.codegen.statetype.flat.RPCoreSTReceiveStateBuilder;
import org.scribble.ext.go.core.codegen.statetype.flat.RPCoreSTSelectActionBuilder;
import org.scribble.ext.go.core.codegen.statetype.flat.RPCoreSTSelectStateBuilder;
import org.scribble.ext.go.core.codegen.statetype.flat.RPCoreSTSendActionBuilder;
import org.scribble.ext.go.core.codegen.statetype.nested.RPCoreNOutputStateBuilder;
import org.scribble.ext.go.core.codegen.statetype.nested.RPCoreNSendActionBuilder;
import org.scribble.ext.go.core.model.endpoint.RPCoreEState;
import org.scribble.ext.go.core.model.endpoint.action.RPCoreEAction;
import org.scribble.ext.go.core.type.RPFamily;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.ext.go.core.type.RPRoleVariant;
import org.scribble.ext.go.core.visit.RPCoreIndexVarCollector;
import org.scribble.ext.go.main.GoJob;
import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.ext.go.util.Smt2Translator;
import org.scribble.main.ScribbleException;
import org.scribble.model.endpoint.EGraph;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.type.kind.Global;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.MessageId;
import org.scribble.type.name.Role;
import org.scribble.visit.util.MessageIdCollector;



// FIXME: -parforeach race condition


// TODO CHECKME: factor out with RPCoreSTApiGenerator?
public class RPCoreSTApiGenerator
{
	public enum Mode
	{
		Int("int"),
		IntPair("session2.Pair");
		
		public final String indexType;
		
		Mode(String indexType)
		{
			this.indexType = indexType;
		}
	}
	public final RPCoreSTApiNameGen namegen = new RPCoreSTApiNameGen(this);

	public final Mode mode;
	public final Smt2Translator smt2t;
	
	public final GoJob job;
	public final GProtocolName proto;  // Full name
	public final String packpath;  
		// e.g.,github.com/rhu1/scribble-go-runtime/test/foreach/foreach12 -- N.B. now
		// no trailing /Foreach12 (module name)
		//
		// Prefix for absolute imports in generated APIs (e.g.,
		// "github.com/rhu1/scribble-go-runtime/test2/bar/bar02/Bar2") -- not supplied
		// by Scribble module
	public final List<Role> selfs;  

	public final Map<Role, Map<RPRoleVariant, RPCoreLType>> projections;
	public final Map<Role, Map<RPRoleVariant, EGraph>> variants;
		// All the original variants
	
	protected final Map<RPRoleVariant, Set<RPCoreEState>> reachable;
	protected final Map<RPRoleVariant, Map<Integer, String>> stateChanNames;  // State2
			// These are the "base" names (not the intermed names)

	public static final int INIT_FAMILY_ID = 1;
	public final Map<RPFamily, Integer> families;  // int is arbitrary familiy id  // CHECKME: reverse the map?
		// "Compacted" families

	public final Map<RPRoleVariant, Map<RPFamily, Set<RPRoleVariant>>> peers;  
		// "Original" families (with all orig-variants, which includes any subsumers)
		// -- see RPCoreCommandLine (getPeers done before compactFamilies)
		//
		// For dial/accept
		// Also for "common" endpoint kind factoring (currently disabled)
	
	public final Map<RPFamily, RPFamily> subsum;  
		// new-family-after-subsumptions -> original-family-before-subsumptions
	public final Map<RPRoleVariant, Map<RPFamily, RPRoleVariant>> aliases;  
		// subsumed-variant -> original-family-subsumed-in -> subsuming-variant 
	
	public RPCoreSTApiGenerator(Mode mode, Smt2Translator smt2t, GoJob job,
			GProtocolName fullname, String packpath, List<Role> selfs,
			Map<Role, Map<RPRoleVariant, RPCoreLType>> projections,
			Map<Role, Map<RPRoleVariant, EGraph>> variants, Set<RPFamily> families,
			Map<RPRoleVariant, Map<RPFamily, Set<RPRoleVariant>>> peers,
			Map<RPFamily, RPFamily> subsum,
			Map<RPRoleVariant, Map<RPFamily, RPRoleVariant>> aliases)
	{
		this.mode = mode;
		this.smt2t = smt2t;

		this.job = job;
		this.proto = fullname;
		this.packpath = packpath;
		this.selfs = Collections.unmodifiableList(selfs);

		this.projections = Collections.unmodifiableMap(
					projections.entrySet().stream().collect(Collectors.toMap(
							e -> e.getKey(),
							e -> Collections.unmodifiableMap(e.getValue())
					)));
		this.variants = Collections.unmodifiableMap(
					variants.entrySet().stream().collect(Collectors.toMap(
							e -> e.getKey(),
							e -> Collections.unmodifiableMap(e.getValue())
					)));

		this.reachable = Collections.unmodifiableMap(getReachable());
		this.stateChanNames = Collections.unmodifiableMap(
				makeStateChanNames().entrySet().stream().collect(Collectors.toMap(
						e -> e.getKey(),
						e -> Collections.unmodifiableMap(e.getValue())
				)));

		int[] i = { INIT_FAMILY_ID };
		this.families = Collections
				.unmodifiableMap(families.stream().sorted(RPFamily.COMPARATOR)
						.collect(Collectors.toMap(f -> f, f -> i[0]++)));
		
		this.peers = Collections.unmodifiableMap(
					peers.entrySet().stream().collect(Collectors.toMap(
							e -> e.getKey(),
							e -> Collections.unmodifiableMap(e.getValue())
					)));
		
		this.subsum = Collections.unmodifiableMap(subsum);
		this.aliases = Collections.unmodifiableMap(
					aliases.entrySet().stream().collect(Collectors.toMap(
							e -> e.getKey(),
							e -> Collections.unmodifiableMap(e.getValue())
					)));
	}

	private Map<RPRoleVariant, Set<RPCoreEState>> getReachable()
	{
		Map<RPRoleVariant, Set<RPCoreEState>> res = new HashMap<>();

		// For each variant/EFSM
		for (Entry<RPRoleVariant, EGraph> e : (Iterable<Entry<RPRoleVariant, EGraph>>) 
				this.selfs.stream().map(r -> this.variants.get(r))
					.flatMap(x -> x.entrySet().stream())::iterator)
		{
			RPRoleVariant v = e.getKey();
			EGraph g = e.getValue();

			Set<RPCoreEState> rs = new HashSet<>();
			rs.add((RPCoreEState) g.init);
			rs.addAll(RPCoreEState.getReachableStates((RPCoreEState) g.init));
				// FIXME: cast needed to select correct static -- refactor to eliminate cast
			res.put(v, Collections.unmodifiableSet(new HashSet<>(rs)));
		}

		return res;
	}

	private Map<RPRoleVariant, Map<Integer, String>> makeStateChanNames()
	{
		Map<RPRoleVariant, Map<Integer, String>> res = new HashMap<>();

		for (RPRoleVariant v : this.reachable.keySet())
		{
			EGraph g = this.variants.get(v.getName()).get(v);
			Set<RPCoreEState> todo = new HashSet<>(this.reachable.get(v));
			Map<Integer, String> curr = new HashMap<>();
			res.put(v, curr);

			for (RPCoreEState s : new HashSet<>(todo))
			{
				if (s.hasNested()) // Nested inits
				{
					RPCoreEState nested = s.getNested();
					curr.put(nested.id, "Init_" + nested.id);
					todo.remove(nested);
				}
			}

			// Handling top-level init/end must come after handling nested
			curr.put(g.init.id, "Init");
			todo.remove(g.init);
			if (g.term != null && g.term.id != g.init.id)
				// Latter is for single top-level foreach states
			{
				todo.remove(g.term); // Remove top-level term from rs
				curr.put(g.term.id, "End");
			}

			int[] counter = { 2 }; // 1 is Init
			todo.forEach(s ->
			{
				String n = s.isTerminal() // Includes nested terminals
						? (s.hasNested() ? "End_" + s.id : "End") 
							// "Nesting" or "plain" terminal (all "plain" terminals can be
							// treated the same)
						: "State" + counter[0]++;
				curr.put(s.id, n);
			});
		}

		return res;
	}


	// N.B. the base EGraph class will probably be replaced by a more specific
	// (and more helpful) param-core class later
	public Map<String, String> build() throws ScribbleException
	{
		for (Role r : this.variants.keySet())
		{
			char c = r.toString().charAt(0);
			if (c < 'A' || c > 'Z')
			{
				throw new ScribbleException("[rp-core] [" + RPCoreCLArgParser.RPCORE_API_GEN_FLAG + "]" 
						+ " Role names must start uppercase for Go accessibility: " + r);  
			}
		}

		// Duplicated from RPCoreSTSessionApiBuilder#build
		Module mod = this.job.getContext().getModule(this.proto.getPrefix());
		MessageIdCollector midcol = new MessageIdCollector(this.job, ((ModuleDel) mod.del()).getModuleContext());
		ProtocolDecl<Global> gpd = mod.getProtocolDecl(this.proto.getSimpleName());

		// Duplicated from. SessionApiGeneration#constructOpClasses
		gpd.accept(midcol);
		for (MessageId<?> mid : midcol.getNames())
		{
			String mname = mid.toString();
			char c;
			if (mname.length() == 0 || ((c = mname.charAt(0))) < 'A' || c > 'Z')
			{
				throw new ScribbleException(
						"[rp-core] [" + RPCoreCLArgParser.RPCORE_API_GEN_FLAG + "]"
								+ " Message identifiers must start uppercase for Go accessibility: "
								+ mname);
			}
		}

		RPCoreIndexVarCollector ivarcol = new RPCoreIndexVarCollector(this.job);
		gpd.accept(ivarcol);
		for (RPIndexVar ivar : ivarcol.getIndexVars())
		{
			char c = ivar.name.charAt(0);
			if (c < 'A' || c > 'Z')
			{
				throw new ScribbleException(
						"[rp-core] [" + RPCoreCLArgParser.RPCORE_API_GEN_FLAG + "]"
								+ " Index variables must be uppercase for Go accessibility: "
								+ ivar);
			}
		}
		
		Map<String, String> res = new HashMap<>();  // filepath -> source 

		// Build Session API (Protocol and Endpoint types) and State Channel API
		res.putAll(buildSessionApi());

		// Build State Channel API
		for (Role rname : (Iterable<Role>) 
				this.selfs.stream().sorted(Role.COMPARATOR)::iterator)
		{
			// For each variant/EFSM, in order of FSM init state ID
			for (Entry<RPRoleVariant, EGraph> e : 
					this.variants.get(rname).entrySet().stream()
						.sorted(new Comparator<Entry<RPRoleVariant, EGraph>>()
						{
							@Override
							public int compare(Entry<RPRoleVariant, EGraph> o1, Entry<RPRoleVariant, EGraph> o2)
							{
								return new Integer(o1.getValue().init.id).compareTo(o2.getValue().init.id);  //o1.getValue().init.id - o2.getValue().init.id;
							}
						}).collect(Collectors.toList()))
			{
				RPRoleVariant self = e.getKey();
				// Foreach family that self belongs to, in order of family ID
				for (RPFamily family : (Iterable<RPFamily>) 
						IntStream.range(INIT_FAMILY_ID, this.families.size() + INIT_FAMILY_ID)
							.mapToObj(x -> this.families.entrySet().stream()
									.filter(y -> y.getValue() == x).findAny().get().getKey())
							.filter(x -> x.variants.contains(self))::iterator)
								// CHECKME: reverse families map?
				{
					res.putAll(buildStateChannelApi(family, self, e.getValue()));
				}
			}
		}
		return res;
	}

	//@Override
	public Map<String, String> buildSessionApi()  // TODO: factor out
	{
		this.job
				.debugPrintln("\n[rp-core] Running " + RPCoreSTSessionApiBuilder.class
						+ " for " + this.proto + "@" + this.selfs);
		return new RPCoreSTSessionApiBuilder(this).build();
	}
	
	public Map<String, String> buildStateChannelApi(
			RPFamily family, RPRoleVariant variant, EGraph graph)  // TODO: factor out
	{
		this.job.debugPrintln(
				"\n[rp-core] Running " + RPCoreSTStateChanApiBuilder.class + " for "
						+ this.proto + "@" + variant);

		STOutputStateBuilder ob;
		STReceiveStateBuilder rb;
		STBranchStateBuilder bb;
		STCaseBuilder cb;
		STEndStateBuilder eb = new RPCoreSTEndStateBuilder();
		if (this.job.dotApi)
		{
			//throw new RuntimeException("[rp-core] TODO");
			ob = new RPCoreNOutputStateBuilder(
					null, //new RPCoreSTSplitActionBuilder(),  // TODO
					new RPCoreNSendActionBuilder());
			rb = new RPCoreSTReceiveStateBuilder(
					null, //new RPCoreSTReduceActionBuilder(),  // TODO
					new RPCoreSTReceiveActionBuilder());
				// Select-based branch, or type switch branch by default
			bb = (this.job.selectApi)
					? new RPCoreSTSelectStateBuilder(new RPCoreSTSelectActionBuilder())
					: new RPCoreSTBranchStateBuilder(new RPCoreSTBranchActionBuilder());
			cb = (this.job.selectApi)
					? null
					: new RPCoreSTCaseBuilder(new RPCoreSTCaseActionBuilder());
		}
		else
		{
			ob = new RPCoreSTOutputStateBuilder(
					null, //new RPCoreSTSplitActionBuilder(),  // TODO
					new RPCoreSTSendActionBuilder());
			rb = new RPCoreSTReceiveStateBuilder(
					null, //new RPCoreSTReduceActionBuilder(),  // TODO
					new RPCoreSTReceiveActionBuilder());
				// Select-based branch, or type switch branch by default
			bb = (this.job.selectApi)
					? new RPCoreSTSelectStateBuilder(new RPCoreSTSelectActionBuilder())
					: new RPCoreSTBranchStateBuilder(new RPCoreSTBranchActionBuilder());
			cb = (this.job.selectApi)
					? null
					: new RPCoreSTCaseBuilder(new RPCoreSTCaseActionBuilder());
		}
		return new RPCoreSTStateChanApiBuilder(this, family, variant, graph,
				this.stateChanNames.get(variant), ob, rb, bb, cb, eb).build();
	}
	
	public String makeLinearResourceInstance()
	{
		// Same as new(...)
		return "&" + RPCoreSTApiGenConstants.LINEARRESOURCE_TYPE + "{}";
	}

	// FIXME: refactor, e.g., take state ID, factor out "End"
	// TODO: consider best way to support different combinations of -dotpai (menu), -parforeach (threadId), etc.
	public String makeStateChanInstance(String schanName, String epName,
			String threadId, RPCoreEState s)
		// CHECKME: epName is always "ep"?
	{
		String res = ("&" + schanName + "{ nil, "
				+ (this.job.parForeach
						? makeLinearResourceInstance() + ", "
						: (schanName.equals("Init") ? "1, " : "0, ")))
					// TODO: factor out constants 
				+ epName + (this.job.parForeach ? ", " + threadId : "");
		if (this.job.dotApi && s != null)  // HACK FIXME
		{
			Map<RPIndexedRole, Set<String>> menu = getStateChanMenu(s);
			for (Entry<RPIndexedRole, Set<String>> e : 
				(Iterable<Entry<RPIndexedRole, Set<String>>>) menu.entrySet().stream()
					.sorted(new Comparator<Entry<RPIndexedRole, Set<String>>>()
						{
							@Override
							public int compare(Entry<RPIndexedRole, Set<String>> e1,
									Entry<RPIndexedRole, Set<String>> e2)
							{
								return RPIndexedRole.COMPARATOR.compare(e1.getKey(),
										e2.getKey());
							}
						})::iterator)
			{
				//ep._Init.W_1toK = &W_1toK{&Scatter_4{ep._Init}}
				// TODO factor out explicit "type name"
				/*RPIndexedRole peer = e.getKey();
				res += ", &" + this.namegen.getGeneratedIndexedRoleName(peer) + "{";
				for (String action : e.getValue())
				{
					res += "&" + action + "_" + s.id + "{" + epName + "._" + schanName + "}";
				}
				res += "}";*/
				res += ", nil";
			}
		}
		return res + " }";
	}
	
	public String getEndpointKindStateChanField(String schanName)
	{
		return "_" + schanName;
	}

	public Map<RPIndexedRole, Set<String>> getStateChanMenu(RPCoreEState s)
	{
		Map<RPIndexedRole, Set<String>> menu = new HashMap<>();  // CHECKME: apply sorting here?
		for (EAction a : s.getActions())
		{
			RPCoreEAction rpa = (RPCoreEAction) a;
			RPIndexedRole peer = rpa.getPeer();
			String action = "Scatter";  // TODO: generalise
			Set<String> tmp = menu.get(peer);  // CHECKME: always singleton?
			if (tmp == null)
			{
				tmp = new HashSet<>();
				menu.put(peer, tmp);
			}
			tmp.add(action);
		}
		return menu;
	}
	
	/**
	 * For factoring out "common" endpoint kinds from families -- CHECKME: do
	 * earlier with/after family computation?
	 * Means current variant talks to at most the same single peer in all families
	 * (but maybe not involved in some families -- but that is fine, considering a
	 * distributed projection needs only to know what to do in its relevant
	 * families).
	 * Note: without explicit connections, even if don't directly talk with a
	 * family co-member, basic session model still forms the connection.
	 * E.g., basic pipeline, left/right not "common" because of "connections to
	 * right/[middle]/left" distinguished from "connections to *neighbours*".
	 * Ideally, want to reason about dial/accept to make only the "necessary
	 * connections" => explicit connections
	 * 
	 * --- Currently only applied to singleton families
	 */
	public boolean isCommonEndpointKind(RPRoleVariant variant)
	{
		//boolean isCommonEndpointKind = this.apigen.peers.get(variant).size() == 1;
				// CHECKME: why 1, should it be generalised?
				// CHECKME: peers doesn't consider families -- should isCommonEndpointKind be determined from families?
		/*boolean isCommonEndpointKind = this.families.keySet().stream().filter(p -> p.left.contains(variant))
						// Would also be reasonable to require variant to be in every family -- but not necessary since a distributed projection is only for the families that the variant is involved in
				.allMatch(p -> p.left.containsAll(this.peers.get(variant)));
		return isCommonEndpointKind;*/
		return this.families.size() == 1;  // Now essentially disabled (only singleton families treated), to make "variant equivalence" easier
		
		/* // "Syntactically" determining common endpoint kinds difficult because concrete peers depends on param (and foreachvar) values, e.g., M in PGet w.r.t. #F
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
	
	
	
	
	/*//@Override
	public List<String> getScribbleRuntimeImports()  // FIXME: factor up
	{
		return Stream.of(
					ParamCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_SESSION_PACKAGE
					////ParamCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_SESSIONPARAM_PACKAGE
					//ParamCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_TRANSPORT_PACKAGE

					//ParamCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_BYTES_PACKAGE,
					//ParamCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_GOB_PACKAGE
				).collect(Collectors.toList());
	}

	public String generateScribbleRuntimeImports()
	{
		return getScribbleRuntimeImports().stream().map(x -> "import \"" + x + "\"\n").collect(Collectors.joining());
	}*/
}
