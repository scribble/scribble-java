package org.scribble.ext.go.core.codegen.statetype;

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
import org.scribble.ext.go.type.index.RPBinIndexExpr;
import org.scribble.ext.go.type.index.RPIndexExpr;
import org.scribble.ext.go.type.index.RPIndexInt;
import org.scribble.ext.go.type.index.RPIndexIntPair;
import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.ext.go.util.Smt2Translator;
import org.scribble.main.ScribbleException;
import org.scribble.model.endpoint.EGraph;
import org.scribble.type.kind.Global;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.MessageId;
import org.scribble.type.name.Role;
import org.scribble.visit.util.MessageIdCollector;

// Duplicated from org.scribble.ext.go.codegen.statetype.go.GoSTEndpointApiGenerator
public class RPCoreSTApiGenerator
{
	public enum Mode { Int, IntPair }
	
	public final GoJob job;
	public final GProtocolName proto;  // Full name
	public final Mode mode;

  // FIXME: factor out an RPCoreJob(Context)
	public final Map<Role, Map<RPRoleVariant, RPCoreLType>> projections;
	public final Map<Role, Map<RPRoleVariant, EGraph>> variants;

	//public final Map<RPRoleVariant, Set<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>>> families;
	//public final Map<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>, Integer> families;  // int is arbitrary familiy id
	public final Map<RPFamily, Integer> families;  // int is arbitrary familiy id  // CHECKME: reverse the map?

	//public final Map<RPRoleVariant, Map<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>, Set<RPRoleVariant>>> peers;  
	public final Map<RPRoleVariant, Map<RPFamily, Set<RPRoleVariant>>> peers;  
			// For "common" endpoint kind factoring (dial/accept methods)
	
	//public final Map<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>, Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>> subsum;
	public final Map<RPFamily, RPFamily> subsum;
	//public final Map<RPRoleVariant, Map<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>, RPRoleVariant>> aliases;
	public final Map<RPRoleVariant, Map<RPFamily, RPRoleVariant>> aliases;
	
	public final String packpath;  
			// Prefix for absolute imports in generated APIs (e.g., "github.com/rhu1/scribble-go-runtime/test2/bar/bar02/Bar2") -- not supplied by Scribble module
	//public final Role self;  
	public final List<Role> selfs;  
			// FIXME? just a role name -- cf. CL arg
			// FIXME: any way to separate Session API (Protocol) from Endpoint/StateChan APIs?
	
	public final Smt2Translator smt2t;
	
	public RPCoreSTApiGenerator(GoJob job, GProtocolName fullname, Map<Role, Map<RPRoleVariant, RPCoreLType>> projections, 
			//Map<Role, Map<RPRoleVariant, EGraph>> variants, Set<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>> families,
			Map<Role, Map<RPRoleVariant, EGraph>> variants, Set<RPFamily> families,
			//Map<RPRoleVariant, Map<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>, Set<RPRoleVariant>>> peers,
			Map<RPRoleVariant, Map<RPFamily, Set<RPRoleVariant>>> peers,
			//Map<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>, Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>> subsum,
			Map<RPFamily, RPFamily> subsum,
			//Map<RPRoleVariant, Map<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>, RPRoleVariant>> aliases,
			Map<RPRoleVariant, Map<RPFamily, RPRoleVariant>> aliases,
			String packpath, //Role self)
			List<Role> selfs, Mode mode, Smt2Translator smt2t)
	{
		this.job = job;
		this.proto = fullname;
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

		/*Map<RPRoleVariant, Set<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>>> fs = new HashMap<>();
		for (Pair<Set<RPRoleVariant>, Set<RPRoleVariant>> f : families)
		{
			for (RPRoleVariant v : f.left)
			{
				Set<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>> tmp2 = fs.get(v);
				if (tmp2 == null)
				{
					tmp2 = new HashSet<>();
					fs.put(v, tmp2);
				}
				tmp2.add(f);
			}
		}
		this.families = Collections.unmodifiableMap(
					fs.entrySet().stream().collect(Collectors.toMap(
							e -> e.getKey(),
							e -> Collections.unmodifiableSet(e.getValue())
					)));*/
		int[] i = { 1 };  // Default family ID starts from 1
		/*Comparator<RPRoleVariant> variantComp = new Comparator<RPRoleVariant>()  // FIXME: factor out
			{
				@Override
				public int compare(RPRoleVariant i1, RPRoleVariant i2)
				{
					return i1.toString().compareTo(i2.toString());
				}
			};
		this.families = Collections.unmodifiableMap(families.stream()
				.sorted(new Comparator<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>>()
					{
						// FIXME refactor properly
						@Override
						public int compare(Pair<Set<RPRoleVariant>, Set<RPRoleVariant>> f1, Pair<Set<RPRoleVariant>, Set<RPRoleVariant>> f2)
						{
							//return f1.toString().compareTo(f2.toString());  // No: Set elements
							return (f1.left.stream().sorted(variantComp).map(x -> x.toString()).collect(Collectors.joining("_")) + "_not_" + f1.right.stream().sorted(variantComp).map(x -> x.toString()).collect(Collectors.joining("_")))
									.compareTo(f2.left.stream().sorted(variantComp).map(x -> x.toString()).collect(Collectors.joining("_")) + "_not_" + f2.right.stream().sorted(variantComp).map(x -> x.toString()).collect(Collectors.joining("_")));
						}})
				.collect(Collectors.toMap(f -> f, f -> i[0]++)));
				// CHECKME: deep copy?*/
		this.families = Collections.unmodifiableMap(families.stream().sorted(RPFamily.COMPARATOR).collect(Collectors.toMap(f -> f, f -> i[0]++)));

		this.packpath = packpath;
		//this.self = self;
		this.selfs = Collections.unmodifiableList(selfs);
		
		this.peers = Collections.unmodifiableMap(
					peers.entrySet().stream().collect(Collectors.toMap(
							e -> e.getKey(),
							e -> Collections.unmodifiableMap(e.getValue())
					)));
		
		this.subsum = Collections.unmodifiableMap(subsum);
		this.aliases = Collections.unmodifiableMap(aliases);
				// FIXME: deep copies
		
		this.mode = mode;
		this.smt2t = smt2t;
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
				throw new ScribbleException("[rpcore] [" + RPCoreCLArgParser.RPCORE_API_GEN_FLAG + "]" 
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
		
		// Build Session API (Protocol and Endpoint types) and State Channel API
		//makeStateChanNames();
		Map<String, String> res = new HashMap<>();  // filepath -> source 
		res.putAll(buildSessionApi());
		for (Role self : (Iterable<Role>) this.selfs.stream().sorted(Role.COMPARATOR)::iterator)
		{
			for (Entry<RPRoleVariant, EGraph> e : this.variants.get(self).entrySet().stream().sorted(new Comparator<Entry<RPRoleVariant, EGraph>>()
				{
					@Override
					public int compare(Entry<RPRoleVariant, EGraph> o1, Entry<RPRoleVariant, EGraph> o2)
					{
						return new Integer(o1.getValue().init.id).compareTo(o2.getValue().init.id); //o1.getValue().init.id - o2.getValue().init.id;
					}
				}).collect(Collectors.toList()))
			{
				RPRoleVariant variant = e.getKey();
				/*for (RPFamily family :
						this.families.entrySet().stream().filter(x -> x.getKey().variants.contains(variant))
						.sorted(new Comparator<Entry<RPFamily, Integer>>()
							{
								@Override
								public int compare(Entry<RPFamily, Integer> o1,
										Entry<RPFamily, Integer> o2)
								{
									return o1.getValue() - o2.getValue();
								}
							}).map(x -> x.getKey()).collect(Collectors.toList()))*/
				for (RPFamily family : (Iterable<RPFamily>) IntStream.range(1, this.families.size()+1)  // TODO: factor out constant
						.mapToObj(x -> this.families.entrySet().stream().filter(y -> y.getValue() == x).findAny().get().getKey())
						.filter(x -> x.variants.contains(variant))::iterator)
								// TODO: reverse families map?
				{
					res.putAll(buildStateChannelApi(family, variant, e.getValue()));
				}
			}
		}
		return res;
	}
	
	private Map<RPRoleVariant, Map<Integer, String>> stateChanNames = new HashMap<>();

	//@Override
	public Map<String, String> buildSessionApi()  // FIXME: factor out
	{
		this.job.debugPrintln("\n[rp-core] Running " + RPCoreSTSessionApiBuilder.class + " for " + this.proto + "@" + this.selfs);
		RPCoreSTSessionApiBuilder b = new RPCoreSTSessionApiBuilder(this);
		this.stateChanNames.putAll(b.stateChanNames);
		return b.build();
	}
	
	public Map<String, String> buildStateChannelApi(
			RPFamily family, RPRoleVariant variant, EGraph graph)  // FIXME: factor out
	{
		this.job.debugPrintln("\n[rp-core] Running " + RPCoreSTStateChanApiBuilder.class + " for " + this.proto + "@" + variant);
		return new RPCoreSTStateChanApiBuilder(this, family, variant, graph, this.stateChanNames.get(variant)).build();
	}
	
	//@Override
	public String getApiRootPackageName()  // Derives only from simple proto name
	{
		return this.proto.getSimpleName().toString();
	}

	/*public String makeApiRootPackageDecl()
	{
		return "package " + getApiRootPackageName();
	}*/

	//public String getFamilyPackageName(Pair<Set<RPRoleVariant>, Set<RPRoleVariant>> family)
	public String getFamilyPackageName(RPFamily family)
	{
		return "family_" + this.families.get(family);
	}

	public static String getEndpointKindPackageName(RPRoleVariant variant)
	{
		return getGeneratedRoleVariantName(variant);
	}
	
	// FIXME: doesn't need simpname
	// Role variant = Endpoint kind -- e.g., S_1To1, W_1Ton
	public static String getEndpointKindTypeName(GProtocolName simpname, RPRoleVariant variant)
	{
		//return simpname + "_" + getGeneratedActualRoleName(r);
		return getGeneratedRoleVariantName(variant);
	}
	
	public static String getGeneratedRoleVariantName(RPRoleVariant variant)
	{
		/*return actual.getName()
				+ actual.ranges.toString().replaceAll("\\[", "_").replaceAll("\\]", "_").replaceAll("\\.", "_");*/
		/*if (actual.ranges.size() > 1 || actual.coranges.size() > 0)
		{
			throw new RuntimeException("[param-core] TODO: " + actual);
		}
		ParamRange g = actual.ranges.iterator().next();
		return actual.getName() + "_" + g.start + "To" + g.end;*/
		return variant.getName() + "_"
				+ variant.intervals.stream().map(g -> getGeneratedNameLabel(g.start) + "to" + getGeneratedNameLabel(g.end))
				    .sorted().collect(Collectors.joining("and"))
				+ (variant.cointervals.isEmpty()
						? ""
						: "_not_" + variant.cointervals.stream().map(g -> getGeneratedNameLabel(g.start) + "to" + getGeneratedNameLabel(g.end))
						    .sorted().collect(Collectors.joining("and")));
	}

	// For type name generation -- not code exprs, cf. RPCoreSTStateChanApiBuilder#generateIndexExpr
	public static String getGeneratedNameLabel(RPIndexExpr e)
	{
		if (e instanceof RPIndexInt)
		{
			return e.toGoString();
		}
		else if (e instanceof RPIndexVar)
		{
			return e.toGoString();
		}
		else if (e instanceof RPIndexIntPair)
		{
			//return e.toGoString();  // No: that gives the "value" expression
			RPIndexIntPair p = (RPIndexIntPair) e;
			int l = ((RPIndexInt) p.left).val;
			int r = ((RPIndexInt) p.right).val;
			String ll = (l < 0) ? "neg" + (-1*l) : Integer.toString(l);
			String rr = (r < 0) ? "neg" + (-1*r) : Integer.toString(r);
			return "l" + ll + "r" + rr;
		}
		else if (e instanceof RPBinIndexExpr)
		{
			RPBinIndexExpr b = (RPBinIndexExpr) e;
			String op;
			switch (b.op)
			{
				case Add:  op = "plus"; break;
				case Subt: op = "sub";  break;
				case Mult: op = "mul";  //break;
				default: throw new RuntimeException("TODO: " + b.op);
			}
			return getGeneratedNameLabel(b.left) + op + getGeneratedNameLabel(b.right);  // FIXME: pre/postfix more consistent?
		}
		else
		{
			throw new RuntimeException("Shouldn't get here: " + e);
		} 
	}
	
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
		return isCommonEndpointKind;*/
		return this.families.size() == 1;  // Now essentially disabled, to make "variant equivalence" easier
		
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
