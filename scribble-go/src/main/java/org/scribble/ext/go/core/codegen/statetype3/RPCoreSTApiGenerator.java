package org.scribble.ext.go.core.codegen.statetype3;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.ast.Module;
import org.scribble.ast.ProtocolDecl;
import org.scribble.del.ModuleDel;
import org.scribble.ext.go.core.ast.local.RPCoreLType;
import org.scribble.ext.go.core.cli.RPCoreCLArgParser;
import org.scribble.ext.go.core.model.endpoint.action.RPCoreEAction;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.ext.go.core.type.RPInterval;
import org.scribble.ext.go.core.type.RPRoleVariant;
import org.scribble.ext.go.core.visit.RPCoreIndexVarCollector;
import org.scribble.ext.go.main.GoJob;
import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.ext.go.util.Z3Wrapper;
import org.scribble.main.ScribbleException;
import org.scribble.model.MState;
import org.scribble.model.endpoint.EGraph;
import org.scribble.type.kind.Global;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.MessageId;
import org.scribble.type.name.Role;
import org.scribble.util.Pair;
import org.scribble.visit.util.MessageIdCollector;

// Duplicated from org.scribble.ext.go.codegen.statetype.go.GoSTEndpointApiGenerator
public class RPCoreSTApiGenerator
{
	public final GoJob job;
	public final GProtocolName proto;  // Full name

  // FIXME: factor out an RPCoreJob(Context)
	public final Map<Role, Map<RPRoleVariant, RPCoreLType>> projections;
	public final Map<Role, Map<RPRoleVariant, EGraph>> variants;
	//public final Map<RPRoleVariant, Set<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>>> families;
	public final Map<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>, Integer> families;
	public final Map<RPRoleVariant, Set<RPRoleVariant>> peers;
	
	public final String packpath;  // Prefix for absolute imports in generated APIs (e.g., "github.com/rhu1/scribble-go-runtime/test2/bar/bar02/Bar2") -- not supplied by Scribble module
	//public final Role self;  
	public final List<Role> selfs;  
			// FIXME? just a role name -- cf. CL arg
			// FIXME: any way to separate Session API (Protocol) from Endpoint/StateChan APIs?
	
	public RPCoreSTApiGenerator(GoJob job, GProtocolName fullname, Map<Role, Map<RPRoleVariant, RPCoreLType>> projections, 
			Map<Role, Map<RPRoleVariant, EGraph>> variants, Set<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>> families,
			String packpath, //Role self)
			List<Role> selfs)
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
		int[] i = { 1 };
		this.families = Collections.unmodifiableMap(families.stream()
				.sorted(new Comparator<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>>()
					{
						@Override
						public int compare(Pair<Set<RPRoleVariant>, Set<RPRoleVariant>> f1, Pair<Set<RPRoleVariant>, Set<RPRoleVariant>> f2)
						{
							return f1.toString().compareTo(f2.toString());
						}})
				.collect(Collectors.toMap(f -> f, f -> i[0]++)));

		this.packpath = packpath;
		//this.self = self;
		this.selfs = Collections.unmodifiableList(selfs);
		
		Map<RPRoleVariant, Set<RPRoleVariant>> foo = new HashMap<>();
		Module mod = job.getContext().getModule(this.proto.getPrefix());
		ProtocolDecl<Global> gpd = mod.getProtocolDecl(this.proto.getSimpleName());
		for (Role rname : selfs)
		{
			for (RPRoleVariant variant : variants.get(rname).keySet()) 
			{
				/*for (Pair<Set<RPRoleVariant>, Set<RPRoleVariant>> family :
						(Iterable<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>>) 
								this.families.keySet().stream().filter(f -> f.left.contains(variant))::iterator)  // FIXME: use family to make accept/dial
				{*/
					Set<RPIndexedRole> irs = MState.getReachableActions(variants.get(rname).get(variant).init).stream()
							.map(a -> ((RPCoreEAction) a).getPeer()).collect(Collectors.toSet());
					Set<RPRoleVariant> bar = new HashSet<>();
					next: for (RPRoleVariant v : (Iterable<RPRoleVariant>)
							variants.values().stream()
									.flatMap(m -> m.keySet().stream())::iterator)
					{
						if (!v.equals(variant) && !bar.contains(v))
						{
							for (RPIndexedRole ir : irs)
							{
								if (ir.getName().equals(v.getName()))
								{
									if (ir.intervals.size() > 1)
									{
										throw new RuntimeException("[rp-core] TODO: multi-dimension intervals: " + ir);
									}
									RPInterval d = ir.intervals.stream().findAny().get();
									
									Set<RPIndexVar> vars = Stream.concat(v.intervals.stream().flatMap(x -> x.getIndexVars().stream()), v.cointervals.stream().flatMap(x -> x.getIndexVars().stream()))
											.collect(Collectors.toSet());
									vars.addAll(ir.getIndexVars());

									String smt2 = "(assert ";
									smt2 += "(exists ((self Int) "
											+  (vars.isEmpty() ? "" : vars.stream().map(x -> "(" + x + " Int)").collect(Collectors.joining(" "))) + ")\n";
									smt2 += "(and \n";
									smt2 += v.intervals.stream().map(x -> "(>= self " + x.start + ") (<= self " + x.end + ")").collect(Collectors.joining(" ")) + "\n";
									smt2 += v.cointervals.isEmpty() 
											? ""
											: "(or " + v.cointervals.stream().map(x -> "(< self " + x.start + ") (> self " + x.end + ")").collect(Collectors.joining(" ")) + ")\n";
									smt2 += "(>= self " + d.start + ") (<= self " + d.end + ")\n";
									smt2 += ")";
									smt2 += ")";
									smt2 += ")";
									boolean isSat = Z3Wrapper.checkSat(job, gpd, smt2);
									//System.out.println("bbb smt2: " + variant + ",, " + v + ",, " + ir + "\n" + smt2);
									//System.out.println("ccc checked sat: " + isSat + "\n");
									if (isSat)
									{
										bar.add(v);
										continue next;
									}
								}
							}
						}
					}
					foo.put(variant, Collections.unmodifiableSet(bar));
				//}
			}
		}
		this.peers = Collections.unmodifiableMap(foo);
		//System.out.println("aaa: " + peers);
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
		Map<String, String> res = new HashMap<>();  // filepath -> source 
		res.putAll(buildSessionApi());
		for (Role self : this.selfs)
		{
			for (Entry<RPRoleVariant, EGraph> e : this.variants.get(self).entrySet())
			{
				RPRoleVariant variant = e.getKey();
				for (Pair<Set<RPRoleVariant>, Set<RPRoleVariant>> family :
						(Iterable<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>>) 
								this.families.keySet().stream().filter(f -> f.left.contains(variant))::iterator)
				{
					res.putAll(buildStateChannelApi(family, variant, e.getValue()));
				}
			}
		}
		return res;
	}

	//@Override
	public Map<String, String> buildSessionApi()  // FIXME: factor out
	{
		this.job.debugPrintln("\n[rp-core] Running " + RPCoreSTSessionApiBuilder.class + " for " + this.proto + "@" + this.selfs);
		return new RPCoreSTSessionApiBuilder(this).build();
	}
	
	public Map<String, String> buildStateChannelApi(
			Pair<Set<RPRoleVariant>, Set<RPRoleVariant>> family, RPRoleVariant variant, EGraph graph)  // FIXME: factor out
	{
		this.job.debugPrintln("\n[rp-core] Running " + RPCoreSTStateChanApiBuilder.class + " for " + this.proto + "@" + variant);
		return new RPCoreSTStateChanApiBuilder(this, family, variant, graph).build();
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

	public String getFamilyPackageName(Pair<Set<RPRoleVariant>, Set<RPRoleVariant>> family)
	{
		return "family_" + this.families.get(family);
	}

	public static String getEndpointKindPackageName(RPRoleVariant variant)
	{
		return getGeneratedRoleVariantName(variant);
	}
	
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
				+ variant.intervals.stream().map(g -> g.start + "to" + g.end).sorted().collect(Collectors.joining("and"))
				+ (variant.cointervals.isEmpty()
						? ""
						: "_not_" + variant.cointervals.stream().map(g -> g.start + "to" + g.end).sorted().collect(Collectors.joining("and")));
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
