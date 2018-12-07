package org.scribble.ext.go.core.codegen.dotapi;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.scribble.ast.Module;
import org.scribble.ast.ProtocolDecl;
import org.scribble.del.ModuleDel;
import org.scribble.ext.go.core.ast.local.RPCoreLType;
import org.scribble.ext.go.core.cli.RPCoreCLArgParser;
import org.scribble.ext.go.core.type.RPFamily;
import org.scribble.ext.go.core.type.RPRoleVariant;
import org.scribble.ext.go.core.visit.RPCoreIndexVarCollector;
import org.scribble.ext.go.main.GoJob;
import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.ext.go.util.Smt2Translator;
import org.scribble.main.ScribbleException;
import org.scribble.model.endpoint.EGraph;
import org.scribble.type.kind.Global;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.MessageId;
import org.scribble.type.name.Role;
import org.scribble.visit.util.MessageIdCollector;



// FIXME: -parforeach race condition


// Duplicated from org.scribble.ext.go.codegen.statetype.go.GoSTEndpointApiGenerator
public class RPCoreDotApiGen
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
	
	public final RPCoreDotApiNameGen namegen = new RPCoreDotApiNameGen(this);

	public final Mode mode;
	public final Smt2Translator smt2t;
	
	public final GoJob job;
	public final GProtocolName proto;  // Full name
	public final String packpath;  // e.g.,github.com/rhu1/scribble-go-runtime/test/foreach/foreach12 -- N.B. now no trailing /Foreach12 (module name)
			// Prefix for absolute imports in generated APIs (e.g., "github.com/rhu1/scribble-go-runtime/test2/bar/bar02/Bar2") -- not supplied by Scribble module
	public final List<Role> selfs;  
	
	public final Map<Role, Map<RPRoleVariant, RPCoreLType>> projections;
	public final Map<Role, Map<RPRoleVariant, EGraph>> variants;

	public static final int INIT_FAMILY_ID = 1;
	public final Map<RPFamily, Integer> families;  // int is arbitrary familiy id  // CHECKME: reverse the map?

	public final Map<RPRoleVariant, Map<RPFamily, Set<RPRoleVariant>>> peers;  
			// For dial/accept
			// Also for "common" endpoint kind factoring (currently disabled)
	
	public final Map<RPFamily, RPFamily> subsum;  // new-family-after-subsumptions -> original-family-before-subsumptions
	public final Map<RPRoleVariant, Map<RPFamily, RPRoleVariant>> aliases;  // subsumed-variant -> original-family-subsumed-in -> subsuming-variant 
	
	//private Map<RPRoleVariant, Map<Integer, String>> stateChanNames = new HashMap<>();  // In RPCoreDotSessionApiBuilder
	
	public RPCoreDotApiGen(Mode mode, Smt2Translator smt2t, GoJob job, GProtocolName fullname,
			String packpath, List<Role> selfs, 
			Map<Role, Map<RPRoleVariant, RPCoreLType>> projections, 
			Map<Role, Map<RPRoleVariant, EGraph>> variants,
			Set<RPFamily> families,
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

		int[] i = { INIT_FAMILY_ID };
		this.families = Collections.unmodifiableMap(families.stream().sorted(RPFamily.COMPARATOR)
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

	// N.B. the base EGraph class will probably be replaced by a more specific (and more helpful) param-core class later
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
				throw new ScribbleException("[rp-core] [" + RPCoreCLArgParser.RPCORE_API_GEN_FLAG + "]" 
						+ " Message identifiers must start uppercase for Go accessibility: " + mname);
			}
		}

		RPCoreIndexVarCollector ivarcol = new RPCoreIndexVarCollector(this.job);
		gpd.accept(ivarcol);
		for (RPIndexVar ivar : ivarcol.getIndexVars())
		{
			char c = ivar.name.charAt(0);
			if (c < 'A' || c > 'Z')
			{
				throw new ScribbleException("[rp-core] [" + RPCoreCLArgParser.RPCORE_API_GEN_FLAG + "]" 
						+ " Index variables must be uppercase for Go accessibility: " + ivar);  
			}
		}
		
		Map<String, String> res = new HashMap<>();  // filepath -> source 

		// Build Session API (Protocol and Endpoint types) and State Channel API
		res.putAll(buildSessionApi());

		// Build State Channel API
		for (Role rname : (Iterable<Role>) this.selfs.stream().sorted(Role.COMPARATOR)::iterator)
		{
			// For each variant/EFSM, in order of FSM init state ID
			for (Entry<RPRoleVariant, EGraph> e : this.variants.get(rname).entrySet().stream()
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
				for (RPFamily family : (Iterable<RPFamily>) IntStream.range(INIT_FAMILY_ID, this.families.size()+INIT_FAMILY_ID)
						.mapToObj(x -> this.families.entrySet().stream().filter(y -> y.getValue() == x).findAny().get().getKey())
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
	public Map<String, String> buildSessionApi()  // FIXME: factor out
	{
		/*this.job.debugPrintln("\n[rp-core] Running " + RPCoreSTSessionApiBuilder.class + " for " + this.proto + "@" + this.selfs);
		RPCoreSTSessionApiBuilder b = new RPCoreSTSessionApiBuilder(this);
		this.stateChanNames.putAll(b.stateChanNames);
		return b.build();*/
		throw new RuntimeException("TODO");
	}
	
	public Map<String, String> buildStateChannelApi(
			RPFamily family, RPRoleVariant variant, EGraph graph)  // FIXME: factor out
	{
		/*this.job.debugPrintln("\n[rp-core] Running " + RPCoreSTStateChanApiBuilder.class + " for " + this.proto + "@" + variant);
		return new RPCoreSTStateChanApiBuilder(this, family, variant, graph, this.stateChanNames.get(variant)).build();*/
		throw new RuntimeException("TODO");
	}
	
	public String makeLinearResourceInstance()
	{
		// Same as new(...)
		return "&" + RPCoreDotApiGenConstants.RUNTIME_LINEARRESOURCE_TYPE + "{}";
	}

	// FIXME: refactor, e.g., take state ID, factor out "End"
	public String makeStateChanInstance(String schanName, String epName, String threadId)  // CHECKME: epName is always "ep"?
	{
		return ("&" + schanName + "{ nil, "
				+ (this.job.parForeach ? makeLinearResourceInstance() + ", " : (schanName.equals("Init") ? "1, " : "0, "))
						// FIXME: factor out constants
				)
				+ epName + (this.job.parForeach ? ", " + threadId : "") + " }";
	}
	
	public String getEndpointKindStateChanField(String schanName)
	{
		return "_" + schanName;
	}
	
	/*
	public boolean isCommonEndpointKind(RPRoleVariant variant)
	{
		// For factoring out "common" endpoint kinds from families -- FIXME: do earlier with/after family computation?
		// Means current variant talks to at most the same single peer in all families (but maybe not involved in some families -- but that is fine, considering a distributed projection needs only to know what to do in its relevant families)
		// Note: without explicit connections, even if don't directly talk with a family co-member, basic session model still forms the connection
		// E.g., basic pipeline, left/right not "common" because of "connections to right/[middle]/left" distinguished from "connections to *neighbours*"
		// Ideally, want to reason about dial/accept to make only the "necessary connections" => explicit connections
		//boolean isCommonEndpointKind = this.apigen.peers.get(variant).size() == 1;
				// CHECKME: why 1, should it be generalised?
				// CHECKME: peers doesn't consider families -- should isCommonEndpointKind be determined from families?
		/*boolean isCommonEndpointKind = this.families.keySet().stream().filter(p -> p.left.contains(variant))
						// Would also be reasonable to require variant to be in every family -- but not necessary since a distributed projection is only for the families that the variant is involved in
				.allMatch(p -> p.left.containsAll(this.peers.get(variant)));
		return isCommonEndpointKind;* /
		return this.families.size() == 1;  // Now essentially disabled, to make "variant equivalence" easier
		
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
		//* /
	}*/
	
	
	
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
